package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class DashboardController {
    public AnchorPane ancpanal;
    public Button btnEmployee;
    public Button btnOT;
    public Button btnTask;
    public Button btnLeave;
    public Button btnSalary;
    public Button btnDeduction;
    public Button btnDepartment;
    public Button btnPosition;
    public Button btnEmployeeEquipment;
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
    }

    public void userOnAction(ActionEvent actionEvent) {
    }

    public void settingOnAction(ActionEvent actionEvent) {
    }

    public void attendanceOnAction(ActionEvent actionEvent) {
    }

    public void emeqOnAction(ActionEvent actionEvent) {
    }

    public void positionOnAction(ActionEvent actionEvent) {
    }

    public void departmentOnAction(ActionEvent actionEvent) {
    }

    public void deductionOnAction(ActionEvent actionEvent) {
    }

    public void otOnAction(ActionEvent actionEvent) {
    }

    public void leaveOnAction(ActionEvent actionEvent) {
    }

    public void taskOnAction(ActionEvent actionEvent) {
    }
}