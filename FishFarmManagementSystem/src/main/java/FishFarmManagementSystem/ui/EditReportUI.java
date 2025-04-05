package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.FeedingReport;
import FishFarmManagementSystem.NumberCheck;
import FishFarmManagementSystem.service.FeedingReportService;
import com.github.mfathi91.time.PersianDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class EditReportUI {
    static Integer reportId;

    @FXML
    private Button backButton;

    @FXML
    private TextField dayField;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField descriptionField;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField monthField;

    @FXML
    private TextField phField;

    @FXML
    private Button submitButton;

    @FXML
    private TextField temperatureField;

    @FXML
    private TextField yearField;

    private File selectedImageFile;

    @FXML
    void back(ActionEvent event) throws IOException {
        MainUI.changePage("/FeedingSpecialistMenu.fxml", "پنل مسئول تغذیه", backButton);
    }

    @FXML
    void delete(ActionEvent event) throws ChangeSetPersister.NotFoundException, IOException {
        Integer isSubmitted = FeedingReport.deleteReport(reportId);
        if(isSubmitted == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("حذف گزارش");
            alert.setHeaderText("وضعیت گزارش");
            alert.setContentText("حذف گزارش موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
            ///
            MainUI.changePage("/FeedingSpecialistMenu.fxml", "پنل مسئول تغذیه", backButton);
        }
    }

    @FXML
    void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(imageView.getScene().getWindow());

        if (selectedFile != null) {
            selectedImageFile = selectedFile;
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        if(NumberCheck.isInteger(dayField.getText()) && NumberCheck.isInteger(monthField.getText()) && NumberCheck.isInteger(yearField.getText()) && NumberCheck.isNumber(temperatureField.getText()) && NumberCheck.isNumber(phField.getText())){
            int day = Integer.parseInt(dayField.getText());
            int month = Integer.parseInt(monthField.getText());
            int year = Integer.parseInt(yearField.getText());
            PersianDate persianDate = PersianDate.of(year, month, day);
            LocalDate gregDate = persianDate.toGregorian();
            ///
            String temperature = temperatureField.getText();
            String ph = phField.getText();
            String description = descriptionField.getText();
            Integer isSubmitted = FeedingReport.updateReport(reportId, gregDate, description, Float.parseFloat(ph), Float.parseFloat(temperature), String.valueOf(selectedImageFile), LoginUI.username);
            ///
            if (isSubmitted == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ویرایش گزارش");
                alert.setHeaderText("وضعیت گزارش");
                alert.setContentText("ویرایش گزارش موفق:)");

                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                alert.showAndWait();
                ///
                MainUI.changePage("/FeedingSpecialistMenu.fxml", "پنل مسئول تغذیه", backButton);
            }
        }
        else {
            NumberCheck.error();
        }
    }

    @FXML
    public void initialize() throws ChangeSetPersister.NotFoundException {
        FeedingReportService reportService = MainUI.context.getBean(FeedingReportService.class);
        FeedingReport temp = reportService.getFeedingReportById(reportId);
        descriptionField.setText(temp.getDescription());
        phField.setText(Double.toString(temp.getPh()));
        temperatureField.setText(Double.toString(temp.getTemperature()));
        File selectedFile = new File(temp.getImage_address());
        imageView.setImage(new Image(selectedFile.toURI().toString()));
        LocalDate tempDate = temp.getDate();
        PersianDate persianDate = PersianDate.fromGregorian(tempDate);
        dayField.setText(String.valueOf(persianDate.getDayOfMonth()));
        monthField.setText(String.valueOf(persianDate.getMonthValue()));
        yearField.setText(String.valueOf(persianDate.getYear()));
    }

}
