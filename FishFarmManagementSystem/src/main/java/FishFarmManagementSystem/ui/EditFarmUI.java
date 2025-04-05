package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.Farm;
import FishFarmManagementSystem.FeedingReport;
import FishFarmManagementSystem.Pool;
import FishFarmManagementSystem.User;
import FishFarmManagementSystem.service.FarmService;
import FishFarmManagementSystem.service.FeedingReportService;
import FishFarmManagementSystem.service.PoolService;
import FishFarmManagementSystem.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.*;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditFarmUI {
    static Integer farmId;

    private List<String> ResultFeedingSpecialistNamesIds = new ArrayList<>();
    private List<Integer> ResultFeedingSpecialistIds = new ArrayList<>();
    private UserService userService = MainUI.context.getBean(UserService.class);


    @FXML
    public Label phLabel;

    @FXML
    public Label temperatureLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ChoiceBox<String> feedingSpecialistCheckList;

    @FXML
    private TextField nameField;

    @FXML
    private Button submitButton;

    @FXML
    void back(ActionEvent event) throws IOException {
        MainUI.changePage("/FarmMenu.fxml", "مزارع", backButton);
    }

    @FXML
    void delete(ActionEvent event) throws IOException, ChangeSetPersister.NotFoundException {
        Integer isSubmitted = Farm.deleteFarm(farmId);
        if(isSubmitted == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("حذف مزرعه");
            alert.setHeaderText("وضعیت مزرعه");
            alert.setContentText("حذف مزرعه موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
            ///
            MainUI.changePage("/FarmMenu.fxml", "مزارع", backButton);
        }
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        Integer isSubmitted = Farm.updateFarm(farmId, nameField.getText(), ResultFeedingSpecialistIds.get(ResultFeedingSpecialistNamesIds.indexOf(feedingSpecialistCheckList.getValue())));
        ///
        if(isSubmitted == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ویرایش مزرعه");
            alert.setHeaderText("وضعیت مزرعه");
            alert.setContentText("ویرایش مزرعه موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
            ///
            MainUI.changePage("/FarmMenu.fxml", "مزارع", backButton);
        }
    }

    @FXML
    public void initialize() throws ChangeSetPersister.NotFoundException {

        List<User> AllFeedingSpecialist = userService.findAllUsersByRole("FeedingSpecialist");
        List<Integer> AllFeedingSpecialistIds = new ArrayList<>();
        for (User user : AllFeedingSpecialist) {
            AllFeedingSpecialistIds.add(user.getId());
        }

        FarmService farmService = MainUI.context.getBean(FarmService.class);
        List<Farm> AllFarms = farmService.getAllFarms();
        List<Integer> AllFeedingSpecialistIdsWithFarm = new ArrayList<>();
        for(Farm farm : AllFarms){
            if(farm.getFeedingSpecialistId() != null) {
                AllFeedingSpecialistIdsWithFarm.add(farm.getFeedingSpecialistId());
            }
        }


        List<User> ResultFeedingSpecialist = new ArrayList<>();

        ResultFeedingSpecialist.add(userService.getUserById(farmService.getFarmById(farmId).get().getFeedingSpecialistId()));
        ResultFeedingSpecialistNamesIds.add(userService.getUserById(farmService.getFarmById(farmId).get().getFeedingSpecialistId()).getName() + " / " + farmService.getFarmById(farmId).get().getFeedingSpecialistId());
        ResultFeedingSpecialistIds.add(farmService.getFarmById(farmId).get().getFeedingSpecialistId());

        for(Integer id:AllFeedingSpecialistIds){
            if(!AllFeedingSpecialistIdsWithFarm.contains(id)){
                ResultFeedingSpecialist.add(userService.getUserById(id));
                ResultFeedingSpecialistNamesIds.add(userService.getUserById(id).getName() + " / " + id);
                ResultFeedingSpecialistIds.add(id);
            }
        }

        ObservableList<String> feedingSpecialistOptions = FXCollections.observableArrayList(ResultFeedingSpecialistNamesIds);
        feedingSpecialistCheckList.setItems(feedingSpecialistOptions);
        feedingSpecialistCheckList.setValue(feedingSpecialistOptions.get(0));


        nameField.setText(farmService.getFarmById(farmId).get().getName());

        FeedingReportService reportService = MainUI.context.getBean(FeedingReportService.class);
        List<FeedingReport> trash = reportService.getAllFeedingReportsByUsername(userService.getUserById(farmService.getFarmById(farmId).get().getFeedingSpecialistId()).getUsername());
        phLabel.setText("پی اچ" + ": "+ String.valueOf(trash.get(trash.size()-1).getPh()));
        temperatureLabel.setText("دما" + ": "+ String.valueOf(trash.get(trash.size()-1).getTemperature()));

    }

}
