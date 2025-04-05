package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.Line;
import FishFarmManagementSystem.NumberCheck;
import FishFarmManagementSystem.Pool;
import FishFarmManagementSystem.Season;
import FishFarmManagementSystem.service.LineService;
import FishFarmManagementSystem.service.PoolService;
import FishFarmManagementSystem.service.SeasonService;
import com.github.mfathi91.time.PersianDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.IOException;
import java.util.List;

public class EditPoolUI {
    static Integer poolId;


    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField equipmentField;

    @FXML
    private TextField sizeField;

    @FXML
    private Button submitButton;

    @FXML
    void back(ActionEvent event) throws IOException {
        MainUI.changePage("/PoolMenu.fxml", "استخر ها", backButton);
    }

    @FXML
    void delete(ActionEvent event) throws ChangeSetPersister.NotFoundException, IOException {
        Integer isSubmitted = Pool.deletePool(poolId);
        if(isSubmitted == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("حذف استخر");
            alert.setHeaderText("وضعیت استخر");
            alert.setContentText("حذف استخر موفق:)");

            alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

            alert.showAndWait();
            ///
            MainUI.changePage("/PoolMenu.fxml", "استخر ها", backButton);
        }
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        if(NumberCheck.isNumber(sizeField.getText())){
            Integer isSubmitted = Pool.updatePool(poolId, equipmentField.getText(), Double.parseDouble(sizeField.getText()));
            ///
            if (isSubmitted == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ویرایش استخر");
                alert.setHeaderText("وضعیت استخر");
                alert.setContentText("ویرایش استخر موفق:)");

                alert.getDialogPane().setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

                alert.showAndWait();
                ///
                MainUI.changePage("/PoolMenu.fxml", "استخر ها", backButton);
            }
        } else{
            NumberCheck.error();
        }
    }

    @FXML
    public void initialize() throws ChangeSetPersister.NotFoundException {
        PoolService poolService = MainUI.context.getBean(PoolService.class);
        Pool tempPool = poolService.getPoolById(poolId);
        sizeField.setText(String.valueOf(tempPool.getSize()));
        equipmentField.setText(tempPool.getOtherEquipments());
    }
}
