package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.CSVExporter;
import FishFarmManagementSystem.FeedingReport;
import FishFarmManagementSystem.User;
import FishFarmManagementSystem.service.FeedingReportService;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class UserManagementUI implements Initializable {

    @FXML
    private Label dateLabel;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Users, Integer> idColumn;

    @FXML
    private TableColumn<Users, String> nameColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TableColumn<Users, String> passColumn;

    @FXML
    private TextField passwordField;

    @FXML
    private TableView<Users> reportTableView;

    @FXML
    private ChoiceBox<String> roleChoice;

    @FXML
    private TableColumn<Users, String> roleColumn;

    @FXML
    private TableColumn<Users, Integer> rowNumberColumn;

    @FXML
    private TableColumn<Users, String> usernameColumn;

    @FXML
    private TextField usernameField;

    ObservableList<Users> initialData(){
        Integer no = 1;
        List<User> temp = getUsers();
        List<Users> users = new ArrayList<>();
        for(int i=temp.size()-1;i>=0;i--){
            User tem = temp.get(i);
            users.add(new Users(no, tem.getName(), tem.getUsername(), tem.getRole(), tem.getPassword(), tem.getId()));
            no++;
        }
        return FXCollections.<Users> observableArrayList(users);
    }

    public List<User> getUsers(){
        UserService userService = MainUI.context.getBean(UserService.class);

        if(Objects.equals(userService.findUserByUsername(LoginUI.username).getRole(), "SuperAdmin")) {
            return userService.getAllUsers();
        }else {
            List<User> allUsers = new ArrayList<>();
            allUsers = userService.findAllUsersByRole("FeedingSpecialist"); //age tasisat ezaf shod taghiir mikone.
            /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            allUsers.addAll(userService.findAllUsersByRole("FacilitySpecialist"));
            /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
            return allUsers;
        }
    }

    private void makeRowsClickable() {
        reportTableView.setRowFactory(tv -> {
            TableRow<Users> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 1) { // Single Click
                    Users clickedUser = row.getItem();
                    System.out.println("Clicked on: " + clickedUser);

                    // Call a method to handle the row click
                    try {
                        onRowClick(clickedUser);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            return row;
        });
    }

    private void onRowClick(Users user) throws IOException {
        EditUserUI.userId = user.getIdColumn();
        MainUI.changePage("/EditUserMenu.fxml", "ویرایش/حذف یوزر", exitButton);
    }

    private <T> void centerAlignColumn(TableColumn<Users, T> column) {
        column.setCellFactory(tc -> {
            TableCell<Users, T> cell = new TableCell<>() {
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
    void newUser(ActionEvent event) {
        ///
        int temp = 1;
        if(roleChoice.getValue().equals("ادمین")){
            temp = 0;
        }
        if(roleChoice.getValue().equals("مسئول تاسیسات")){
            temp = 2;
        }

        Integer isSubmitted = User.addUser(nameField.getText(), usernameField.getText(), passwordField.getText(), temp);
        ///
        if(isSubmitted == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ثبت یوزر");
            alert.setHeaderText("وضعیت یوزر");
            alert.setContentText("ثبت یوزر موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
        }

        initialize(null, null);
        nameField.clear();
        usernameField.clear();
        passwordField.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        UserService userService = MainUI.context.getBean(UserService.class);

        if(Objects.equals(userService.findUserByUsername(LoginUI.username).getRole(), "SuperAdmin")) {
            ObservableList<String> options = FXCollections.observableArrayList("ادمین", "مسئول تغذیه", "مسئول تاسیسات");
            roleChoice.setItems(options);
            roleChoice.setValue("ادمین"); // Set default selection (optional)
        }else {
            ObservableList<String> options = FXCollections.observableArrayList("مسئول تغذیه", "مسئول تاسیسات");
            roleChoice.setItems(options);
            roleChoice.setValue("مسئول تغذیه"); // Set default selection (optional)
        }

        dateLabel.setText(String.valueOf(PersianDate.now()));


        idColumn.setCellValueFactory(new PropertyValueFactory<Users, Integer>("idColumn"));
        passColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("passColumn"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("roleColumn"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("usernameColumn"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("nameColumn"));
        rowNumberColumn.setCellValueFactory(new PropertyValueFactory<Users, Integer>("rowNumberColumn"));

        // Center-align all columns
        centerAlignColumn(idColumn);
        centerAlignColumn(passColumn);
        centerAlignColumn(roleColumn);
        centerAlignColumn(usernameColumn);
        centerAlignColumn(nameColumn);
        centerAlignColumn(rowNumberColumn);

        // Set row click listener
        makeRowsClickable();

        reportTableView.setItems(initialData());
    }

    public void CSVReport() throws IOException {
        UserService userService = MainUI.context.getBean(UserService.class);

        if(Objects.equals(userService.findUserByUsername(LoginUI.username).getRole(), "SuperAdmin")) {
            CSVExporter.exportToCSV(reportTableView, "users_superadmin_reports.csv");
        }else {
            CSVExporter.exportToCSV(reportTableView, "users_admin_reports.csv");
        }
        System.out.println("CSV exported successfully!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ذخیره گزارش اکسل");
        alert.setHeaderText("وضعیت گزارش اکسل");
        alert.setContentText("ذخیره گزارش موفق:)");
        alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        alert.showAndWait();
    }
}
