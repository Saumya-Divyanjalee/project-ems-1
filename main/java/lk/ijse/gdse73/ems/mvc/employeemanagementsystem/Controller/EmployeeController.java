package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class EmployeeController {
    public TextField txtEmpId;
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtDeptId;
    public Label dob;
    public DatePicker dateDOB;
    public TextField txtAddress;
    public DatePicker dateJoinDate;
    public TextField txtAge;
    public TextField txtEmail;
    public TextField txtContact;
    public TextField txtPositionId;
    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnClear;
    public TableView tblEmployees;
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
    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        setNextId();
        loadtable();
    }

    private void loadtable() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> employees = Emodel.getAllEmployee();
        ObservableList<EmployeeTM> obc = FXCollections.observableArrayList();
        for (EmployeeDTO employee : employees) {
            EmployeeTM ETM = new EmployeeTM(
                    employee.getId(),
                    employee.getName(),
                    employee.getAddress(),
                    employee.getGender(),
                    employee.getDob(),
                    employee.getEmail(),
                    employee.getContact()
            );
            obc.add(ETM);
        }
        tblEmployee.setItems(obc);
    }


    private void setNextId() throws SQLException, ClassNotFoundException {
        String nextiD = Emodel.getNextEmployee();
        lblEmployeeid.setText(nextiD);

    }

    private void setCellValueFactory() {
        clmEmployeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        clmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        clmDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

    }
    public void tblOnAction(SortEvent<TableView> tableViewSortEvent) {
    }

    public void resetOnAction(ActionEvent actionEvent) {
    }

    public void deleteOnAction(ActionEvent actionEvent) {

    }

    public void updateOnAction(ActionEvent actionEvent) {

    }

    public void saveOnAction(ActionEvent actionEvent) {
    }

    public void eidOnAction(ActionEvent actionEvent) {
    }

    public void efirstnameOnAction(ActionEvent actionEvent) {
    }

    public void elastnameOnAction(ActionEvent actionEvent) {
    }

    public void departmentOnAction(ActionEvent actionEvent) {
    }

    public void dobOnAction(ActionEvent actionEvent) {
    }

    public void addressOnAction(ActionEvent actionEvent) {
    }

    public void jdOnAction(ActionEvent actionEvent) {
    }

    public void ageOnAction(ActionEvent actionEvent) {
    }

    public void emailOnAction(ActionEvent actionEvent) {
    }

    public void contactOnAction(ActionEvent actionEvent) {
    }

    public void pidOnAction(ActionEvent actionEvent) {

    }
}
