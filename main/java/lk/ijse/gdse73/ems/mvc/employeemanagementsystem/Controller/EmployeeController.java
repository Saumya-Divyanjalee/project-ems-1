package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.EmployeeDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.EmployeeTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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
    public TableColumn<EmployeeTM, String> colEmpId;
    public TableColumn<EmployeeTM, String> colFirstName;
    public TableColumn<EmployeeTM, String> colLastName;
    public TableColumn<EmployeeTM, String> colDeptId;
    public TableColumn<EmployeeTM, String> colDOB;
    public TableColumn<EmployeeTM, String> colAddress;
    public TableColumn<EmployeeTM, String> colJoinDate;
    public TableColumn<EmployeeTM, String> colAge;
    public TableColumn<EmployeeTM, String> colEmail;
    public TableColumn<EmployeeTM, String> colContact;
    public TableColumn<EmployeeTM, String> colPositionId;


private  final EmployeeModel employeeModel = new EmployeeModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final String namePattern = "^[A-Za-z ]+$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colDeptId.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("eAddress"));
        colJoinDate.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPositionId.setCellValueFactory(new PropertyValueFactory<>("positionId"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong.").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> employeeDTOArrayList = employeeModel.getAllEmployees();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO dto : employeeDTOArrayList) {
            EmployeeTM tm = new EmployeeTM(
                    dto.getEmployeeId(),
                    dto.getFirstName(),
                    dto.getLastName(),
                    dto.getDepartmentId(),
                    dto.getDob(),
                    dto.getEAddress(),
                    dto.getJoinDate(),
                    dto.getAge(),
                    dto.getEmail(),
                    dto.getContact(),
                    dto.getPositionId()
            );
            employeeTMS.add(tm);
        }

        tblEmployees.setItems(employeeTMS);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();


            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtFirstName. setText("");
            txtLastName.setText("");;
            txtDeptId.setText("");
            txtAddress.setText("");
            txtAge.setText("");
            txtEmail.setText("");
            txtContact.setText("");
            txtPositionId.setText("");
            txtDob.setText("");
            txtJoinDate.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong.").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = lblEmployeeId.getText();
        String firstname = txtFirstName.getText();
        String lastname = txtLastName.getText();
        String deptId = txtDeptId.getText();
        String address = txtAddress.getText();
        String dob = txtDob.getText();
        String joinDate = txtJoinDate.getText();
        String age = txtAge.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String positionId = txtPositionId.getText();


        boolean validName = firstname.matches(namePattern);
        boolean validEmail = email.matches(emailPattern);
        boolean validContact = contact.matches(contactPattern);

        txtFirstName.setStyle(txtFirstName.getStyle() + "-fx-border-color:  #7367F0;;");
        txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color:  #7367F0;");
        txtContact.setStyle(txtContact.getStyle() + "-fx-border-color:  #7367F0;");

        if (!validName) txtFirstName.setStyle(txtFirstName.getStyle() + "-fx-border-color: red;");
        if (!validEmail) txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color: red;");
        if (!validContact) txtContact.setStyle(txtContact.getStyle() + "-fx-border-color: red;");

        EmployeeDTO dto = new EmployeeDTO(id, firstname, lastname, deptId, dob, address, joinDate, age, email, contact, positionId);

        if (validName && validEmail && validContact) {

            try {
                boolean isSaved = employeeModel.saveEmployee(dto);
                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Employee saved successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save employee.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to save employee.").show();
            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {

                String employeeId = lblEmployeeId.getText();
                String firstname = txtFirstName.getText();
                String lastname = txtLastName.getText();
                String deptId = txtDeptId.getText();
                String address = txtAddress.getText();
                String dob = txtDob.getText();
                String joinDate = txtJoinDate.getText();
                String age = txtAge.getText();
                String email = txtEmail.getText();
                String contact = txtContact.getText();
                String positionId = txtPositionId.getText();

                EmployeeDTO employeeDTO = new EmployeeDTO(
                        employeeId,firstname,lastname,deptId,dob,address,joinDate,age,email,contact,positionId
                );



        try {
            boolean isUpdated = employeeModel.updateEmployee(employeeDTO);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Employee updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update employee.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update employee.").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure?",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String empId = lblEmployeeId.getText();
            try {
                boolean isDeleted = employeeModel.deleteEmployee(empId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete employee.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to delete employee.").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void loadNextId()throws Exception{
        String nextId = employeeModel.getNextEmployeeId();
        lblEmployeeId.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        EmployeeTM selected = tblEmployees.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblEmployeeId.setText(selected.getEmployeeId());
            txtFirstName.setText(selected.getFirstName());
            txtLastName.setText(selected.getLastName());
            txtDeptId.setText(selected.getDepartmentId());
            txtDob.setText(selected.getDob());
            txtAddress.setText(selected.getEAddress());
            txtJoinDate.setText(selected.getJoinDate());
            txtAge.setText(selected.getAge());
            txtEmail.setText(selected.getEmail());
            txtContact.setText(selected.getContact());
            txtPositionId.setText(selected.getPositionId());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
