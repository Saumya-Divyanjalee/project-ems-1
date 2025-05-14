package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.OtDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OtModel {

    public ArrayList<OtDTO> getAllOt() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM OT");

        ArrayList<OtDTO> otDTOArrayList = new ArrayList<>();
        while (resultSet.next()) {
            OtDTO ot = new OtDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            otDTOArrayList.add(ot);
        }

        return otDTOArrayList;
    }

    public boolean saveOt(OtDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO OT VALUES (?, ?, ?, ?, ?, ?)",
                dto.getOtId(),
                dto.getEmployeeId(),
                dto.getSalaryId(),
                dto.getOtHours(),
                dto.getRatePerHours(),
                dto.getOtDate()
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
        char tableCharacter = 'T';

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNUmber);
            return nextIdString;
        }
        return tableCharacter + "001";
    }
}
