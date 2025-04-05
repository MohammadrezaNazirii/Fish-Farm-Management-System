package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.FeedingReport;
import FishFarmManagementSystem.NumberCheck;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import com.github.mfathi91.time.PersianDate;
import com.github.mfathi91.time.PersianMonth;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class NewReportUI {

    @FXML
    public Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField dayField;

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
        if(NumberCheck.isInteger(dayField.getText()) && NumberCheck.isInteger(monthField.getText()) && NumberCheck.isInteger(yearField.getText()) && NumberCheck.isNumber(temperatureField.getText()) && NumberCheck.isNumber(phField.getText())) {
            int day = Integer.parseInt(dayField.getText());
            int month = Integer.parseInt(monthField.getText());
            int year = Integer.parseInt(yearField.getText());
            PersianDate persianDate = PersianDate.of(year, month, day);
            LocalDate gregDate = persianDate.toGregorian();
            ///
            String temperature = temperatureField.getText();
            String ph = phField.getText();
            String description = descriptionField.getText();
            Integer isSubmitted = FeedingReport.addReport(gregDate, description, Float.parseFloat(ph), Float.parseFloat(temperature), String.valueOf(selectedImageFile), LoginUI.username);
            ///
            if (isSubmitted == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ثبت گزارش");
                alert.setHeaderText("وضعیت گزارش");
                alert.setContentText("ثبت گزارش موفق:)");

                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                alert.showAndWait();
                ///
                MainUI.changePage("/FeedingSpecialistMenu.fxml", "پنل مسئول تغذیه", backButton);
            }
        }else {
            NumberCheck.error();
        }
    }

}
