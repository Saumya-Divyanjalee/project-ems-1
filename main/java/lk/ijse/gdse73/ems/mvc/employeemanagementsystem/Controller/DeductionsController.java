package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
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
import java.util.Optional;
import java.util.ResourceBundle;

public class DeductionsController implements Initializable {

    public TextField txtDeductionName;
    public TextField txtDeductionId;

    public TableView<DeductionsTM> tblDeductions;
    public TableColumn<DeductionsTM, String> colDeductionId;
    public TableColumn<DeductionsTM, String> colDeductionName;



    private final DeductionsModel deductionsModel = new DeductionsModel();

    public Button btnAdd;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button btnSaveId;

    private final String deductionNamePattern = "^[A-Za-z ]+$";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDeductionId.setCellValueFactory(new PropertyValueFactory<>("deductionId"));
        colDeductionName.setCellValueFactory(new PropertyValueFactory<>("deductionName"));

        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }


    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        tblDeductions.setItems(FXCollections.observableArrayList(
                deductionsModel.getAllDeductions().stream().map(dto -> new DeductionsTM(
                        dto.getDeductionId(), dto.getDeductionName())).toList()
        ));
    }



    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSaveId.setDisable(false);
            btnDelete.setDisable(true);
            btnReset.setDisable(true);

            btnUpdate.setDisable(true);

            txtDeductionId.setText("");
            txtDeductionName.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String deductionId = txtDeductionId.getText();
        String deductionName = txtDeductionName.getText();

        boolean isValidName = deductionName.matches(deductionNamePattern);
        txtDeductionName.setStyle("-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtDeductionName.setStyle("-fx-border-color: red;");
            return;
        }

        DeductionsDTO dto = new DeductionsDTO(deductionId, deductionName);

        try {
            boolean isSaved = deductionsModel.saveDeduction(dto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Deduction saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save deduction!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while saving!").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String deductionId = txtDeductionId.getText();
        String deductionName = txtDeductionName.getText();

        DeductionsDTO dto = new DeductionsDTO(deductionId, deductionName);

        try {
            boolean isUpdated = deductionsModel.updateDeduction(dto);
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
        String deductionId = txtDeductionId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDeleted = deductionsModel.deleteDeduction(deductionId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deduction deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Deduction deleted successfully!").show();
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
        String nextId = deductionsModel.getNextDeductionId();
        txtDeductionId.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        DeductionsTM selected = tblDeductions.getSelectionModel().getSelectedItem();

        if (selected != null) {
            txtDeductionId.setText(selected.getDeductionId());
            txtDeductionName.setText(selected.getDeductionName());

            btnSaveId.setDisable(true);
            btnAdd.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnReset.setDisable(false);
        }
    }

    public void addOnAction(ActionEvent actionEvent) {
        saveOnAction(actionEvent);
    }
}
