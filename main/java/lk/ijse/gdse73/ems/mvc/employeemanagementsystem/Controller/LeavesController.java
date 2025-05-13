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
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.LeavesModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class LeavesController implements Initializable {

    public Label lblLeaveId;
    public TextField txtEmployeeId;
    public TextField txtLeaveType;
    public TextField txtStartDate;
    public TextField txtEndDate;
    public TextField txtStatus;

    public TableView<LeavesTM> tblLeaves;
    public TableColumn<LeavesTM, String> colLeaveId;
    public TableColumn<LeavesTM, String> colEmployeeId;
    public TableColumn<LeavesTM, String> colLeaveType;
    public TableColumn<LeavesTM, java.sql.Date> colStartDate;
    public TableColumn<LeavesTM, java.sql.Date> colEndDate;
    public TableColumn<LeavesTM, String> colStatus;



    private final LeavesModel leavesModel = new LeavesModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colLeaveId.setCellValueFactory(new PropertyValueFactory<>("leaveId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colLeaveType.setCellValueFactory(new PropertyValueFactory<>("leaveType"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<LeavesDTO> leavesDTOArrayList = leavesModel.getAllLeaves();
        ObservableList<LeavesTM> leavesTMS = FXCollections.observableArrayList();

        for (LeavesDTO dto : leavesDTOArrayList) {
            LeavesTM leavesTM = new LeavesTM(
                    dto.getLeaveId(),
                    dto.getLeaveType(),
                    dto.getStartDate(),
                    dto.getEndDate(),
                    dto.getStatus(),
                    dto.getEmployeeId()
            );
            leavesTMS.add(leavesTM);
        }

        tblLeaves.setItems(leavesTMS);
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        loadTableData();
        loadNextLeaveId();

        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        txtEmployeeId.setText("");
        txtLeaveType.setText("");
        txtStartDate.setText("");
        txtEndDate.setText("");
        txtStatus.setText("");


    }
    public void saveOnAction(ActionEvent actionEvent) {
        String leaveId = lblLeaveId.getText();
        String leaveType = txtLeaveType.getText();
        String startDate = txtStartDate.getText();
        String endDate = txtEndDate.getText();
        String status = txtStatus.getText();
        String employeeId = txtEmployeeId.getText();

        LeavesDTO dto = new LeavesDTO(leaveId,leaveType,startDate,endDate,status,employeeId);

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
            new Alert(Alert.AlertType.ERROR, "Invalid input or save error.").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        String leaveId = lblLeaveId.getText();
        String leaveType = txtLeaveType.getText();
        String startDate = txtStartDate.getText();
        String endDate = txtEndDate.getText();
        String status = txtStatus.getText();
        String employeeId = txtEmployeeId.getText();

        LeavesDTO dto = new LeavesDTO(leaveId,leaveType,startDate,endDate,status,employeeId);



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
            new Alert(Alert.AlertType.ERROR, "Error during update.").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure to delete this record?",
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
                new Alert(Alert.AlertType.ERROR, "Error during deletion.").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       resetPage();
    }

    private void loadNextLeaveId() throws SQLException, ClassNotFoundException {
        String nextId = leavesModel.getNextLeaveId();
        lblLeaveId.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        LeavesTM selected = tblLeaves.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblLeaveId.setText(selected.getLeaveId());
            txtEmployeeId.setText(selected.getEmployeeId());
            txtLeaveType.setText(selected.getLeaveType());
            txtStartDate.setText(String.valueOf(selected.getStartDate()));
            txtEndDate.setText(String.valueOf(selected.getEndDate()));
            txtStatus.setText(selected.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
