package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.DeductionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.DeductionsTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.DeductionsModel;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeductionsController {

    @FXML
    private Label lblId;
    @FXML
    private ComboBox<String> cmbDeductionName;
    @FXML
    private ComboBox<String> cmbEmployeeId;
    @FXML
    private TextField txtBasicSalary;
    @FXML
    private TextField txtDeductionPercentage;
    @FXML
    private TextField txtTotalDeduction;

    @FXML
    private TableView<DeductionsTM> tblDeductions;
    @FXML
    private TableColumn<DeductionsTM, String> colDeductionId;
    @FXML
    private TableColumn<DeductionsTM, String> colEmployeeId;
    @FXML
    private TableColumn<DeductionsTM, String> colDeductionName;
    @FXML
    private TableColumn<DeductionsTM, String> colBasicSalary;
    @FXML
    private TableColumn<DeductionsTM, String> colDeductionPercentage;
    @FXML
    private TableColumn<DeductionsTM, String> colTotalDeduction;

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnSaveId;

    @FXML
    public void initialize() {

        colDeductionId.setCellValueFactory(new PropertyValueFactory<>("deductionId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDeductionName.setCellValueFactory(new PropertyValueFactory<>("deductionName"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colDeductionPercentage.setCellValueFactory(new PropertyValueFactory<>("deductionPercentage"));
        colTotalDeduction.setCellValueFactory(new PropertyValueFactory<>("totalDeduction"));


        cmbDeductionName.setItems(FXCollections.observableArrayList("ETF+EPF", "Other"));


        loadEmployeeIds();
        resetPage();

        cmbEmployeeId.setOnAction(this::employeeIdSelected);
    }

    private void loadEmployeeIds() {
        try {
            List<String> employeeIds = EmployeeModel.getAllEmployeeIds();
            cmbEmployeeId.getItems().addAll(employeeIds);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading employee IDs!").show();
        }
    }

    private void loadTableData() {
        try {
            ArrayList<DeductionsDTO> deductionsDTOArrayList = DeductionsModel.getAllDeductions();
            ObservableList<DeductionsTM> deductionsTMS = FXCollections.observableArrayList();

            for (DeductionsDTO dto : deductionsDTOArrayList) {
                DeductionsTM tm = new DeductionsTM(
                        dto.getDeductionId(),
                        dto.getEmployeeId(),
                        dto.getDeductionName(),
                        dto.getBasicSalary(),
                        dto.getDeductionPercentage(),
                        dto.getTotalDeduction()
                );
                deductionsTMS.add(tm);
            }

            tblDeductions.setItems(deductionsTMS);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load deductions!").show();
        }
    }

    private void resetPage() {
        loadTableData();
        loadNextId();

        btnSaveId.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

        cmbDeductionName.setValue("ETF+EPF");
        cmbEmployeeId.getSelectionModel().clearSelection();




    }

    private void loadNextId() {
        try {
            String nextId = DeductionsModel.getNextDeductionId();
            lblId.setText(nextId);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to generate next Deduction ID!").show();
        }
    }

    private boolean validateInputs() {
        if (cmbDeductionName.getValue() == null || cmbEmployeeId.getValue() == null ||
                txtBasicSalary.getText().isEmpty() || txtDeductionPercentage.getText().isEmpty() || txtTotalDeduction.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return false;
        }
        return true;
    }

    @FXML
    public void saveOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        try {
            double basicSalary = Double.parseDouble(txtBasicSalary.getText());
            double deductionPercentage = Double.parseDouble(txtDeductionPercentage.getText());
            double totalDeduction = basicSalary * deductionPercentage / 100;

            DeductionsDTO dto = new DeductionsDTO(
                    lblId.getText(),
                    cmbEmployeeId.getValue(),
                    cmbDeductionName.getValue(),
                    String.valueOf(basicSalary),
                    String.valueOf(deductionPercentage),
                    String.format("%.2f", totalDeduction)
            );

            boolean isSaved = DeductionsModel.saveDeduction(dto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Deduction saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save deduction!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Please enter valid numeric values for salary and deduction percentage!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while saving deduction!").show();
        }
    }

    @FXML
    public void updateOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        try {
            double basicSalary = Double.parseDouble(txtBasicSalary.getText());
            double deductionPercentage = Double.parseDouble(txtDeductionPercentage.getText());
            double totalDeduction = basicSalary * deductionPercentage / 100;

            DeductionsDTO dto = new DeductionsDTO(
                    lblId.getText(),
                    cmbEmployeeId.getValue(),
                    cmbDeductionName.getValue(),
                    String.valueOf(basicSalary),
                    String.valueOf(deductionPercentage),
                    String.format("%.2f", totalDeduction)
            );

            boolean isUpdated = DeductionsModel.updateDeduction(dto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Deduction updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update deduction!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Please enter valid numeric values for salary and deduction percentage!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An error occurred while updating deduction!").show();
        }
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        String deductionId = lblId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this deduction?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = DeductionsModel.deleteDeduction(deductionId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deduction deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete deduction!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting!").show();
            }
        }
    }

    @FXML
    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    @FXML
    public void onClickTable(MouseEvent mouseEvent) {
        DeductionsTM selected = tblDeductions.getSelectionModel().getSelectedItem();

        if (selected != null) {
            lblId.setText(selected.getDeductionId());
            cmbEmployeeId.setValue(selected.getEmployeeId());
            cmbDeductionName.setValue(selected.getDeductionName());
            txtBasicSalary.setText(selected.getBasicSalary());
            txtDeductionPercentage.setText(selected.getDeductionPercentage());
            txtTotalDeduction.setText(selected.getTotalDeduction());

            btnSaveId.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    @FXML
    public void employeeIdSelected(ActionEvent event) {
        String empId = cmbEmployeeId.getValue();

        if (empId != null && !empId.isEmpty()) {
            try {
                String basicSalary = EmployeeModel.getSalaryById(empId);
                txtBasicSalary.setText(basicSalary);

                String deductionName = cmbDeductionName.getValue();

                if ("ETF+EPF".equals(deductionName)) {
                    double percentage = 8.0;
                    txtDeductionPercentage.setText(String.valueOf(percentage));


                    double totalDeduction = Double.parseDouble(basicSalary) * percentage / 100;
                    txtTotalDeduction.setText(String.format("%.2f", totalDeduction));
                } else if ("Other".equals(deductionName)) {

                    txtDeductionPercentage.clear();
                    txtTotalDeduction.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to retrieve employee salary or deduction percentage!").show();
            }
        }
    }
}
