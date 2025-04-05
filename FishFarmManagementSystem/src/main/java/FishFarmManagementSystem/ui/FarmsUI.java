package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.*;
import FishFarmManagementSystem.service.FarmService;
import FishFarmManagementSystem.service.FeedingReportService;
import FishFarmManagementSystem.service.PoolService;
import FishFarmManagementSystem.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FarmsUI implements Initializable {
    private PoolService poolService = MainUI.context.getBean(PoolService.class);
    private UserService userService = MainUI.context.getBean(UserService.class);
    private List<String> pools = new ArrayList<>();
    private List<Integer> poolIds = new ArrayList<>();

    List<String> ResultFeedingSpecialistNamesIds = new ArrayList<>();
    List<Integer> ResultFeedingSpecialistIds = new ArrayList<>();

    @FXML
    private Button exitButton;

    @FXML
    private TextField farmNameField;

    @FXML
    private ChoiceBox<String> feedingSpecialistCheckList;

    @FXML
    private TableColumn<Farms, String> feedingSpecialistColumn;

    @FXML
    private TableColumn<Farms, Integer> idColumn;

    @FXML
    private TableColumn<Farms, String> nameColumn;

    @FXML
    private Button newFarm;

    @FXML
    private TableColumn<Farms, String> poolsColumn;

    @FXML
    private ListView<String> poolsList;

    @FXML
    private TableView<Farms> reportTableView;

    @FXML
    private TableColumn<Farms, Integer> rowNumberColumn;

    @FXML
    void exit(ActionEvent event) throws IOException {
        MainUI.changePage("/AdminMenu.fxml", "پنل مدیریت", exitButton);
    }

    @FXML
    void newFarm(ActionEvent event) {
        Integer isSubmitted = Farm.addFarm(farmNameField.getText(), ResultFeedingSpecialistIds.get(ResultFeedingSpecialistNamesIds.indexOf(feedingSpecialistCheckList.getValue())));
        List<String> selectedPools = poolsList.getSelectionModel().getSelectedItems();
        FarmService farmService = MainUI.context.getBean(FarmService.class);
        for (String selectedPool : selectedPools) {
            poolService.updateFarmID(poolIds.get(pools.indexOf(selectedPool)), farmService.getAllFarms().get(farmService.getAllFarms().size() - 1).getId());
        }

        ///
        if(isSubmitted == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ثبت مزرعه");
            alert.setHeaderText("وضعیت مزرعه");
            alert.setContentText("ثبت مزرعه موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
        }

        farmNameField.clear();
        poolsList.getItems().clear();
        initialize(null, null);
    }

    private void makeRowsClickable() {
        reportTableView.setRowFactory(tv -> {
            TableRow<Farms> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) { // Single Click
                    Farms clickedFarm = row.getItem();
                    System.out.println("Clicked on: " + clickedFarm);

                    // Call a method to handle the row click
                    try {
                        onRowClick(clickedFarm);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }

    private void onRowClick(Farms farm) throws IOException { /// edit on click
        EditFarmUI.farmId = farm.getIdColumn();
        MainUI.changePage("/EditFarmMenu.fxml", "ویرایش/حذف مزرعه", exitButton);
    }

    ObservableList<Farms> initialData(){
        Integer no = 1;
        List<Farm> temp = getFarms();
        List<Farms> farms = new ArrayList<>();
        for(int i=temp.size()-1;i>=0;i--){
            Farm tem = temp.get(i);
            List<Pool> tempPools = poolService.getAllPoolsByFarmId(tem.getId());
            String poolCol = "";
            for (int j=0;j<tempPools.size();j++){
                if(j == tempPools.size()-1){
                    poolCol = poolCol.concat(tempPools.get(j).getId() + " / " + tempPools.get(j).getSize());
                }else{
                    poolCol = poolCol.concat(tempPools.get(j).getId() + " / " + tempPools.get(j).getSize() + " - ");
                }
            }
            farms.add(new Farms(no, tem.getName(), poolCol, userService.getUserById(tem.getFeedingSpecialistId()).getName(), tem.getId()));
            no++;
        }
        return FXCollections.<Farms> observableArrayList(farms);
    }

    public List<Farm> getFarms(){
        FarmService farmService = MainUI.context.getBean(FarmService.class);

        return farmService.getAllFarms();
    }

    private <T> void centerAlignColumn(TableColumn<Farms, T> column) {
        column.setCellFactory(tc -> {
            TableCell<Farms, T> cell = new TableCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(item.toString());
                        setAlignment(Pos.CENTER); // Center-align the text
                    }
                }
            };
            return cell;
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        poolsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        List<Pool> temp = poolService.getAllPoolsByFarmId(null);
        pools.clear();
        poolIds.clear();
        for(int i=temp.size()-1;i>=0;i--){
            pools.add(String.valueOf(temp.get(i).getId()) + " / " + String.valueOf(temp.get(i).getSize()));
            poolIds.add(temp.get(i).getId());
        }
        ObservableList<String> options = FXCollections.observableArrayList(pools);
        poolsList.setItems(options);

        ResultFeedingSpecialistNamesIds.clear();
        ResultFeedingSpecialistIds.clear();

        List<User> AllFeedingSpecialist = userService.findAllUsersByRole("FeedingSpecialist");
        List<Integer> AllFeedingSpecialistIds = new ArrayList<>();
        for (User user : AllFeedingSpecialist) {
            AllFeedingSpecialistIds.add(user.getId());
        }

        FarmService farmService = MainUI.context.getBean(FarmService.class);
        List<Farm> AllFarms = farmService.getAllFarms();
        List<Integer> AllFeedingSpecialistIdsWithFarm = new ArrayList<>();
        for(Farm farm : AllFarms){
            if(farm.getFeedingSpecialistId() != null) {
                AllFeedingSpecialistIdsWithFarm.add(farm.getFeedingSpecialistId());
            }
        }

        List<User> ResultFeedingSpecialist = new ArrayList<>();

        for(Integer id:AllFeedingSpecialistIds){
            if(!AllFeedingSpecialistIdsWithFarm.contains(id)){
                ResultFeedingSpecialist.add(userService.getUserById(id));
                ResultFeedingSpecialistNamesIds.add(userService.getUserById(id).getName() + " / " + id);
                ResultFeedingSpecialistIds.add(id);
            }
        }

        ObservableList<String> feedingSpecialistOptions = FXCollections.observableArrayList(ResultFeedingSpecialistNamesIds);
        feedingSpecialistCheckList.setItems(feedingSpecialistOptions);


        idColumn.setCellValueFactory(new PropertyValueFactory<Farms, Integer>("idColumn"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Farms, String>("nameColumn"));
        poolsColumn.setCellValueFactory(new PropertyValueFactory<Farms, String>("poolsColumn"));
        feedingSpecialistColumn.setCellValueFactory(new PropertyValueFactory<Farms, String>("feedingSpecialistColumn"));
        rowNumberColumn.setCellValueFactory(new PropertyValueFactory<Farms, Integer>("rowNumberColumn"));

        // Center-align all columns
        centerAlignColumn(idColumn);
        centerAlignColumn(nameColumn);
        centerAlignColumn(poolsColumn);
        centerAlignColumn(feedingSpecialistColumn);
        centerAlignColumn(rowNumberColumn);

        // Set row click listener
        makeRowsClickable();

        reportTableView.setItems(initialData());
    }

    public void CSVReport() throws IOException {
        CSVExporter.exportToCSV(reportTableView, "farms_reports.csv");
        System.out.println("CSV exported successfully!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ذخیره گزارش اکسل");
        alert.setHeaderText("وضعیت گزارش اکسل");
        alert.setContentText("ذخیره گزارش موفق:)");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }
}
