package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class SalaryController {
    public TextField txtSalaryId;
    public TextField txtEmployeeId;
    public TextField txtBasicSalary;
    public TextField txtOtHours;
    public TextField txtDeduction;
    public TextField txtDeductionType;
    public TextField txtNetSalary;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
     
    public TableView tblSalary;
    public TableColumn colSalaryId;
    public TableColumn colEmployeeId;
    public TableColumn colBasicSalary;
    public TableColumn colOtHours;
    public TableColumn colDeduction;
    public TableColumn colDeductionType;
    public TableColumn colNetSalary;
    public Button btnReset;

    public Label lblSalaryId;

    public void resetOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {

    }



    public void onClickTable(MouseEvent mouseEvent) {
    }
}
