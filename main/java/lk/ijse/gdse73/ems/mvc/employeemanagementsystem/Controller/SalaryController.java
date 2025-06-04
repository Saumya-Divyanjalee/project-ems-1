package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.SalaryTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.SalaryModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalaryController implements Initializable {

    public Label lblSalaryId;
    public ComboBox<String> cmbEmployeeId;
    public TextField txtEmployeeName;
    public TextField txtBasicSalary;
    public TextField txtOtHours;
    public TextField txtDeduction;
    public TextField txtDeductionType;
    public TextField txtNetSalary;

    public TableView<SalaryTM> tblSalary;
    public TableColumn<SalaryTM, String> colSalaryId;
    public TableColumn<SalaryTM, String> colEmployeeId;
    public TableColumn<SalaryTM, String> colBasicSalary;
    public TableColumn<SalaryTM, String> colOtHours;
    public TableColumn<SalaryTM, String> colDeduction;
    public TableColumn<SalaryTM, String> colDeductionType;
    public TableColumn<SalaryTM, String> colNetSalary;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final SalaryModel salaryModel = new SalaryModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colOtHours.setCellValueFactory(new PropertyValueFactory<>("otHours"));
        colDeduction.setCellValueFactory(new PropertyValueFactory<>("deduction"));
        colDeductionType.setCellValueFactory(new PropertyValueFactory<>("deductionType"));
        colNetSalary.setCellValueFactory(new PropertyValueFactory<>("netSalary"));

        try {
            resetPage();
            loadEmployeeIds();
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

    private void loadEmployeeIds() throws SQLException {

        ArrayList<String> employeeIds = salaryModel.getAllEmployeeIds();
        cmbEmployeeId.getItems().clear();
        cmbEmployeeId.getItems().addAll(employeeIds);
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
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            cmbEmployeeId.getSelectionModel().clearSelection();
            txtEmployeeName.clear();
            txtBasicSalary.clear();
            txtOtHours.clear();
            txtDeduction.clear();
            txtDeductionType.clear();
            txtNetSalary.clear();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to reset page").show();
        }
    }

    private void generateNextSalaryId() throws SQLException {
        String nextId = salaryModel.generateNextSalaryId();
        lblSalaryId.setText(nextId);
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String salaryId = lblSalaryId.getText();
        String employeeId = cmbEmployeeId.getValue();
        String employeeName = txtEmployeeName.getText();
        double basicSalary = Double.parseDouble(txtBasicSalary.getText());
        double otHours = Double.parseDouble(txtOtHours.getText());
        double deduction = Double.parseDouble(txtDeduction.getText());
        String deductionType = txtDeductionType.getText();
        double netSalary = Double.parseDouble(txtNetSalary.getText());



        SalaryTM salary = new SalaryTM(salaryId, employeeId, employeeName, basicSalary, otHours, deduction, deductionType, netSalary);

        try {
            boolean isSaved = salaryModel.saveSalary(salary);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Salary saved successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save salary.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while saving salary.").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String salaryId = lblSalaryId.getText();
        String employeeId = cmbEmployeeId.getValue();
        String employeeName = txtEmployeeName.getText();
        double basicSalary = Double.parseDouble(txtBasicSalary.getText());
        double otHours = Double.parseDouble(txtOtHours.getText());
        double deduction = Double.parseDouble(txtDeduction.getText());
        String deductionType = txtDeductionType.getText();
        double netSalary = Double.parseDouble(txtNetSalary.getText());

        SalaryTM salary = new SalaryTM(salaryId, employeeId, employeeName,basicSalary, otHours, deduction, deductionType, netSalary);

        try {
            boolean isUpdated = salaryModel.updateSalary(salary);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Salary updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update salary.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while updating salary.").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String salaryId = lblSalaryId.getText();

            try {
                boolean isDeleted = salaryModel.deleteSalary(salaryId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Salary deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete salary.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error while deleting salary.").show();
            }
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        SalaryTM selected = tblSalary.getSelectionModel().getSelectedItem();

        if (selected != null) {
            lblSalaryId.setText(selected.getSalaryId());
            cmbEmployeeId.setValue(selected.getEmployeeId());
            txtBasicSalary.setText(String.valueOf(selected.getBasicSalary()));
            txtOtHours.setText(String.valueOf(selected.getOtPayment()));
            txtDeduction.setText(String.valueOf(selected.getDeduction()));
            txtDeductionType.setText(selected.getDeductionType());
            txtNetSalary.setText(String.valueOf(selected.getNetSalary()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void cmbEIDOnAction(ActionEvent actionEvent) {

        String employeeId = cmbEmployeeId.getValue();

        try {
            String employeeName = salaryModel.getEmployeeNameById(employeeId);
            txtEmployeeName.setText(employeeName);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load employee name").show();
        }
    }

    public void txtEmployeeNameOnAction(ActionEvent actionEvent) {

    }
}
