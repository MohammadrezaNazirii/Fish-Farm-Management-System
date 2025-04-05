package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.User;
import FishFarmManagementSystem.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.IOException;

public class EditUserUI {

    static Integer userId;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private CheckBox isBlocked;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField roleField;

    @FXML
    private Button submitButton;

    @FXML
    private TextField usernameField;

    @FXML
    void back(ActionEvent event) throws IOException {
        MainUI.changePage("/UserManagementMenu.fxml", "مدیریت کارمندان", backButton);
    }

    @FXML
    void delete(ActionEvent event) throws ChangeSetPersister.NotFoundException, IOException {
        Integer isSubmitted = User.deleteUser(userId);
        if(isSubmitted == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("حذف یوزر");
            alert.setHeaderText("وضعیت یوزر");
            alert.setContentText("حذف یوزر موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
            ///
            MainUI.changePage("/UserManagementMenu.fxml", "مدیریت کارمندان", backButton);}
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        Integer isSubmitted = User.updateUser(userId, nameField.getText(), usernameField.getText(), passwordField.getText(), roleField.getText(), isBlocked.isSelected());
        ///
        if(isSubmitted == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ویرایش یوزر");
            alert.setHeaderText("وضعیت یوزر");
            alert.setContentText("ویرایش یوزر موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
            ///
            MainUI.changePage("/UserManagementMenu.fxml", "مدیریت کارمندان", backButton);}
    }

    @FXML
    public void initialize() throws ChangeSetPersister.NotFoundException {
        UserService userService = MainUI.context.getBean(UserService.class);
        User temp = userService.getUserById(userId);
        nameField.setText(temp.getName());
        usernameField.setText(temp.getUsername());
        passwordField.setText(temp.getPassword());
        roleField.setText(temp.getRole());
        roleField.setEditable(false);
        if(temp.getBlocked()){
            isBlocked.fire();
        }
    }

}


