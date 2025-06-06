package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.PositionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.PositionsTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.PositionsModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PositionsController implements Initializable {

    public Label lblPosition;
    public TextField txtPositionTitle;
    public TextField txtSalaryGrade;
    public ComboBox<String> cmbBasic;

    public TableView<PositionsTM> tblPositions;
    public TableColumn<PositionsTM, String> colPositionId;
    public TableColumn<PositionsTM, String> colPositionTitle;
    public TableColumn<PositionsTM, String> colSalaryGrade;
    public TableColumn<PositionsTM, String> colBasicSalary;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final PositionsModel positionsModel = new PositionsModel();
    public Label lblBasic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colPositionId.setCellValueFactory(new PropertyValueFactory<>("positionId"));
        colPositionTitle.setCellValueFactory(new PropertyValueFactory<>("pTitle"));
        colSalaryGrade.setCellValueFactory(new PropertyValueFactory<>("salaryGrade"));
        colBasicSalary.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));

        cmbBasic.setItems(FXCollections.observableArrayList("25000", "30000", "35000", "40000", "50000")); // Example values

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong during initialization").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<PositionsDTO> positionsDTOArrayList = positionsModel.getAllPositions();
        ObservableList<PositionsTM> positionTMS = FXCollections.observableArrayList();

        for (PositionsDTO dto : positionsDTOArrayList) {
            positionTMS.add(new PositionsTM(
                    dto.getPositionId(),
                    dto.getPTitle(),
                    dto.getSalaryGrade(),
                    dto.getBasicSalary()
            ));
        }
        tblPositions.setItems(positionTMS);
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = positionsModel.generateNextPositionId();
        lblPosition.setText(nextId);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtPositionTitle.clear();
            txtSalaryGrade.clear();
            cmbBasic.getSelectionModel().clearSelection();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong while resetting").show();
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String positionId = lblPosition.getText();
            try {
                boolean isDeleted = positionsModel.deletePosition(positionId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Position deleted successfully.").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete position.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error while deleting position.").show();
            }
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String positionId = lblPosition.getText();
        String title = txtPositionTitle.getText();
        String grade = txtSalaryGrade.getText();
        String basicSalary = cmbBasic.getValue();

        if (title.isEmpty() || grade.isEmpty() || basicSalary == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        PositionsDTO dto = new PositionsDTO(
                positionId, title, grade, basicSalary
        );

        try {
            boolean isUpdated = positionsModel.updatePosition(dto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Position updated successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update position.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while updating position.").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        String positionId = lblPosition.getText();
        String title = txtPositionTitle.getText();
        String grade = txtSalaryGrade.getText();
        String basicSalary = cmbBasic.getValue();

        if (title.isEmpty() || grade.isEmpty() || basicSalary == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        PositionsDTO dto = new PositionsDTO(positionId, title, grade, basicSalary);

        try {
            boolean isSaved = positionsModel.savePosition(dto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Position saved successfully.").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save position.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while saving position.").show();
        }
    }

    public void onClickTable(MouseEvent mouseEvent) {
        PositionsTM selected = tblPositions.getSelectionModel().getSelectedItem();

        if (selected != null) {
            lblPosition.setText(selected.getPositionId());
            txtPositionTitle.setText(selected.getPTitle());
            txtSalaryGrade.setText(selected.getSalaryGrade());
            cmbBasic.setValue(selected.getBasicSalary());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
