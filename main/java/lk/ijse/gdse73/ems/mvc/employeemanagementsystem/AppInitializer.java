package lk.ijse.gdse73.ems.mvc.employeemanagementsystem;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class AppInitializer extends Application {
    public static void main(String[] args) {

        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {

        primaryStage.setScene(
                new Scene(
                        new FXMLLoader(getClass().getResource(
                                "/view/LoadingScreen.fxml"

                )).load()
                )
        );
        primaryStage.show();

        Task<Scene> loadingTask = new Task<>() {
            @Override
            protected Scene call() throws Exception {
                Parent parent = FXMLLoader.load(
                        getClass().getResource("/view/login.fxml")
                );

                return new Scene(parent);
            }
        };
        loadingTask.setOnSucceeded(event -> {
            Scene value = loadingTask.getValue();

            primaryStage.setTitle("Employee Management System");
            primaryStage.setScene(value);

        });
        loadingTask.setOnFailed(event -> {
            System.out.println("Failed to load Application");
            primaryStage.close();

        });

        new Thread(loadingTask).start();

//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Employee Management System");
//        primaryStage.show();
    }


}
