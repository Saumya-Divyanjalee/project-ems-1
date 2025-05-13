package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.EmployeeTM;

public class EmployeeController implements Initializable {

    public Label lblEmployeeId;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtDeptId;
    public TextField txtAddress;
    public TextField txtAge;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtPositionId;
    public TextField txtDob;
    public TextField txtJoinDate;


    public TableView<EmployeeTM> tblEmployees;
    public TableColumn colEmpId;
    public TableColumn colFirstName;
    public TableColumn colLastName;
    public TableColumn colDeptId;
    public TableColumn colDOB;
    public TableColumn colAddress;
    public TableColumn colJoinDate;
    public TableColumn colAge;
    public TableColumn colEmail;
    public TableColumn colContact;
    public TableColumn colPositionId;



    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void resetOnAction(ActionEvent actionEvent) {
    }

    public void onClickTable(MouseEvent mouseEvent) {

    }
}
