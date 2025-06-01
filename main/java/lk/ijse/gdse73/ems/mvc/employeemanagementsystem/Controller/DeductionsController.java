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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeductionsController implements Initializable {

    public Label lblId;
    public ComboBox<String> cmbDeductionName;

    public TableView<DeductionsTM> tblDeductions;
    public TableColumn<DeductionsTM, String> colDeductionId;
    public TableColumn<DeductionsTM, String> colDeductionName;

    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button btnSaveId;

    private final DeductionsModel deductionsModel = new DeductionsModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDeductionId.setCellValueFactory(new PropertyValueFactory<>("deductionId"));
        colDeductionName.setCellValueFactory(new PropertyValueFactory<>("deductionName"));

        cmbDeductionName.setItems(FXCollections.observableArrayList("ETF", "EPF"));
        cmbDeductionName.setValue("ETF");

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong!").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<DeductionsDTO> deductionsDTOArrayList = DeductionsModel.getAllDeductions();
        ObservableList<DeductionsTM> deductionsTMS = FXCollections.observableArrayList();

        for (DeductionsDTO dto : deductionsDTOArrayList) {
            DeductionsTM tm = new DeductionsTM(dto.getDeductionId(), dto.getDeductionName());
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

            cmbDeductionName.setValue("ETF");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong!").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String deductionId = lblId.getText();
        String deductionName = cmbDeductionName.getValue();

        if (deductionName == null || deductionName.isEmpty()) {
            cmbDeductionName.setStyle("-fx-border-color: red;");
            return;
        }

        cmbDeductionName.setStyle("-fx-border-color: #7367F0;");

        DeductionsDTO dto = new DeductionsDTO(deductionId, deductionName);

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

        if (deductionName == null || deductionName.isEmpty()) {
            cmbDeductionName.setStyle("-fx-border-color: red;");
            return;
        }

        cmbDeductionName.setStyle("-fx-border-color: #7367F0;");

        DeductionsDTO dto = new DeductionsDTO(deductionId, deductionName);

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
            cmbDeductionName.setValue(selected.getDeductionName());

            btnSaveId.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
