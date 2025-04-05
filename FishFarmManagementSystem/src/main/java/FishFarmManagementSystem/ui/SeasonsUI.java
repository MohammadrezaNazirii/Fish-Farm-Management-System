package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.CSVExporter;
import FishFarmManagementSystem.NumberCheck;
import FishFarmManagementSystem.Season;
import FishFarmManagementSystem.User;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SeasonsUI implements Initializable {

    @FXML
    private TextField beginDayField;

    @FXML
    private TextField beginMonthField;

    @FXML
    private TextField beginYearField;

    @FXML
    private TableColumn<Seasons, String> endDateColumn;

    @FXML
    private TextField endDayField;

    @FXML
    private TextField endMonthField;

    @FXML
    private TextField endYearField;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Seasons, Integer> idColumn;

    @FXML
    private TableView<Seasons> reportTableView;

    @FXML
    private TableColumn<Seasons, Integer> rowNumberColumn;

    @FXML
    private TableColumn<Seasons, String> speciesColumn;

    @FXML
    private TextField speciesField;

    @FXML
    private TableColumn<Seasons, String> startDateColumn;

    ObservableList<Seasons> initialData(){
        Integer no = 1;
        List<Season> temp = getSeasons();
        List<Seasons> seasons = new ArrayList<>();
        for(int i=temp.size()-1;i>=0;i--){
            Season tem = temp.get(i);
            seasons.add(new Seasons(no, tem.getTypeOfFish(), PersianDate.fromGregorian(tem.getStartDate()).toString(), PersianDate.fromGregorian(tem.getEndDate()).toString(), tem.getId()));
            no++;
        }
        return FXCollections.<Seasons> observableArrayList(seasons);
    }

    public List<Season> getSeasons(){
        SeasonService seasonService = MainUI.context.getBean(SeasonService.class);

        return seasonService.getAllSeasons();
    }

    private <T> void centerAlignColumn(TableColumn<Seasons, T> column) {
        column.setCellFactory(tc -> {
            TableCell<Seasons, T> cell = new TableCell<>() {
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

    @FXML
    void exit(ActionEvent event) throws IOException {
        MainUI.changePage("/AdminMenu.fxml", "پنل مدیریت", exitButton);
    }

    @FXML
    void newSeason(ActionEvent event) {
        if(NumberCheck.isInteger(beginDayField.getText()) && NumberCheck.isInteger(beginMonthField.getText()) && NumberCheck.isInteger(beginYearField.getText()) && NumberCheck.isInteger(endDayField.getText()) && NumberCheck.isInteger(endMonthField.getText()) && NumberCheck.isInteger(endYearField.getText())){
            int startDay = Integer.parseInt(beginDayField.getText());
            int startMonth = Integer.parseInt(beginMonthField.getText());
            int startYear = Integer.parseInt(beginYearField.getText());
            int endDay = Integer.parseInt(endDayField.getText());
            int endMonth = Integer.parseInt(endMonthField.getText());
            int endYear = Integer.parseInt(endYearField.getText());
            PersianDate persianDateStart = PersianDate.of(startYear, startMonth, startDay);
            LocalDate start = persianDateStart.toGregorian();
            PersianDate persianDateEnd = PersianDate.of(endYear, endMonth, endDay);
            LocalDate end = persianDateEnd.toGregorian();
            ///
            Integer isSubmitted = Season.addSeason(speciesField.getText(), start, end);
            ///
            if (isSubmitted == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ثبت فصل");
                alert.setHeaderText("وضعیت فصل");
                alert.setContentText("ثبت فصل موفق:)");

                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                alert.showAndWait();
            }

            initialize(null, null);
            speciesField.clear();
            beginDayField.clear();
            beginMonthField.clear();
            beginYearField.clear();
            endDayField.clear();
            endMonthField.clear();
            endYearField.clear();
        }else{
            NumberCheck.error();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idColumn.setCellValueFactory(new PropertyValueFactory<Seasons, Integer>("idColumn"));
        speciesColumn.setCellValueFactory(new PropertyValueFactory<Seasons, String>("speciesColumn"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<Seasons, String>("startDateColumn"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Seasons, String>("endDateColumn"));
        rowNumberColumn.setCellValueFactory(new PropertyValueFactory<Seasons, Integer>("rowNumberColumn"));

        // Center-align all columns
        centerAlignColumn(idColumn);
        centerAlignColumn(speciesColumn);
        centerAlignColumn(startDateColumn);
        centerAlignColumn(endDateColumn);
        centerAlignColumn(rowNumberColumn);


        reportTableView.setItems(initialData());
    }

    public void CSVReport() throws IOException {
        CSVExporter.exportToCSV(reportTableView, "seasons_reports.csv");

        System.out.println("CSV exported successfully!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ذخیره گزارش اکسل");
        alert.setHeaderText("وضعیت گزارش اکسل");
        alert.setContentText("ذخیره گزارش موفق:)");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }
}
