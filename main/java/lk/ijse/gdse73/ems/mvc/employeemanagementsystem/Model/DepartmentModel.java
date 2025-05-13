package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.DepartmentDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DepartmentModel {

    public String getNextDepartmentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT department_id FROM Department ORDER BY department_id DESC LIMIT 1");
        char tableCharacter ='D';

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableCharacter + "%03d", nextIdNumber);
        }

        return tableCharacter + "001";

    }
    public boolean saveDepartment(DepartmentDTO departmentDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Department (department_id, d_name, employee_id) VALUES (?, ?, ?)",
                departmentDTO.getDepartmentId(),
                departmentDTO.getDepartmentName(),
                departmentDTO.getEmployeeId()
        );
    }


    public ArrayList<DepartmentDTO> getAllDepartment() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Department");

        ArrayList<DepartmentDTO> departmentDTOArrayList = new ArrayList<>();

        while (resultSet.next()) {
            DepartmentDTO departmentDTO = new DepartmentDTO(
                    resultSet.getString("department_id"),
                    resultSet.getString("d_name"),
                    resultSet.getString("employee_id")
            );
            departmentDTOArrayList.add(departmentDTO);
        }

        return departmentDTOArrayList;
    }



    public boolean updatedepartment(DepartmentDTO departmentDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Department SET d_name=?, employee_id=? WHERE department_id=?",
                departmentDTO.getDepartmentName(),
                departmentDTO.getEmployeeId(),
                departmentDTO.getDepartmentId()
        );
    }

    public boolean deletedepartment(String departmentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM Department WHERE department_id=?",
                departmentId
        );
    }


    public String findNameById(String departmentId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT d_name FROM Department WHERE department_id = ?",
                departmentId
        );

        if (resultSet.next()) {
            return resultSet.getString("d_name");
        }

        return null;
    }
}
