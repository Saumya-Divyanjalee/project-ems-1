package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

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
    public Button btnEquipment;
    public Button calId;
    public Button btnQR;



    private void nevigateTo(String s) {
        try {
            ancpanal.getChildren().clear();

            Parent pane = FXMLLoader.load(getClass().getResource(s));

            if (pane instanceof Region) {
                Region region = (Region) pane;
                region.prefWidthProperty().bind(ancpanal.widthProperty());
                region.prefHeightProperty().bind(ancpanal.heightProperty());
            }

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

    public void qrCodeOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/QRCode.fxml");
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
        nevigateTo("/view/Ot.fxml");
    }

    public void leaveOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/Leaves.fxml");
    }

    public void taskOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/Task.fxml");
    }

    public void calOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/Calculator.fxml");
    }

    public void equipmentOnAction(ActionEvent actionEvent) {
        nevigateTo("/view/Equipment.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nevigateTo("/view/Employee.fxml");
    }
}
