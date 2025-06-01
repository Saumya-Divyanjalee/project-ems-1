package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.User;

import java.io.IOException;

public class LoginController {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnLogin;
    public Label lblError;

    private User user = new User("sau", "1234");

    public void loginOnAction(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (user.verifyLogin(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoard.fxml"));
                Parent dashboardRoot = loader.load();
                Stage stage = (Stage) txtUsername.getScene().getWindow();
                Scene scene = new Scene(dashboardRoot);
                stage.setScene(scene);
                stage.setTitle("Dashboard");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Invalid username or password.", Alert.AlertType.ERROR);
        }

    }
    private void showAlert (String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }
    }
