package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.DBConnection.DBConnection;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.EmployeeDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.EmployeeTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

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
    public DatePicker cmbDob;
    public DatePicker cmbJoinDate;


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
    public TableColumn<EmployeeTM, String> colStatus;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button btnMail;
    public Button btnReport;

    private final EmployeeModel employeeModel = new EmployeeModel();

    private final String namePattern = "^[A-Za-z ]+$";
    private final String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final String contactPattern = "^\\d{10}$";


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
        ArrayList<EmployeeDTO> employeeDTOList = employeeModel.getAllEmployees();
        ObservableList<EmployeeTM> tmList = FXCollections.observableArrayList();

        for (EmployeeDTO dto : employeeDTOList) {
            tmList.add(new EmployeeTM(
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
                    dto.getPositionId(),
                    dto.getStatus()
            ));
        }
        tblEmployees.setItems(tmList);
    }

    private void resetPage() throws Exception {
        loadTableData();
        loadNextId();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtFirstName.clear();
        txtLastName.clear();
        txtDeptId.clear();
        txtAddress.clear();
        txtAge.clear();
        txtEmail.clear();
        txtContact.clear();
        txtPositionId.clear();
        cmbDob.setValue(null);
        cmbJoinDate.setValue(null);
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String id = lblEmployeeId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String deptId = txtDeptId.getText();
        String address = txtAddress.getText();
        String dob = cmbDob.getValue() != null ? cmbDob.getValue().toString() : "";
        String joinDate = cmbJoinDate.getValue() != null ? cmbJoinDate.getValue().toString() : "";
        String age = txtAge.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String positionId = txtPositionId.getText();

        boolean validName = firstName.matches(namePattern);
        boolean validEmail = email.matches(emailPattern);
        boolean validContact = contact.matches(contactPattern);

        txtFirstName.setStyle("-fx-border-color: #7367F0;");
        txtEmail.setStyle("-fx-border-color: #7367F0;");
        txtContact.setStyle("-fx-border-color: #7367F0;");

        if (!validName) txtFirstName.setStyle("-fx-border-color: red;");
        if (!validEmail) txtEmail.setStyle("-fx-border-color: red;");
        if (!validContact) txtContact.setStyle("-fx-border-color: red;");

        if (validName && validEmail && validContact) {
            EmployeeDTO dto = new EmployeeDTO(id, firstName, lastName, deptId, dob, address, joinDate, age, email, contact, positionId,colStatus);
            try {
                if (employeeModel.saveEmployee(dto)) {
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
        EmployeeDTO dto = new EmployeeDTO(
                lblEmployeeId.getText(),
                txtFirstName.getText(),
                txtLastName.getText(),
                txtDeptId.getText(),
                cmbDob.getValue() != null ? cmbDob.getValue().toString() : "",
                txtAddress.getText(),
                cmbJoinDate.getValue() != null ? cmbJoinDate.getValue().toString() : "",
                txtAge.getText(),
                txtEmail.getText(),
                txtContact.getText(),
                txtPositionId.getText(),
                colStatus
        );
        try {
            if (employeeModel.updateEmployee(dto)) {
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.YES) {
            try {
                if (employeeModel.deleteEmployee(lblEmployeeId.getText())) {
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
        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadNextId() throws Exception {
        lblEmployeeId.setText(employeeModel.getNextEmployeeId());
    }

    public void onClickTable(MouseEvent mouseEvent) {
        EmployeeTM selected = tblEmployees.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblEmployeeId.setText(selected.getEmployeeId());
            txtFirstName.setText(selected.getFirstName());
            txtLastName.setText(selected.getLastName());
            txtDeptId.setText(selected.getDepartmentId());
            cmbDob.setValue(LocalDate.parse(selected.getDob()));
            txtAddress.setText(selected.getEAddress());
            cmbJoinDate.setValue(LocalDate.parse(selected.getJoinDate()));
            txtAge.setText(selected.getAge());
            txtEmail.setText(selected.getEmail());
            txtContact.setText(selected.getContact());
            txtPositionId.setText(selected.getPositionId());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void reportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/EmployeeDetails.jrxml"));
            Connection conn = DBConnection.getInstance().getConnection();
            Map<String, Object> params = new HashMap<>();
            params.put("p_date", LocalDate.now().toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, conn);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mailOnAction(ActionEvent actionEvent) {
        EmployeeTM selected = tblEmployees.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an employee first.").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MailForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle("Send Email");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
