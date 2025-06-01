package edu.ijse.ayurveda_skin_care;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));

        Scene scene = new Scene(parent);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ayurveda Skin Care");
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}