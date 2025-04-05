package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.CSVExporter;
import FishFarmManagementSystem.FacilityReport;
import FishFarmManagementSystem.service.FacilityReportService;
import FishFarmManagementSystem.service.UserService;
import com.github.mfathi91.time.PersianDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class FacilitySpecialistUI implements Initializable {
    @FXML
    public Label nameLabel;

    @FXML
    public Label dateLabel;

    @FXML
    public Button exitButton;

    @FXML
    public Button addButton;

    @FXML
    public Label farmLabel;

    @FXML
    private TableView<FacilityReports> reportTableView;

    @FXML
    private TableColumn<FacilityReports, Integer> idColumn;

    @FXML
    private TableColumn<FacilityReports, String> descriptionColumn;

    @FXML
    private TableColumn<FacilityReports, String> dateColumn;

    @FXML
    private TableColumn<FacilityReports, Integer> rowNumberColumn;

    ObservableList<FacilityReports> initialData(){
        Integer no = 1;
        List<FacilityReport> temp = getReports();
        List<FacilityReports> reports = new ArrayList<>();
        for(int i=temp.size()-1;i>=0;i--){
            FacilityReport tem = temp.get(i);
            reports.add(new FacilityReports(no, PersianDate.fromGregorian(tem.getDate()).toString(), tem.getDescription(), tem.getId()));
            no++;
        }
        return FXCollections.<FacilityReports> observableArrayList(reports);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<FacilityReports, Integer>("idColumn"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<FacilityReports, String>("descriptionColumn"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<FacilityReports, String>("dateColumn"));
        rowNumberColumn.setCellValueFactory(new PropertyValueFactory<FacilityReports, Integer>("rowNumberColumn"));

        // Center-align all columns
        centerAlignColumn(idColumn);
        centerAlignColumn(descriptionColumn);
        centerAlignColumn(dateColumn);
        centerAlignColumn(rowNumberColumn);

        // Set row click listener
        makeRowsClickable();

        reportTableView.setItems(initialData());

        dateLabel.setText(String.valueOf(PersianDate.now()));
        nameLabel.setText(MainUI.context.getBean(UserService.class).findUserByUsername(LoginUI.username).getName());

//        UserService userService = MainUI.context.getBean(UserService.class);
//        FarmService farmService = MainUI.context.getBean(FarmService.class);
//
//        List<Farm> farmName = farmService.getAllFarmsByFeedingSpecialistId(userService.findUserByUsername(LoginUI.username).getId());//.get(0).getName();

//        if(farmName.isEmpty()){
//            farmLabel.setText("بدون مزرعه");
//        }else {
//            farmLabel.setText("مزرعه: " + farmName.get(0).getName());
//        }
    }

    /// ///////////////////

    private void makeRowsClickable() {
        reportTableView.setRowFactory(tv -> {
            TableRow<FacilityReports> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) { // Single Click
                    FacilityReports clickedReport = row.getItem();
                    System.out.println("Clicked on: " + clickedReport);

                    // Call a method to handle the row click
                    try {
                        onRowClick(clickedReport);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }

    private void onRowClick(FacilityReports report) throws IOException {
        EditFacilityReportUI.reportId = report.getIdColumn();
        MainUI.changePage("/EditFacilityReportMenu.fxml", "ویرایش/حذف گزارش", exitButton);
    }


    private <T> void centerAlignColumn(TableColumn<FacilityReports, T> column) {
        column.setCellFactory(tc -> {
            TableCell<FacilityReports, T> cell = new TableCell<>() {
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

    public List<FacilityReport> getReports(){
        FacilityReportService reportService = MainUI.context.getBean(FacilityReportService.class);

        return reportService.getAllFacilityReportsByUsername(LoginUI.username);
    }

    public void exit() throws IOException {
        MainUI.changePage("/LoginMenu.fxml", "سیستم مدیریت مزرعه پرورش ماهی", exitButton);
    }

    public void addReport() throws IOException {
        MainUI.changePage("/AddFacilityReportMenu.fxml", "گزارش جدید", addButton);
    }

    public void CSVReport() throws IOException {
        CSVExporter.exportToCSV(reportTableView, "facility_reports.csv");
        System.out.println("CSV exported successfully!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ذخیره گزارش اکسل");
        alert.setHeaderText("وضعیت گزارش اکسل");
        alert.setContentText("ذخیره گزارش موفق:)");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }
}
