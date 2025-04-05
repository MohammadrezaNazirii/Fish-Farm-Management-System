package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.CSVExporter;
import FishFarmManagementSystem.FacilityReport;
import FishFarmManagementSystem.FeedingReport;
import FishFarmManagementSystem.NumberCheck;
import FishFarmManagementSystem.service.FacilityReportService;
import FishFarmManagementSystem.service.FeedingReportService;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminReportUI implements Initializable {

    @FXML
    private TextField beginDayField;

    @FXML
    private TextField beginMonthField;

    @FXML
    private TextField beginYearField;

    @FXML
    private TableColumn<Report, String> dateColumn;

    @FXML
    private Label dateLabel;

    @FXML
    private TableColumn<Report, String> descriptionColumn;

    @FXML
    private TextField endDayField;

    @FXML
    private TextField endMonthField;

    @FXML
    private TextField endYearField;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Report, Integer> idColumn;

    @FXML
    private TableColumn<Report, Double> phColumn;

    @FXML
    private Button reportButton;

    @FXML
    private TableView<Report> reportTableView;

    @FXML
    private TableColumn<Report, Integer> rowNumberColumn;

    @FXML
    private TableColumn<Report, Double> temperatureColumn;

    LocalDate start;
    LocalDate end;

    int sizeFeedingReport;

    int mode = 0;

    @FXML
    void report(ActionEvent event) throws IOException {
        if(NumberCheck.isInteger(beginDayField.getText()) && NumberCheck.isInteger(beginMonthField.getText()) && NumberCheck.isInteger(beginYearField.getText()) && NumberCheck.isInteger(endDayField.getText()) && NumberCheck.isInteger(endMonthField.getText()) && NumberCheck.isInteger(endYearField.getText())){
            int startDay = Integer.parseInt(beginDayField.getText());
            int startMonth = Integer.parseInt(beginMonthField.getText());
            int startYear = Integer.parseInt(beginYearField.getText());
            int endDay = Integer.parseInt(endDayField.getText());
            int endMonth = Integer.parseInt(endMonthField.getText());
            int endYear = Integer.parseInt(endYearField.getText());
            PersianDate persianDateStart = PersianDate.of(startYear, startMonth, startDay);
            start = persianDateStart.toGregorian();
            PersianDate persianDateEnd = PersianDate.of(endYear, endMonth, endDay);
            end = persianDateEnd.toGregorian();

            mode = 1;//use filter between dates
//        initialData();
            initialize(null, null);
//        MainUI.changePage("/AdminReport.fxml", "گزارش ها", exitButton);
        }else{
            NumberCheck.error();
        }

    }

    ObservableList<Report> initialData(){
        Integer no = 1;
        List<FeedingReport> temp;
        List<FacilityReport> facilityTemp;
        if(mode == 0) {
            temp = getReports();
            facilityTemp = getFacilityReports();
            System.out.println("full");
        }
        else {
            temp = getReportsBetween(start, end);
            facilityTemp = getFacilityReportsBetween(start, end);
            System.out.println("between");
        }
        List<Report> reports = new ArrayList<>();
        for(int i=temp.size()-1;i>=0;i--){
            FeedingReport tem = temp.get(i);
            reports.add(new Report(no, PersianDate.fromGregorian(tem.getDate()).toString(), tem.getDescription(), tem.getTemperature(), tem.getPh(), tem.getId()));
            no++;
        }
        for(int i=facilityTemp.size()-1;i>=0;i--){
            FacilityReport tem = facilityTemp.get(i);
            reports.add(new Report(no, PersianDate.fromGregorian(tem.getDate()).toString(), tem.getDescription(), tem.getId()));
            no++;
        }
        sizeFeedingReport = temp.size();
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
    }

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
        SingleReportAdminUI.reportId = report.getIdColumn();
        if(report.getRowNumberColumn() > sizeFeedingReport){
            SingleReportAdminUI.whichReport = 0;
        }
        else{
            SingleReportAdminUI.whichReport = 1;
        }
        MainUI.changePage("/SingleReportAdminMenu.fxml", "مشاهده/ویرایش/حذف گزارش", exitButton);
    }

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

        return reportService.getAllFeedingReports();
    }

    public List<FacilityReport> getFacilityReports(){
        FacilityReportService reportService = MainUI.context.getBean(FacilityReportService.class);

        return reportService.getAllFacilityReports();
    }

    public List<FeedingReport> getReportsBetween(LocalDate start, LocalDate end){
        FeedingReportService reportService = MainUI.context.getBean(FeedingReportService.class);

        return reportService.getAllFeedingReportsBetweenDates(start, end);
    }

    public List<FacilityReport> getFacilityReportsBetween(LocalDate start, LocalDate end){
        FacilityReportService reportService = MainUI.context.getBean(FacilityReportService.class);

        return reportService.getAllFacilityReportsBetweenDates(start, end);
    }

    public void exit() throws IOException {
        MainUI.changePage("/AdminMenu.fxml", "پنل مدیریت", exitButton);

    }

    public void CSVReport() throws IOException {
        CSVExporter.exportToCSV(reportTableView, "admin_reports.csv");

        System.out.println("CSV exported successfully!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ذخیره گزارش اکسل");
        alert.setHeaderText("وضعیت گزارش اکسل");
        alert.setContentText("ذخیره گزارش موفق:)");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }

}
