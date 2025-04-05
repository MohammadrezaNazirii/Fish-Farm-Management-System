package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.*;
import FishFarmManagementSystem.service.LineService;
import FishFarmManagementSystem.service.SeasonService;
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
import java.util.ResourceBundle;

public class LinesUI implements Initializable {
    private SeasonService seasonService = MainUI.context.getBean(SeasonService.class);
    private List<String> seasons = new ArrayList<>();
    private List<Integer> seasonIds = new ArrayList<>();

    @FXML
    public TextField feederField;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Lines, Integer> fishCountColumn;

    @FXML
    private TableColumn<Lines, Integer> idColumn;

    @FXML
    private TableView<Lines> reportTableView;

    @FXML
    private TableColumn<Lines, Integer> rowNumberColumn;

    @FXML
    private ChoiceBox<String> seasonCheckList;

    @FXML
    private TableColumn<Lines, String> seasonColumn;

    @FXML
    private TextField sizeField;

    @FXML
    void exit(ActionEvent event) throws IOException {
        MainUI.changePage("/AdminMenu.fxml", "پنل مدیریت", exitButton);
    }

    @FXML
    void newLine(ActionEvent event) {
        if(NumberCheck.isInteger(sizeField.getText())){
            Integer isSubmitted = Line.addLine(Integer.parseInt(sizeField.getText()), seasonIds.get(seasons.indexOf(seasonCheckList.getValue())));
            ///
            if (isSubmitted == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ثبت لاین");
                alert.setHeaderText("وضعیت لاین");
                alert.setContentText("ثبت لاین موفق:)");

                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                alert.showAndWait();
            }

            initialize(null, null);
            sizeField.clear();
            feederField.clear();
        }else{
            NumberCheck.error();
        }
    }

    private void makeRowsClickable() {
        reportTableView.setRowFactory(tv -> {
            TableRow<Lines> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) { // Single Click
                    Lines clickedLine = row.getItem();
                    System.out.println("Clicked on: " + clickedLine);

                    // Call a method to handle the row click
                    try {
                        onRowClick(clickedLine);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }

    private void onRowClick(Lines line) throws IOException { /// edit on click
        EditLineUI.lineId = line.getIdColumn();
        MainUI.changePage("/EditLineMenu.fxml", "ویرایش/حذف لاین", exitButton);
    }

    ObservableList<Lines> initialData(){
        Integer no = 1;
        List<Line> temp = getLines();
        List<Lines> lines = new ArrayList<>();
        for(int i=temp.size()-1;i>=0;i--){
            Line tem = temp.get(i);
            lines.add(new Lines(no, tem.getFishCount(), PersianDate.fromGregorian(seasonService.getSeasonById(tem.getSeasonId()).get().getStartDate()).toString() + " / " + PersianDate.fromGregorian(seasonService.getSeasonById(tem.getSeasonId()).get().getEndDate()).toString(), tem.getId()));
            no++;
        }
        return FXCollections.<Lines> observableArrayList(lines);
    }

    public List<Line> getLines(){
        LineService lineService = MainUI.context.getBean(LineService.class);

        return lineService.getAllLines();
    }

    private <T> void centerAlignColumn(TableColumn<Lines, T> column) {
        column.setCellFactory(tc -> {
            TableCell<Lines, T> cell = new TableCell<>() {
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
        List<Season> temp = seasonService.getAllSeasons();
        for(int i=temp.size()-1;i>=0;i--){
            seasons.add(PersianDate.fromGregorian(temp.get(i).getStartDate()).toString() + " / " + PersianDate.fromGregorian(temp.get(i).getEndDate()).toString());
            seasonIds.add(temp.get(i).getId());
        }
        ObservableList<String> options = FXCollections.observableArrayList(seasons);
        seasonCheckList.setItems(options);
        seasonCheckList.setValue(seasons.get(0));

        idColumn.setCellValueFactory(new PropertyValueFactory<Lines, Integer>("idColumn"));
        seasonColumn.setCellValueFactory(new PropertyValueFactory<Lines, String>("seasonColumn"));
        fishCountColumn.setCellValueFactory(new PropertyValueFactory<Lines, Integer>("fishCountColumn"));
        rowNumberColumn.setCellValueFactory(new PropertyValueFactory<Lines, Integer>("rowNumberColumn"));

        // Center-align all columns
        centerAlignColumn(idColumn);
        centerAlignColumn(seasonColumn);
        centerAlignColumn(fishCountColumn);
        centerAlignColumn(rowNumberColumn);

        // Set row click listener
        makeRowsClickable();

        reportTableView.setItems(initialData());

    }

    public void CSVReport() throws IOException {
        CSVExporter.exportToCSV(reportTableView, "lines_reports.csv");

        System.out.println("CSV exported successfully!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ذخیره گزارش اکسل");
        alert.setHeaderText("وضعیت گزارش اکسل");
        alert.setContentText("ذخیره گزارش موفق:)");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }
}
