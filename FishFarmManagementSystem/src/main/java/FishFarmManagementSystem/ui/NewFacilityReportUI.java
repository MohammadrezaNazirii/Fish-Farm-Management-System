package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.FacilityReport;
import FishFarmManagementSystem.NumberCheck;
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

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class NewFacilityReportUI {

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
    private Button submitButton;

    @FXML
    private TextField yearField;

    private File selectedImageFile;

    @FXML
    void back(ActionEvent event) throws IOException {
        MainUI.changePage("/FacilitySpecialistMenu.fxml", "پنل مسئول تاسیسات", backButton);
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
        if(NumberCheck.isInteger(dayField.getText()) && NumberCheck.isInteger(monthField.getText()) && NumberCheck.isInteger(yearField.getText())) {
            int day = Integer.parseInt(dayField.getText());
            int month = Integer.parseInt(monthField.getText());
            int year = Integer.parseInt(yearField.getText());
            PersianDate persianDate = PersianDate.of(year, month, day);
            LocalDate gregDate = persianDate.toGregorian();
            ///
            String description = descriptionField.getText();
            Integer isSubmitted = FacilityReport.addReport(gregDate, description, String.valueOf(selectedImageFile), LoginUI.username);
            ///
            if (isSubmitted == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ثبت گزارش");
                alert.setHeaderText("وضعیت گزارش");
                alert.setContentText("ثبت گزارش موفق:)");

                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                alert.showAndWait();
                ///
                MainUI.changePage("/FacilitySpecialistMenu.fxml", "پنل مسئول تاسیسات", backButton);
            }
        } else {
            NumberCheck.error();
        }
    }

}
