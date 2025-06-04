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

    public DatePicker dateStart;
    public DatePicker dateEnd;

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
            new Alert(Alert.AlertType.ERROR, "Initialization failed!").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<LeavesDTO> list = leavesModel.getAllLeaves();
        ObservableList<LeavesTM> tmList = FXCollections.observableArrayList();

        for (LeavesDTO dto : list) {
            LeavesTM tm = new LeavesTM(
                    dto.getLeaveId(),
                    dto.getLeaveType(),
                    dto.getStartDate(),
                    dto.getEndDate(),
                    dto.getStatus(),
                    dto.getEmployeeId()
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
        String leaveId = lblLeaveId.getText();
        String leaveType = cmbLeaveType.getValue();
        Date startDate = (dateStart.getValue() != null) ? Date.valueOf(dateStart.getValue()) : null;
        Date endDate = (dateEnd.getValue() != null) ? Date.valueOf(dateEnd.getValue()) : null;
        String status = cmbStatus.getValue();
        String employeeId = cmbEId.getValue();

        if (leaveType == null || status == null || employeeId == null || startDate == null || endDate == null) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields!").show();
            return;
        }

        LeavesDTO dto = new LeavesDTO(leaveId, leaveType, startDate, endDate, status, employeeId);

        try {
            boolean isSaved = leavesModel.saveLeave(dto);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Leave saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save leave.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while saving.").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String leaveId = lblLeaveId.getText();
        String leaveType = cmbLeaveType.getValue();
        Date startDate = (dateStart.getValue() != null) ? Date.valueOf(dateStart.getValue()) : null;
        Date endDate = (dateEnd.getValue() != null) ? Date.valueOf(dateEnd.getValue()) : null;
        String status = cmbStatus.getValue();
        String employeeId = cmbEId.getValue();

        LeavesDTO dto = new LeavesDTO(leaveId, leaveType, startDate, endDate, status, employeeId);

        try {
            boolean isUpdated = leavesModel.updateLeave(dto);

            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Leave updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update leave.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error while updating.").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure to delete this leave?",
                ButtonType.YES,
                ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String leaveId = lblLeaveId.getText();

            try {
                boolean isDeleted = leavesModel.deleteLeave(leaveId);

                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Leave deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete leave.").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error while deleting.").show();
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
}
