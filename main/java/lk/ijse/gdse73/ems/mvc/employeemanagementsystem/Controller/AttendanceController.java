package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.AttendanceDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.AttendanceTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model.AttendanceModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AttendanceController implements Initializable {

    public Label lblAttendanceId;
    public TextField txtEmployeeId;
    public TextField txtCheckIn;
    public TextField txtCheckOut;
    public ComboBox<String> comboboxStatus;
    public DatePicker datePickerDate;

    public TableView<AttendanceTM> tblAttendance;
    public TableColumn<AttendanceTM, String> colAttendanceId;
    public TableColumn<AttendanceTM, String> colEmployeeId;
    public TableColumn<AttendanceTM, Date> colDate;
    public TableColumn<AttendanceTM, String> colCheckIn;
    public TableColumn<AttendanceTM, String> colCheckOut;
    public TableColumn<AttendanceTM, String> colStatus;

    private final AttendanceModel attendanceModel = new AttendanceModel();

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAttendanceId.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        comboboxStatus.setItems(FXCollections.observableArrayList("Present", "Absent"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong.").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AttendanceDTO> attendanceDTOArrayList = attendanceModel.getAllAttendance();
        ObservableList<AttendanceTM> attendanceTMS = FXCollections.observableArrayList();

        for (AttendanceDTO dto : attendanceDTOArrayList) {
            attendanceTMS.add(new AttendanceTM(
                    dto.getAttendanceId(),
                    dto.getEmployeeId(),
                    dto.getDate(),
                    dto.getCheckIn(),
                    dto.getCheckOut(),
                    dto.getStatus()
            ));
        }
        tblAttendance.setItems(attendanceTMS);
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtEmployeeId.clear();
            datePickerDate.setValue(null);
            txtCheckIn.clear();
            txtCheckOut.clear();
            comboboxStatus.getSelectionModel().clearSelection();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong!").show();
        }
    }

    public void saveOnAction(ActionEvent actionEvent) {
        try {
            String attendanceId = lblAttendanceId.getText();
            String employeeId = txtEmployeeId.getText();
            Date date = datePickerDate.getValue() != null ? Date.valueOf(datePickerDate.getValue()) : null;
            String checkIn = txtCheckIn.getText();
            String checkOut = txtCheckOut.getText();
            String status = comboboxStatus.getValue();

            AttendanceDTO dto = new AttendanceDTO(attendanceId, employeeId, date, checkIn, checkOut, status);

            boolean isSaved = attendanceModel.saveAttendance(dto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Attendance saved successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save attendance!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Exception occurred while saving attendance!").show();
        }
    }

    public void updateOnAction(ActionEvent actionEvent) {
        try {
            String attendanceId = lblAttendanceId.getText();
            String employeeId = txtEmployeeId.getText();
            Date date = datePickerDate.getValue() != null ? Date.valueOf(datePickerDate.getValue()) : null;
            String checkIn = txtCheckIn.getText();
            String checkOut = txtCheckOut.getText();
            String status = comboboxStatus.getValue();

            AttendanceDTO dto = new AttendanceDTO(attendanceId, employeeId, date, checkIn, checkOut, status);

            boolean isUpdated = attendanceModel.updateAttendance(dto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Attendance updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update attendance!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Exception occurred while updating attendance!").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String attendanceId = lblAttendanceId.getText();

            try {
                boolean isDeleted = attendanceModel.deleteAttendance(attendanceId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Attendance deleted successfully!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete attendance!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Exception occurred while deleting attendance!").show();
            }
        }
    }

    public void resetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = attendanceModel.getNextAttendanceId();
        lblAttendanceId.setText(nextId);
    }

    public void onClickTable(MouseEvent mouseEvent) {
        AttendanceTM selected = tblAttendance.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblAttendanceId.setText(selected.getAttendanceId());
            txtEmployeeId.setText(selected.getEmployeeId());
            datePickerDate.setValue(LocalDate.parse(selected.getDate().toString()));
            txtCheckIn.setText(selected.getCheckIn());
            txtCheckOut.setText(selected.getCheckOut());
            comboboxStatus.setValue(selected.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
