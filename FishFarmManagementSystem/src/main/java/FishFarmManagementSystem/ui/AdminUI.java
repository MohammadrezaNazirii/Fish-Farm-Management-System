package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.service.UserService;
import com.github.mfathi91.time.PersianDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.IOException;

public class AdminUI {

    @FXML
    private Button backButton;

    @FXML
    private Label dateLabel;

    @FXML
    private Button farmsBtn;

    @FXML
    private Button linesBtn;

    @FXML
    private Label nameLabel;

    @FXML
    private Button poolsBtn;

    @FXML
    private Button reportsBtn;

    @FXML
    private Button seasonsBtn;

    @FXML
    private Button usersBtn;

    @FXML
    void back(ActionEvent event) throws IOException {
        MainUI.changePage("/LoginMenu.fxml", "سیستم مدیریت مزرعه پرورش ماهی", backButton);
    }

    @FXML
    void farmsButton(ActionEvent event) throws IOException {
        MainUI.changePage("/FarmMenu.fxml", "مزارع", farmsBtn);
    }

    @FXML
    void linesButton(ActionEvent event) throws IOException {
        MainUI.changePage("/LineMenu.fxml", "لاین ها", seasonsBtn);
    }

    @FXML
    void poolsButton(ActionEvent event) throws IOException {
        MainUI.changePage("/PoolMenu.fxml", "استخر ها", poolsBtn);
    }

    @FXML
    void reportsButton(ActionEvent event) throws IOException {
        MainUI.changePage("/AdminReport.fxml", "گزارش ها", reportsBtn);

    }

    @FXML
    void seasonsButton(ActionEvent event) throws IOException {
        MainUI.changePage("/SeasonMenu.fxml", "فصل ها", seasonsBtn);
    }

    @FXML
    void usersButton(ActionEvent event) throws IOException {
        MainUI.changePage("/UserManagementMenu.fxml", "مدیریت کارمندان", backButton);
    }

    @FXML
    public void initialize() throws ChangeSetPersister.NotFoundException{
        dateLabel.setText(String.valueOf(PersianDate.now()));
        nameLabel.setText(MainUI.context.getBean(UserService.class).findUserByUsername(LoginUI.username).getName());
    }

}
