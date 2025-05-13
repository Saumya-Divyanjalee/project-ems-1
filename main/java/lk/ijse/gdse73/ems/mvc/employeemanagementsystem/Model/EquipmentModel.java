package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.EquipmentDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipmentModel {

    public String getNextEquipmentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT equipment_id FROM Equipment ORDER BY equipment_id DESC LIMIT 1");
        char tableCharacter = 'Q';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableCharacter + "%03d", nextIdNumber);
        }
        return tableCharacter + "001";
    }


    public boolean saveEquipment(EquipmentDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Equipment (equipment_id, equipment_name, employee_id) VALUES (?, ?, ?)",
                dto.getEquipmentId(),
                dto.getEquipmentName(),
                dto.getEmployeeId()
        );
    }

    public ArrayList<EquipmentDTO> getAllEquipment() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Equipment");
        ArrayList<EquipmentDTO> equipmentDTOArrayList = new ArrayList<>();

        while (resultSet.next()) {
            EquipmentDTO dto = new EquipmentDTO(
                    resultSet.getString("equipment_id"),
                    resultSet.getString("equipment_name"),
                    resultSet.getString("employee_id")
            );
            equipmentDTOArrayList.add(dto);
        }
        return equipmentDTOArrayList;
    }


    public boolean updateEquipment(EquipmentDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Equipment SET equipment_name = ?, employee_id = ? WHERE equipment_id = ?",
                dto.getEquipmentName(),
                dto.getEmployeeId(),
                dto.getEquipmentId()
        );
    }

    public boolean deleteEquipment(String equipmentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM Equipment WHERE equipment_id = ?",
                equipmentId
        );
    }
}
