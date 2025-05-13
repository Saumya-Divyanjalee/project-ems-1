package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.AttendanceDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AttendanceModel {

    public String getNextAttendanceId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT  attendance_id FROM  Attendance ORDER BY  attendance_id DESC LIMIT 1");

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
                "INSERT INTO  Attendance (attendance_id,  employee_id, date,check_in, check_out, status) VALUES (?, ?, ?, ?, ?,?)",
                dto.getAttendanceId(), dto.getEmployeeId(), dto.getDate(), dto.getCheckIn(), dto.getCheckOut(), dto.getStatus()
        );
    }

    public ArrayList<AttendanceDTO> getAllAttendance() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Attendance");
        ArrayList<AttendanceDTO> attendanceDTOArrayList = new ArrayList<>();
        while (rs.next()) {
            AttendanceDTO attendanceDTO = new AttendanceDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)

            );
            attendanceDTOArrayList.add(attendanceDTO);
        }
        return attendanceDTOArrayList;
    }

    public boolean updateAttendance(AttendanceDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Attendance SET employee_id=?, date=?, check_in=?,check_out=? , status=? WHERE attendance_id=?",

                dto.getEmployeeId(),
                dto.getDate(),
                dto.getCheckIn(),
                dto.getCheckOut(),
                dto.getStatus(),
                dto.getAttendanceId()
        );
    }

    public boolean deleteAttendance(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Attendance WHERE attendance_id=?", id);
    }

    public ArrayList<String> getAllAttendanceIds() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT attendance_id FROM Attendance"
        );
        ArrayList<String>list = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString(1);
            list.add(id);
        }
        return list;
    }

    public String findNameById(String selectedAttendanceId ) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT name FROM Attendance WHERE attendance_id=?", selectedAttendanceId
        );

        if (rs.next()) {
            return rs.getString(1);

        }
        return null;
    }



}
