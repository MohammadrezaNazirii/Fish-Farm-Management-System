package FishFarmManagementSystem.ui;

import FishFarmManagementSystem.FishFarmManagementSystemApplication;
//import FishFarmManagementSystem.controller.FeedingReportController;
import FishFarmManagementSystem.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class MainUI extends Application {
    public static ApplicationContext context = SpringApplication.run(FishFarmManagementSystemApplication.class);
//    static FeedingSpecialistController controller;

    @Override
    public void start(Stage stage) throws Exception {
        /// //////Super Admin Singleton
        UserService userService = context.getBean(UserService.class);
        if(userService.findAllUsersByRole("SuperAdmin").isEmpty()) {
            userService.createSuperAdmin("Super-Admin", "SAdmin", "1");
        }
        /// //////

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FeedingSpecialistMenu.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginMenu.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("سیستم مدیریت مزرعه پرورش ماهی");

//        controller = loader.getController();

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void changePage(String fxmlFile, String title, Button button) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
