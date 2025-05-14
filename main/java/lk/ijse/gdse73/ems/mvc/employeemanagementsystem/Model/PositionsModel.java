package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.PositionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PositionsModel {

    public ArrayList<PositionsDTO> getAllPositions() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Positions");
        ArrayList<PositionsDTO> positionsDTOArrayList = new ArrayList<>();

        while (resultSet.next()) {
            PositionsDTO dto = new PositionsDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            );
            positionsDTOArrayList.add(dto);
        }
        return positionsDTOArrayList;
    }

    public boolean deletePosition(String positionId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM Positions WHERE position_id = ?",
                positionId
        );
    }

    public boolean updatePosition(PositionsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Positions SET p_title = ?, salary_grade = ? WHERE position_id = ?",
                dto.getPTitle(),
                dto.getSalaryGrade(),
                dto.getPositionId()
        );
    }

    public boolean savePosition(PositionsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Positions VALUES (?, ?, ?)",
                dto.getPositionId(),
                dto.getPTitle(),
                dto.getSalaryGrade()
        );
    }

    public String getNextPositionId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT position_id FROM Positions ORDER BY position_id DESC LIMIT 1");
        char tableCharacter = 'P';

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1; // 2
            return String.format(tableCharacter + "%03d", nextIdNUmber);
        }

        return tableCharacter + "001";
    }
}
