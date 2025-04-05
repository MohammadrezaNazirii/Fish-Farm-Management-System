package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.User;
import FishFarmManagementSystem.service.UserService;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Objects;


public class LoginUI {

    public TextField descriptionField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label resultLabel;

    @FXML
    private Button vorodbutton;

    public static String username;

    public void loginCheck() throws IOException {
        username = usernameField.getText();
        if(User.Login(usernameField.getText(), passwordField.getText())){
            resultLabel.setText("ورود موفق:)");
            ///
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ورود");
            alert.setHeaderText("وضعیت ورود");
            alert.setContentText("ورود موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
            UserService userService = MainUI.context.getBean(UserService.class);
            User thisUser = userService.findUserByUsername(username);
            if(Objects.equals(thisUser.getRole(), "FeedingSpecialist")){
                MainUI.changePage("/FeedingSpecialistMenu.fxml", "پنل مسئول تغذیه", vorodbutton);
            }else if(Objects.equals(thisUser.getRole(), "SuperAdmin")) {
                MainUI.changePage("/AdminMenu.fxml", "پنل سوپر ادمین", vorodbutton);
            }else if(Objects.equals(thisUser.getRole(), "FacilitySpecialist")){
                MainUI.changePage("/FacilitySpecialistMenu.fxml", "پنل مسئول تاسیسات", vorodbutton);
            }
            else{
                MainUI.changePage("/AdminMenu.fxml", "پنل مدیریت", vorodbutton);
            }//admin

        }else{
            resultLabel.setText("نام کاربری یا رمز عبور اشتباه است:/");
        }
    }

    public void exit(){
        System.exit(0);
    }
}
