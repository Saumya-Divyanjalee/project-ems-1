package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.OtDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OtModel {

    public ArrayList<OtDTO> getAllOt() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM OT");

        ArrayList<OtDTO> otArrayList = new ArrayList<>();
        while (resultSet.next()) {
            OtDTO ot = new OtDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            otArrayList.add(ot);
        }

        return otArrayList;
    }

    public boolean saveOt(OtDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO OT (ot_id, ot_hours, ot_date, rate_per_hours, employee_id, salary_id) VALUES (?, ?, ?, ?, ?, ?)",
                 dto.getOtId(),
                dto.getOtHours(),
                dto.getOtDate(),
                dto.getRatePerHours(),
                dto.getEmployeeId(),
                dto.getSalaryId()
        );
    }

    public boolean updateOt(OtDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE OT SET ot_hours=?, ot_date=?, rate_per_hours=?, employee_id=?, salary_id=? WHERE ot_id=?",
                dto.getOtHours(),
                dto.getOtDate(),
                dto.getRatePerHours(),
                dto.getEmployeeId(),
                dto.getSalaryId(),
                dto.getOtId()
        );
    }

    public boolean deleteOt(String otId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM OT WHERE ot_id=?",
                otId
        );
    }

    public String getNextOtId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT ot_id FROM OT ORDER BY ot_id DESC LIMIT 1");
        char tablePrefix = 'T'; // 'O' for OT

        if (resultSet.next()) {
            String lastId = resultSet.getString("ot_id"); // e.g., O001
            int numericPart = Integer.parseInt(lastId.substring(1));
            String nextId = String.format("%s%03d", tablePrefix, numericPart + 1);
            return nextId;
        }
        return tablePrefix + "001"; // default first ID
    }
}
