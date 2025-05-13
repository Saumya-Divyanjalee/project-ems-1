package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class TaskController {
    public TextField txtTaskId;
    public TextField txtEmployeeId;
    public TextField txtDescription;
    public DatePicker dpDeadline;
    public ComboBox cmbStatus;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public TableView tblTask;
    public TableColumn colTaskId;
    public TableColumn colEmployeeId;
    public TableColumn colDescription;
    public TableColumn colDeadline;
    public TableColumn colStatus;
    public TextField txtStatus;
    public TextField txtDeadline;
    public Button btnAdd;
    public Label lblTaskId;

    public void resetOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void saveOnAction(ActionEvent actionEvent) {

    }

    public void addOnAction(ActionEvent actionEvent) {

    }

    public void onClickTable(MouseEvent mouseEvent) {
    }
}
