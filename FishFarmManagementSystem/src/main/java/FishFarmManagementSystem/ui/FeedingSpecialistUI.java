package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.CSVExporter;
import FishFarmManagementSystem.Farm;
import FishFarmManagementSystem.FeedingReport;
import FishFarmManagementSystem.service.FarmService;
import FishFarmManagementSystem.service.FeedingReportService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FeedingSpecialistUI implements Initializable {
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
    private TableView<Report> reportTableView;

    @FXML
    private TableColumn<Report, Integer> idColumn;

    @FXML
    private TableColumn<Report, Double> phColumn;

    @FXML
    private TableColumn<Report, Double> temperatureColumn;

    @FXML
    private TableColumn<Report, String> descriptionColumn;

    @FXML
    private TableColumn<Report, String> dateColumn;

    @FXML
    private TableColumn<Report, Integer> rowNumberColumn;

    //    private ObservableList<FeedingReport> reportList = FXCollections.observableArrayList();
    ObservableList<Report> initialData(){
        Integer no = 1;
        List<FeedingReport> temp = getReports();
        List<Report> reports = new ArrayList<>();
        for(int i=temp.size()-1;i>=0;i--){
            FeedingReport tem = temp.get(i);
            reports.add(new Report(no, PersianDate.fromGregorian(tem.getDate()).toString(), tem.getDescription(), tem.getTemperature(), tem.getPh(), tem.getId()));
            no++;
        }
        return FXCollections.<Report> observableArrayList(reports);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Report, Integer>("idColumn"));
        phColumn.setCellValueFactory(new PropertyValueFactory<Report, Double>("phColumn"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<Report, Double>("temperatureColumn"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("descriptionColumn"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Report, String>("dateColumn"));
        rowNumberColumn.setCellValueFactory(new PropertyValueFactory<Report, Integer>("rowNumberColumn"));

        // Center-align all columns
        centerAlignColumn(idColumn);
        centerAlignColumn(phColumn);
        centerAlignColumn(temperatureColumn);
        centerAlignColumn(descriptionColumn);
        centerAlignColumn(dateColumn);
        centerAlignColumn(rowNumberColumn);

        // Set row click listener
        makeRowsClickable();

        reportTableView.setItems(initialData());

        dateLabel.setText(String.valueOf(PersianDate.now()));
        nameLabel.setText(MainUI.context.getBean(UserService.class).findUserByUsername(LoginUI.username).getName());

        UserService userService = MainUI.context.getBean(UserService.class);
        FarmService farmService = MainUI.context.getBean(FarmService.class);

        List<Farm> farmName = farmService.getAllFarmsByFeedingSpecialistId(userService.findUserByUsername(LoginUI.username).getId());//.get(0).getName();

        if(farmName.isEmpty()){
            farmLabel.setText("بدون مزرعه");
        }else {
            farmLabel.setText("مزرعه: " + farmName.get(0).getName());
        }
    }

    /// ///////////////////

    private void makeRowsClickable() {
        reportTableView.setRowFactory(tv -> {
            TableRow<Report> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) { // Single Click
                    Report clickedReport = row.getItem();
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

    private void onRowClick(Report report) throws IOException {
        EditReportUI.reportId = report.getIdColumn();
        MainUI.changePage("/EditReportMenu.fxml", "ویرایش/حذف گزارش", exitButton);


        // Example action: Show details in a dialog
        System.out.println("Selected Report: " + report);

        // You can also show an alert with details
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Report Details");
//        alert.setHeaderText("Selected Report Information");
//        alert.setContentText("ID: " + report.getIdColumn() + "\n"
//                + "PH: " + report.getPhColumn() + "\n"
//                + "Temperature: " + report.getTemperatureColumn() + "\n"
//                + "Description: " + report.getDescriptionColumn() + "\n"
//                + "Date: " + report.getDateColumn());
//
//        alert.showAndWait();
    }

    /// /////////////////////////


    private <T> void centerAlignColumn(TableColumn<Report, T> column) {
        column.setCellFactory(tc -> {
            TableCell<Report, T> cell = new TableCell<>() {
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

    public List<FeedingReport> getReports(){
        FeedingReportService reportService = MainUI.context.getBean(FeedingReportService.class);

//        return reportService.getAllFeedingReports();
        return reportService.getAllFeedingReportsByUsername(LoginUI.username);
    }

    public void exit() throws IOException {
        MainUI.changePage("/LoginMenu.fxml", "سیستم مدیریت مزرعه پرورش ماهی", exitButton);
    }

    public void addReport() throws IOException {
        MainUI.changePage("/AddReportMenu.fxml", "گزارش جدید", addButton);
    }

    public void CSVReport() throws IOException {
        CSVExporter.exportToCSV(reportTableView, "feeding_reports.csv");
        System.out.println("CSV exported successfully!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ذخیره گزارش اکسل");
        alert.setHeaderText("وضعیت گزارش اکسل");
        alert.setContentText("ذخیره گزارش موفق:)");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }
}
