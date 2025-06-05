package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.DeductionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.DeductionsTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.DeductionsModel;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.PositionsModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeductionsController implements Initializable {

    public Label lblId;
    public ComboBox<String> cmbDeductionName;
    public ComboBox<String> cmbEmployeeId;
    public TextField txtBasicSalary;
    public TextField txtDeductionPercentage;
    public TextField txtTotalDeduction;

    public TableView<DeductionsTM> tblDeductions;
    public TableColumn<DeductionsTM, String> colDeductionId;
    public TableColumn<DeductionsTM, String> colDeductionName;
    public TableColumn<DeductionsTM, String> colTotalDeduction;
    public TableColumn<DeductionsTM, String> colEmployeeId;
    public TableColumn<DeductionsTM, String> colBasicSalary;
    public TableColumn<DeductionsTM, String> colDeductionPercentage;

    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button btnSaveId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set table column cell value factories
        colDeductionId.setCellValueFactory(new PropertyValueFactory<>("deductionId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDeductionName.setCellValueFactory(new PropertyValueFactory<>("deductionName"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colDeductionPercentage.setCellValueFactory(new PropertyValueFactory<>("deductionPercentage"));
        colTotalDeduction.setCellValueFactory(new PropertyValueFactory<>("totalDeduction"));

        // Initialize deduction names
        cmbDeductionName.setItems(FXCollections.observableArrayList("ETF+EPF", "Other"));

        // Load employee IDs into combo box
        try {
            cmbEmployeeId.setItems(EmployeeModel.getAllEmployeeid());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading employee IDs!").show();
        }

        resetPage();

        // Listen for employee selection changes to update salary and deductions automatically
        cmbEmployeeId.setOnAction(this::employeeIdSelected);
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
        try {
            loadTableData();
            loadNextId();

            btnSaveId.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            cmbDeductionName.setValue("ETF+EPF");
            cmbEmployeeId.getSelectionModel().clearSelection();

            txtBasicSalary.clear();
            txtDeductionPercentage.clear();
            txtTotalDeduction.clear();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops! Something went wrong during reset.").show();
        }
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

        // Additional validation (e.g., numeric validation) can be added here if needed

        return true;
    }

    public void saveOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        try {
            // Calculate total deduction dynamically for accuracy
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

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

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

    public void employeeIdSelected(ActionEvent event) {
        String empId = cmbEmployeeId.getValue();

        if (empId != null && !empId.isEmpty()) {
            try {
                String basicSalary = EmployeeModel.getSalaryById(empId);
                txtBasicSalary.setText(basicSalary);

                String deductionName = cmbDeductionName.getValue();

                if ("ETF+EPF".equals(deductionName)) {
                    // Example fixed percentage or retrieve from PositionsModel if available
                    double percentage = 8.0; // or fetch from PositionsModel if required
                    txtDeductionPercentage.setText(String.valueOf(percentage));

                    // Calculate total deduction
                    double totalDeduction = Double.parseDouble(basicSalary) * percentage / 100;
                    txtTotalDeduction.setText(String.format("%.2f", totalDeduction));
                } else if ("Other".equals(deductionName)) {
                    // Clear or allow manual input for other deductions
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
