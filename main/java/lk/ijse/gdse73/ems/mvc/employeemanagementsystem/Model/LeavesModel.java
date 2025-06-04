package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.LeavesDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeavesModel {

    public ArrayList<LeavesDTO> getAllLeaves() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Leaves");

        ArrayList<LeavesDTO> leavesList = new ArrayList<>();
        while (resultSet.next()) {
            LeavesDTO dto = new LeavesDTO(
                    resultSet.getString("leave_id"),
                    resultSet.getString("leave_type"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    resultSet.getString("status"),
                    resultSet.getString("employee_id")
            );

            leavesList.add(dto);
        }
        return leavesList;
    }

    public boolean saveLeave(LeavesDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Leaves VALUES (?, ?, ?, ?, ?, ?)",
                dto.getLeaveId(),
                dto.getLeaveType(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getStatus(),
                dto.getEmployeeId()
        );
    }

    public boolean updateLeave(LeavesDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Leaves SET leave_type=?, start_date=?, end_date=?, status=?, employee_id=? WHERE leave_id=?",
                dto.getLeaveType(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getStatus(),
                dto.getEmployeeId(),
                dto.getLeaveId()
        );
    }

    public boolean deleteLeave(String leaveId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM Leaves WHERE leave_id=?",
                leaveId
        );
    }

    public String getNextLeaveId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT leave_id FROM Leaves ORDER BY leave_id DESC LIMIT 1");

        char tableCharacter = 'L';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            int lastIdNumber = Integer.parseInt(lastId.substring(1));
            return String.format("%c%03d", tableCharacter, lastIdNumber + 1);
        }
        return tableCharacter + "001";
    }
}
