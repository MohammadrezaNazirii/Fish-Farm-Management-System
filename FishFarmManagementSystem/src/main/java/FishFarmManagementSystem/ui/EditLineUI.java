package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.Line;
import FishFarmManagementSystem.NumberCheck;
import FishFarmManagementSystem.Season;
import FishFarmManagementSystem.User;
import FishFarmManagementSystem.service.LineService;
import FishFarmManagementSystem.service.SeasonService;
import FishFarmManagementSystem.service.UserService;
import com.github.mfathi91.time.PersianDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditLineUI {
    static Integer lineId;
    private List<String> seasons = new ArrayList<>();
    private List<Integer> seasonIds = new ArrayList<>();

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ChoiceBox<String> seasonCheckList;

    @FXML
    private TextField sizeField;

    @FXML
    private Button submitButton;

    @FXML
    void back(ActionEvent event) throws IOException {
        MainUI.changePage("/LineMenu.fxml", "لاین ها", backButton);
    }

    @FXML
    void delete(ActionEvent event) throws ChangeSetPersister.NotFoundException, IOException {
        Integer isSubmitted = Line.deleteLine(lineId);
        if(isSubmitted == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("حذف لاین");
            alert.setHeaderText("وضعیت لاین");
            alert.setContentText("حذف لاین موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
            ///
            MainUI.changePage("/LineMenu.fxml", "لاین ها", deleteButton);
        }
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        if(NumberCheck.isInteger(sizeField.getText())){
            Integer isSubmitted = Line.updateLine(lineId, Integer.parseInt(sizeField.getText()), seasonIds.get(seasons.indexOf(seasonCheckList.getValue())));
            ///
            if (isSubmitted == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ویرایش لاین");
                alert.setHeaderText("وضعیت لاین");
                alert.setContentText("ویرایش لاین موفق:)");

                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                alert.showAndWait();
                ///
                MainUI.changePage("/LineMenu.fxml", "لاین ها", deleteButton);
            }
        } else{
            NumberCheck.error();
        }
    }

    @FXML
    public void initialize() throws ChangeSetPersister.NotFoundException {
        LineService lineService = MainUI.context.getBean(LineService.class);
        Line tempLine = lineService.getLineById(lineId);
        sizeField.setText(String.valueOf(tempLine.getFishCount()));

        SeasonService seasonService = MainUI.context.getBean(SeasonService.class);
        List<Season> temp = seasonService.getAllSeasons();
        for(int i=temp.size()-1;i>=0;i--){
            seasons.add(PersianDate.fromGregorian(temp.get(i).getStartDate()).toString() + " / " + PersianDate.fromGregorian(temp.get(i).getEndDate()).toString());
            seasonIds.add(temp.get(i).getId());
        }
        ObservableList<String> options = FXCollections.observableArrayList(seasons);
        seasonCheckList.setItems(options);
        seasonCheckList.setValue(seasons.get(seasonIds.indexOf(tempLine.getSeasonId())));
    }

}
