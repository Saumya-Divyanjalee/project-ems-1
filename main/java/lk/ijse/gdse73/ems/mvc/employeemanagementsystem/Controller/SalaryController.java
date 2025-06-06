package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.DeductionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.EmployeeDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.OtDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.PositionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.SalaryTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SalaryController implements Initializable {

    public Label lblSalaryId;
    public ComboBox<String> cmbEmployeeId;


    public String txtEmployeeId;
    public String txtEmployeeName;
    public String txtBasicSalary;
    public String txtOtHours;
    public String txtDeduction;
    public String txtDeductionType;
    public String txtNetSalary;

    public TableView<SalaryTM> tblSalary;
    public TableColumn<SalaryTM, String> colSalaryId;
    public TableColumn<SalaryTM, String> colEmployeeId;
    public TableColumn<SalaryTM, String> colBasicSalary;
    public TableColumn<SalaryTM, String> colOtHours;
    public TableColumn<SalaryTM, String> colDeduction;
    public TableColumn<SalaryTM, String> colDeductionType;
    public TableColumn<SalaryTM, String> colNetSalary;
    public TableColumn<SalaryTM, String> colEmployeeName;

    public Button btnSave;
    public Button btnReset;

    private final SalaryModel salaryModel = new SalaryModel();
    private final PositionsModel positionsModel = new PositionsModel();
    private final DeductionsModel deductionsModel = new DeductionsModel();
    private final OtModel otModel = new OtModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colOtHours.setCellValueFactory(new PropertyValueFactory<>("otHours"));
        colDeduction.setCellValueFactory(new PropertyValueFactory<>("deduction"));
        colDeductionType.setCellValueFactory(new PropertyValueFactory<>("deductionType"));
        colNetSalary.setCellValueFactory(new PropertyValueFactory<>("netSalary"));

        try {
            resetPage();
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong during initialization.").show();
        }
        try {
            cmbEmployeeId.setItems(EmployeeModel.getAllEmployeeid());
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadTableData() throws SQLException {
        ArrayList<SalaryTM> salaryList = salaryModel.getAllSalaries();

        ObservableList<SalaryTM> salaryTMS = FXCollections.observableArrayList(salaryList);

        tblSalary.setItems(salaryTMS);
    }

    private void resetPage() {
        try {
            loadTableData();
            generateNextSalaryId();

            btnSave.setDisable(false);

            cmbEmployeeId.getSelectionModel().clearSelection();
            txtEmployeeName = "";
            txtBasicSalary = "";
            txtOtHours = "";
            txtDeduction = "";
            txtDeductionType = "";
            txtNetSalary = "";

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset page").show();
        }
    }

    private void generateNextSalaryId() throws SQLException, ClassNotFoundException {
        String nextId = salaryModel.generateNextSalaryId();
        lblSalaryId.setText(nextId);
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void saveOnAction(ActionEvent actionEvent) {



        String salaryId = lblSalaryId.getText();
        String employeeId = txtEmployeeId;
        String employeeName = txtEmployeeName;
        double basicSalary = Double.parseDouble(txtBasicSalary);
        double otHours = Double.parseDouble(txtOtHours);
        double deduction = Double.parseDouble(txtDeduction);
        String deductionType = txtDeductionType;
        double netSalary = Double.parseDouble(txtNetSalary);



        SalaryTM salary = new SalaryTM(salaryId, employeeId, employeeName, basicSalary, otHours, deduction, deductionType, netSalary);

        try {
            System.out.println("Saving Salary: " + salary);
            boolean isSaved = salaryModel.saveSalary(salary);

            if (isSaved) {
                loadTableData();
                new Alert(Alert.AlertType.INFORMATION, "Salary saved successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save salary.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while saving salary.").show();
        }
    }


    public void cmbEIDOnAction(ActionEvent actionEvent) {
        String employeeId = cmbEmployeeId.getValue();

        if (employeeId == null || employeeId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select an employee ID. "+ employeeId).show();
            return;
        }else {
            txtEmployeeId = employeeId;
        }
        System.out.println("Selected Employee ID: " + employeeId);

        try {
            String employeeName = salaryModel.getEmployeeNameById(employeeId);
            EmployeeDTO employeeDTO = EmployeeModel.getEmployeeById(employeeId);
            if (employeeDTO == null) {

                new Alert(Alert.AlertType.ERROR, "Employee not found 3333").show();
                return;
            }else {
                txtEmployeeName = employeeDTO.getFirstName();

                PositionsDTO positionsDTO = positionsModel.getPositionById(employeeDTO.getPositionId());
                System.out.println(positionsDTO);
                if (positionsDTO != null) {
                    txtBasicSalary = String.valueOf(positionsDTO.getBasicSalary());
                } else {
                    new Alert(Alert.AlertType.ERROR, "Position not found for employee").show();
                    return;
                }

                OtDTO otDTO = otModel.getOtByEmployeeId(employeeDTO.getEmployeeId());
                txtOtHours = String.valueOf(otDTO.getOvertimePayment());

                DeductionsDTO deductionsDTO = deductionsModel.getDeductionByEmployeeId(employeeDTO.getEmployeeId());
                txtDeduction = deductionsDTO.getTotalDeduction();

                txtDeductionType = "ETF"; // Default value for deduction type

                txtNetSalary = String.valueOf(Double.parseDouble(txtBasicSalary) + Double.parseDouble(txtOtHours) - Double.parseDouble(txtDeduction));
            }






        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load employee name").show();
        }
    }

}
