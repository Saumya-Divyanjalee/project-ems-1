package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public AnchorPane ancpanal;
    public Button btnEmployee;
    public Button btnOT;
    public Button btnTask;
    public Button btnLeave;
    public Button btnSalary;
    public Button btnDeduction;
    public Button btnDepartment;
    public Button btnPosition;
    public Button btnAttendance;
    public Button btnUser;
    public Button btnSetting;
    public AnchorPane contentPane;

    private void nevigateTo(String s) {
        try {
            ancpanal.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource(s));

            pane.prefWidthProperty().bind(ancpanal.widthProperty());
            pane.prefHeightProperty().bind(ancpanal.heightProperty());

            ancpanal.getChildren().add(pane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page Not Found!").show();
            e.printStackTrace();

        }
    }

    public void employeeOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/Employee.fxml");
    }

    public void salaryOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/Salary.fxml");
    }

    public void userOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/User.fxml");
    }

    public void settingOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/Settings.fxml");
    }

    public void attendanceOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/Attendance.fxml");
    }


    public void positionOnAction(ActionEvent actionEvent) {

        nevigateTo("/view/Positions.fxml");
    }

    public void departmentOnAction(ActionEvent actionEvent) {

        nevigateTo("/view/Department.fxml");
    }

    public void deductionOnAction(ActionEvent actionEvent) {

        nevigateTo("/view/Deductions.fxml");
    }

    public void otOnAction(ActionEvent actionEvent) {

        nevigateTo("/view/OT.fxml");
    }

    public void leaveOnAction(ActionEvent actionEvent) {

        nevigateTo("/view/Leaves.fxml");
    }

    public void taskOnAction(ActionEvent actionEvent) {

        nevigateTo("/view/Task.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nevigateTo("/view/Employee.fxml");
    }
}