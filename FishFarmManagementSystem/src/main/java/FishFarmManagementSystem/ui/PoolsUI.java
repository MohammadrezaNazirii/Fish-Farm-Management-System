package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.*;
import FishFarmManagementSystem.service.LineService;
import FishFarmManagementSystem.service.PoolService;
import FishFarmManagementSystem.service.SeasonService;
import FishFarmManagementSystem.service.UserService;
import com.github.mfathi91.time.PersianDate;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class PoolsUI implements Initializable {
    private LineService lineService = MainUI.context.getBean(LineService.class);
    private List<String> lines = new ArrayList<>();
    private List<Integer> lineIds = new ArrayList<>();

    @FXML
    private Label dateLabel;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Pools, Integer> idColumn;

    @FXML
    private TableColumn<Pools, String> linesColumn;

    @FXML
    private ListView<String> linesList;

    @FXML
    private Label nameLabel;

    @FXML
    private TableColumn<Pools, String> otherEquipmentColumn;

    @FXML
    private TextField otherEquipmentField;

    @FXML
    private TableView<Pools> reportTableView;

    @FXML
    private TableColumn<Pools, Integer> rowNumberColumn;

    @FXML
    private TableColumn<Pools, Double> sizeColumn;

    @FXML
    private TextField sizeField;

    @FXML
    void exit(ActionEvent event) throws IOException {
        MainUI.changePage("/AdminMenu.fxml", "پنل مدیریت", exitButton);
    }

    @FXML
    void newPool(ActionEvent event) {
        if(NumberCheck.isNumber(sizeField.getText())){
            Integer isSubmitted = Pool.addPool(otherEquipmentField.getText(), Double.parseDouble(sizeField.getText()));
            List<String> selectedLines = linesList.getSelectionModel().getSelectedItems();
            PoolService poolService = MainUI.context.getBean(PoolService.class);
            for (String selectedLine : selectedLines) {
                lineService.updatePoolId(lineIds.get(lines.indexOf(selectedLine)), poolService.getAllPools().get(poolService.getAllPools().size() - 1).getId());
            }

            ///
            if (isSubmitted == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ثبت استخر");
                alert.setHeaderText("وضعیت استخر");
                alert.setContentText("ثبت استخر موفق:)");

                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                alert.showAndWait();
            }

            sizeField.clear();
            otherEquipmentField.clear();
            linesList.getItems().clear();
            initialize(null, null);
        }else{
            NumberCheck.error();
        }
    }

    private void makeRowsClickable() {
        reportTableView.setRowFactory(tv -> {
            TableRow<Pools> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) { // Single Click
                    Pools clickedPool = row.getItem();
                    System.out.println("Clicked on: " + clickedPool);

                    // Call a method to handle the row click
                    try {
                        onRowClick(clickedPool);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }

    private void onRowClick(Pools pool) throws IOException { /// edit on click
        EditPoolUI.poolId = pool.getIdColumn();
        MainUI.changePage("/EditPoolMenu.fxml", "ویرایش/حذف استخر", exitButton);
    }

    ObservableList<Pools> initialData(){
        Integer no = 1;
        List<Pool> temp = getPools();
        List<Pools> pools = new ArrayList<>();
        for(int i=temp.size()-1;i>=0;i--){
            Pool tem = temp.get(i);
            List<Line> tempLines = lineService.getAllLinesByPoolId(tem.getId());
            String lineCol = "";
            for (int j=0;j<tempLines.size();j++){
                if(j == tempLines.size()-1){
                    lineCol = lineCol.concat(tempLines.get(j).getId() + " / " + tempLines.get(j).getFishCount());
                }else{
                    lineCol = lineCol.concat(tempLines.get(j).getId() + " / " + tempLines.get(j).getFishCount() + " - ");
                }
            }
            pools.add(new Pools(no, tem.getSize(), lineCol, tem.getOtherEquipments(), tem.getId()));
            no++;
        }
        return FXCollections.<Pools> observableArrayList(pools);
    }

    public List<Pool> getPools(){
        PoolService poolService = MainUI.context.getBean(PoolService.class);

        return poolService.getAllPools();
    }

    private <T> void centerAlignColumn(TableColumn<Pools, T> column) {
        column.setCellFactory(tc -> {
            TableCell<Pools, T> cell = new TableCell<>() {
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
        linesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        List<Line> temp = lineService.getAllLinesByPoolId(null);
        lines.clear();
        lineIds.clear();
        for(int i=temp.size()-1;i>=0;i--){
            lines.add(String.valueOf(temp.get(i).getId()) + " / " + String.valueOf(temp.get(i).getFishCount()));
            lineIds.add(temp.get(i).getId());
        }
        ObservableList<String> options = FXCollections.observableArrayList(lines);
        linesList.setItems(options);


        idColumn.setCellValueFactory(new PropertyValueFactory<Pools, Integer>("idColumn"));
        otherEquipmentColumn.setCellValueFactory(new PropertyValueFactory<Pools, String>("otherEquipmentColumn"));
        linesColumn.setCellValueFactory(new PropertyValueFactory<Pools, String>("linesColumn"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<Pools, Double>("sizeColumn"));
        rowNumberColumn.setCellValueFactory(new PropertyValueFactory<Pools, Integer>("rowNumberColumn"));

        // Center-align all columns
        centerAlignColumn(idColumn);
        centerAlignColumn(otherEquipmentColumn);
        centerAlignColumn(linesColumn);
        centerAlignColumn(sizeColumn);
        centerAlignColumn(rowNumberColumn);

        // Set row click listener
        makeRowsClickable();

        reportTableView.setItems(initialData());
    }

    public void CSVReport() throws IOException {
        CSVExporter.exportToCSV(reportTableView, "pools_reports.csv");

        System.out.println("CSV exported successfully!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ذخیره گزارش اکسل");
        alert.setHeaderText("وضعیت گزارش اکسل");
        alert.setContentText("ذخیره گزارش موفق:)");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }
}
