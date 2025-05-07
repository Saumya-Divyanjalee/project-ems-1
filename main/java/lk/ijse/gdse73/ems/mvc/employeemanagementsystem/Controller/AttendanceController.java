package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.AttendanceDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.AttendanceTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.AttendanceModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AttendanceController implements Initializable {

    public TextField txtAttendanceId;
    public DatePicker datePicker;
    public TextField txtCheckIn;
    public TextField txtCheckOut;
    public TextField txtEmployeeId;

    public TableView<AttendanceTM> tblAttendance;
    public TableColumn<AttendanceTM, String> colAttendanceId;
    public TableColumn<AttendanceTM, String> colCheckIn;
    public TableColumn<AttendanceTM, String> colCheckOut;
    public TableColumn<AttendanceTM, String> colEmployeeId;
    public TableColumn<AttendanceTM, String> colDate;
    public TableColumn<AttendanceTM, String> statusId;

    private final AttendanceModel attendanceModel = new AttendanceModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAttendanceId.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusId.setCellValueFactory(new PropertyValueFactory<>("statusId"));

        try {
            loadTableData();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong.").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
         tblAttendance.setItems(FXCollections.observableArrayList(
                 attendanceModel.getAllAttendance().stream().map(
                          attendanceDTO -> new AttendanceTM(
                                  attendanceDTO.getAttendanceId(),
                                  attendanceDTO.getEmployeeId(),
                                  attendanceDTO.getDate(),
                                  attendanceDTO.getCheckIn(),
                                  attendanceDTO.getCheckOut()

                          )
                 ).toList()
         ));
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        txtAttendanceId.setText(attendanceModel.getNextAttendanceId());
    }

    public void saveOnAction(ActionEvent actionEvent) {
        try {
            AttendanceDTO dto = getInputData();
            boolean isSaved = attendanceModel.saveAttendance(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Attendance Saved!").show();
                loadTableData();
                loadNextId();
                resetPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save attendance!").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        try {
            AttendanceDTO dto = getInputData();
            boolean isUpdated = attendanceModel.updateAttendance(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Attendance Updated!").show();
                loadTableData();
                resetPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to update!").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
            String id = txtAttendanceId.getText();
            boolean isDeleted = attendanceModel.deleteAttendance(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Attendance Deleted!").show();
                loadTableData();
                loadNextId();
                resetPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private AttendanceDTO getInputData() {
        return new AttendanceDTO(
                txtAttendanceId.getText(),
                txtEmployeeId.getText(),
                datePicker.getValue().toString(),
                txtCheckIn.getText(),
                txtCheckOut.getText()
        );
    }

    private void resetPage() {
        txtEmployeeId.clear();
        txtCheckIn.clear();
        txtCheckOut.clear();
        datePicker.setValue(null);
    }
}
