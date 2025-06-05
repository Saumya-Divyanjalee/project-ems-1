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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeductionsController implements Initializable {

    public Label lblId;
    public ComboBox<String> cmbDeductionName;
    public ComboBox<String> cmbEmployeeId;
    public TextField txtBasicSalary;           // Add these TextFields to your FXML!
    public TextField txtDeductionPercentage;   // Add these TextFields to your FXML!
    public TextField txtTotalDeduction;

    public TableView<DeductionsTM> tblDeductions;
    public TableColumn<DeductionsTM, String> colDeductionId;
    public TableColumn<DeductionsTM, String> colDeductionName;
    public TableColumn<DeductionsTM, String> colTotalDeduction;
    public TableColumn<DeductionsTM, String> colEmployeeId;
    public TableColumn<DeductionsTM, String> colBasicSalary;
    public TableColumn<DeductionsTM, String> colDeductionPercentage;

    private final DeductionsModel deductionsModel = new DeductionsModel();

    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button btnSaveId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDeductionId.setCellValueFactory(new PropertyValueFactory<>("deductionId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDeductionName.setCellValueFactory(new PropertyValueFactory<>("deductionName"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        colDeductionPercentage.setCellValueFactory(new PropertyValueFactory<>("deductionPercentage"));
        colTotalDeduction.setCellValueFactory(new PropertyValueFactory<>("totalDeduction"));

        cmbDeductionName.setItems(FXCollections.observableArrayList("ETF+EPF", "Other"));

        try {
            cmbEmployeeId.setItems(EmployeeModel.getAllEmployeeid());  // Make sure this method is static or create instance accordingly
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading employee IDs!").show();
        }

        resetPage();
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
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
             ;


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong!").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String deductionId = lblId.getText();
        String deductionName = cmbDeductionName.getValue();
        String employeeId = cmbEmployeeId.getValue();
        String basicSalary = txtBasicSalary.getText();
        String deductionPercentage = txtDeductionPercentage.getText();
        String totalDeduction = txtTotalDeduction.getText();

        if (deductionName == null || employeeId == null || basicSalary.isEmpty() || deductionPercentage.isEmpty() || totalDeduction.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        DeductionsDTO dto = new DeductionsDTO(deductionId, employeeId, deductionName, basicSalary, deductionPercentage, totalDeduction);

        try {
            boolean isSaved = DeductionsModel.saveDeduction(dto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Deduction saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save deduction!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save deduction!").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String deductionId = lblId.getText();
        String deductionName = cmbDeductionName.getValue();
        String employeeId = cmbEmployeeId.getValue();
        String basicSalary = txtBasicSalary.getText();
        String deductionPercentage = txtDeductionPercentage.getText();
        String totalDeduction = txtTotalDeduction.getText();

        if (deductionName == null || employeeId == null || basicSalary.isEmpty() || deductionPercentage.isEmpty() || totalDeduction.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        DeductionsDTO dto = new DeductionsDTO(deductionId, employeeId, deductionName, basicSalary, deductionPercentage, totalDeduction);

        try {
            boolean isUpdated = DeductionsModel.updateDeduction(dto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Deduction updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update deduction!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update deduction!").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        String deductionId = lblId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", ButtonType.YES, ButtonType.NO);
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

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = DeductionsModel.getNextDeductionId();
        lblId.setText(nextId);
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

    public void employeeIdSelected(ActionEvent actionEvent) {
        String employeeId = cmbEmployeeId.getValue();

        try {
            double basicSalary = DeductionsModel.getBasicSalaryByEmployeeId(employeeId);
            txtBasicSalary.setText(String.valueOf(basicSalary));

            // Fixed 8% deduction
            double deductionPercentage = 8.0;
            txtDeductionPercentage.setText(String.valueOf(deductionPercentage));

            // Calculate total deduction
            double totalDeduction = basicSalary * deductionPercentage / 100;
            txtTotalDeduction.setText(String.format("%.2f", totalDeduction));

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading salary!").show();
        }
    }

}
