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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AttendanceController implements Initializable {

    public Label lblAttendanceId;
    public TextField txtDate;
    public TextField txtStatus;
    public TextField txtCheckIn;
    public TextField txtCheckOut;
    public TextField txtEmployeeId;

    public TableView<AttendanceTM> tblAttendance;
    public TableColumn<AttendanceTM, String> colAttendanceId;
    public TableColumn<AttendanceTM, String> colCheckIn;
    public TableColumn<AttendanceTM, String> colCheckOut;
    public TableColumn<AttendanceTM, String> colEmployeeId;
    public TableColumn<AttendanceTM, String> colDate;
    public TableColumn<AttendanceTM, String> colStatus;


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
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        try {

            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong.").show();
        }
    }

    private void loadTableData() throws SQLException {
        ArrayList<AttendanceDTO> attendanceDTOArrayList = attendanceModel.getAllAttendance();
        ObservableList<AttendanceTM> attendanceTMS = FXCollections.observableArrayList();

        for (AttendanceDTO attendanceDTO : attendanceDTOArrayList) {
            AttendanceTM attendanceTM = new AttendanceTM(
                    attendanceDTO.getAttendanceId(),
                    attendanceDTO.getEmployeeId(),
                    attendanceDTO.getDate(),
                    attendanceDTO.getCheckIn(),
                    attendanceDTO.getCheckOut(),
                    attendanceDTO.getStatus()
            );
            attendanceTMS.add(attendanceTM);
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

            txtEmployeeId.setText("");
            txtCheckIn.setText("");
            txtCheckOut.setText("");
            txtDate.setText("");
            txtStatus.setText("");

        }catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Something went wrong!").show();
        }
    }



    public void saveOnAction(ActionEvent actionEvent) {
        String attendanceId = lblAttendanceId.getText();
        String checkIn = txtCheckIn.getText();
        String checkOut = txtCheckOut.getText();
        String employeeId = txtEmployeeId.getText();
        String date = txtDate.getText();
        String status = txtStatus.getText();


         AttendanceDTO attendanceDTO = new AttendanceDTO(
                 attendanceId,employeeId,date,checkIn,checkOut,status
         );
         try {
             boolean isSaved = attendanceModel.saveAttendance(attendanceDTO);
             if (isSaved) {
                 resetPage();
                 new Alert(Alert.AlertType.INFORMATION, "Attendance saved successfully!").show();
             }else {
                 new Alert(Alert.AlertType.ERROR, "Oops!...Fail to save attendance!").show();
             }
         }catch (Exception e) {
             e.printStackTrace();
             new Alert(Alert.AlertType.ERROR, "Oops!...Fail to save attendance!").show();
         }
    }

    public void updateOnAction(ActionEvent actionEvent) {

        String attendanceId = lblAttendanceId.getText();
        String checkIn = txtCheckIn.getText();
        String checkOut = txtCheckOut.getText();
        String employeeId = txtEmployeeId.getText();
        String date = txtDate.getText();
        String status = txtStatus.getText();

        AttendanceDTO attendanceDTO = new AttendanceDTO(
                attendanceId,employeeId,date,checkIn,checkOut,status
        );

        try {

            boolean isUpdated = attendanceModel.updateAttendance(attendanceDTO);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Attendance Updated Successfully.!").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Oops!...Fail to update attendance!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Oops!...Fail to update attendance!").show();
        }
    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure ?",
                ButtonType.YES,
                ButtonType.NO
        );

        Optional<ButtonType> response=alert.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.YES) {
            String attendanceId = lblAttendanceId.getText();

            try {
                boolean isDeleted = attendanceModel.deleteAttendance(attendanceId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Attendance Deleted!").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
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
        AttendanceTM attendanceTM = tblAttendance.getSelectionModel().getSelectedItem();

        if (attendanceTM != null){
            lblAttendanceId.setText(attendanceTM.getAttendanceId());
            txtCheckIn.setText(attendanceTM.getCheckIn());
            txtCheckOut.setText(attendanceTM.getCheckOut());
            txtEmployeeId.setText(attendanceTM.getEmployeeId());
            txtDate.setText(attendanceTM.getDate());
            txtStatus.setText(attendanceTM.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }

    }


}
