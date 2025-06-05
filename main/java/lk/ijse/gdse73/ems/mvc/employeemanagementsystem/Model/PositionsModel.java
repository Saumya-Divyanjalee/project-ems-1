package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.PositionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PositionsModel {

    // Retrieve all positions from the database
    public ArrayList<PositionsDTO> getAllPositions() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Positions");
        ArrayList<PositionsDTO> positionsDTOArrayList = new ArrayList<>();

        while (resultSet.next()) {
            PositionsDTO dto = new PositionsDTO(
                    resultSet.getString("position_id"),
                    resultSet.getString("p_title"),
                    resultSet.getString("salary_grade"),
                    resultSet.getString("basic_salary")
            );
            positionsDTOArrayList.add(dto);
        }
        return positionsDTOArrayList;
    }

    // Delete a position by its ID
    public boolean deletePosition(String positionId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM Positions WHERE position_id = ?",
                positionId
        );
    }

    // Update an existing position
    public boolean updatePosition(PositionsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Positions SET p_title = ?, salary_grade = ?, basic_salary = ? WHERE position_id = ?",
                dto.getPTitle(),
                dto.getSalaryGrade(),
                dto.getBasicSalary(),
                dto.getPositionId()
        );
    }

    // Save a new position to the database
    public boolean savePosition(PositionsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Positions (position_id, p_title, salary_grade, basic_salary) VALUES (?, ?, ?, ?)",
                dto.getPositionId(),
                dto.getPTitle(),
                dto.getSalaryGrade(),
                dto.getBasicSalary()
        );
    }

    // Generate the next position ID in the format 'P001', 'P002', etc.
    public String generateNextPositionId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT position_id FROM Positions ORDER BY position_id DESC LIMIT 1");
        char prefix = 'P';

        if (resultSet.next()) {
            String lastId = resultSet.getString("position_id");
            int lastNumericId = Integer.parseInt(lastId.substring(1));
            int nextNumericId = lastNumericId + 1;
            return String.format("%c%03d", prefix, nextNumericId);
        }

        return prefix + "001";
    }

    // Get basic salary of an employee by their employee ID
    public static double getBasicSalaryByEmployeeId(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT p.basic_salary FROM employee e JOIN Positions p ON e.position_id = p.position_id WHERE e.employee_id = ?";
        ResultSet resultSet = CrudUtil.execute(sql, employeeId);

        if (resultSet.next()) {
            return resultSet.getDouble("basic_salary");
        } else {
            throw new SQLException("Basic salary not found for employee ID: " + employeeId);
        }
    }
}
