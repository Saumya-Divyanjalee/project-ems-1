package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.LeavesDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.LeavesTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.EmployeeModel;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.LeavesModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class LeavesController implements Initializable {

    public Label lblLeaveId;
    public ComboBox<String> cmbEId;
    public ComboBox<String> cmbLeaveType;
    public ComboBox<String> cmbStatus;
    public DatePicker dateStart;
    public DatePicker dateEnd;

    public TableView<LeavesTM> tblLeaves;
    public TableColumn<LeavesTM, String> colLeaveId;
    public TableColumn<LeavesTM, String> colEmployeeId;
    public TableColumn<LeavesTM, String> colLeaveType;
    public TableColumn<LeavesTM, Date> colStartDate;
    public TableColumn<LeavesTM, Date> colEndDate;
    public TableColumn<LeavesTM, String> colStatus;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final LeavesModel leavesModel = new LeavesModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colLeaveId.setCellValueFactory(new PropertyValueFactory<>("leaveId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colLeaveType.setCellValueFactory(new PropertyValueFactory<>("leaveType"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        cmbStatus.setItems(FXCollections.observableArrayList("Approved", "Rejected", "Pending"));
        cmbLeaveType.setItems(FXCollections.observableArrayList("Annual Leave", "Casual Leave", "Sick Leave"));

        try {
            cmbEId.setItems(EmployeeModel.getAllEmployeeid());
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load employee IDs.");
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<LeavesDTO> list = leavesModel.getAllLeaves();
        ObservableList<LeavesTM> tmList = FXCollections.observableArrayList();

        for (LeavesDTO dto : list) {
            LeavesTM tm = new LeavesTM(
                    dto.getLeaveId(),
                    dto.getEmployeeId(),
                    dto.getLeaveType(),
                    dto.getStartDate(),
                    dto.getEndDate(),
                    dto.getStatus()
            );
            tmList.add(tm);
        }
        tblLeaves.setItems(tmList);
    }

    private void loadNextLeaveId() throws SQLException, ClassNotFoundException {
        lblLeaveId.setText(leavesModel.getNextLeaveId());
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        loadTableData();
        loadNextLeaveId();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        cmbLeaveType.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();
        cmbEId.getSelectionModel().clearSelection();

        dateStart.setValue(null);
        dateEnd.setValue(null);
    }

    public void saveOnAction(ActionEvent actionEvent) {
        try {
            String leaveId = lblLeaveId.getText();
            String leaveType = cmbLeaveType.getValue();
            Date startDate = dateStart.getValue() != null ? Date.valueOf(dateStart.getValue()) : null;
            Date endDate = dateEnd.getValue() != null ? Date.valueOf(dateEnd.getValue()) : null;
            String status = cmbStatus.getValue();
            String employeeId = cmbEId.getValue();

            if (leaveType == null || status == null || employeeId == null || startDate == null || endDate == null) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all required fields.");
                return;
            }

            LeavesDTO dto = new LeavesDTO(leaveId, leaveType, startDate, endDate, status, employeeId);
            boolean isSaved = leavesModel.saveLeave(dto);

            if (isSaved) {
                resetPage();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Leave saved successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save leave.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Exception", "Error while saving: " + e.getMessage());
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        try {
            String leaveId = lblLeaveId.getText();
            String leaveType = cmbLeaveType.getValue();
            Date startDate = dateStart.getValue() != null ? Date.valueOf(dateStart.getValue()) : null;
            Date endDate = dateEnd.getValue() != null ? Date.valueOf(dateEnd.getValue()) : null;
            String status = cmbStatus.getValue();
            String employeeId = cmbEId.getValue();

            LeavesDTO dto = new LeavesDTO(leaveId, leaveType, startDate, endDate, status, employeeId);
            boolean isUpdated = leavesModel.updateLeave(dto);

            if (isUpdated) {
                resetPage();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Leave updated successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update leave.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Exception", "Error while updating: " + e.getMessage());
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this leave?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                String leaveId = lblLeaveId.getText();
                boolean isDeleted = leavesModel.deleteLeave(leaveId);

                if (isDeleted) {
                    resetPage();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Leave deleted successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete leave.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Exception", "Error while deleting: " + e.getMessage());
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }

    public void onClickTable(MouseEvent mouseEvent) {
        LeavesTM selected = tblLeaves.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblLeaveId.setText(selected.getLeaveId());
            cmbEId.setValue(selected.getEmployeeId());
            cmbLeaveType.setValue(selected.getLeaveType());
            dateStart.setValue(selected.getStartDate().toLocalDate());

            dateEnd.setValue(selected.getEndDate().toLocalDate());
            cmbStatus.setValue(selected.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
