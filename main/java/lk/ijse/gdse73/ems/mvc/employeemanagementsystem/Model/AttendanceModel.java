package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.AttendanceDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AttendanceModel {

    public String getNextAttendanceId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT attendanceId FROM attendance ORDER BY attendanceId DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            int newId = Integer.parseInt(lastId.replace("A", "")) + 1;
            return String.format("A%03d", newId);
        } else {
            return "A001";
        }
    }

    public boolean saveAttendance(AttendanceDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO attendance (attendanceId, employeeId, date, checkIn, checkOut) VALUES (?, ?, ?, ?, ?)",
                dto.getAttendanceId(), dto.getEmployeeId(), dto.getDate(), dto.getCheckIn(), dto.getCheckOut()
        );
    }

    public boolean updateAttendance(AttendanceDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE attendance SET employeeId=?, date=?, checkIn=?, checkOut=? WHERE attendanceId=?",
                dto.getEmployeeId(), dto.getDate(), dto.getCheckIn(), dto.getCheckOut(), dto.getAttendanceId()
        );
    }

    public boolean deleteAttendance(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM attendance WHERE attendanceId=?", id);
    }

    public List<AttendanceDTO> getAllAttendance() {
        return List.of();
    }



}
