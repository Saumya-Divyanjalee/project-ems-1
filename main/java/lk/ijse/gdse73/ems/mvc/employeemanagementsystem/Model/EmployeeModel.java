package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.EmployeeDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    // Get all employee IDs as ObservableList for JavaFX controls
    public static ObservableList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id");
        ObservableList<String> employeeIds = FXCollections.observableArrayList();
        while (rs.next()) {
            employeeIds.add(rs.getString("employee_id"));
        }
        return employeeIds;
    }

    // Get salary by employee ID
    public static String getSalaryById(String empId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT basic_salary FROM Employee WHERE employee_id = ?", empId);
        if (rs.next()) {
            return rs.getString("basic_salary"); // Make sure your DB has this column
        }
        return null;
    }

    // Get next employee ID (e.g., E001, E002, ...)
    public static String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id DESC LIMIT 1");
        char tableCharacter = 'E';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableCharacter + "%03d", nextIdNumber);
        }
        return tableCharacter + "001";
    }
    public static ObservableList<String> getAllEmployeeid() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id");
        ObservableList<String> employeeIds = FXCollections.observableArrayList();
        while (rs.next()) {
            employeeIds.add(rs.getString("employee_id"));
        }
        return employeeIds;
    }

    public static EmployeeDTO getEmployeeById(String employeeId) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee WHERE employee_id = ?", employeeId);
            if (resultSet.next()) {
                return new EmployeeDTO(
                        resultSet.getString(1),  // employee_id
                        resultSet.getString(2),  // first_name
                        resultSet.getString(3),  // last_name
                        resultSet.getString(4),  // department_id
                        resultSet.getString(5),  // dob
                        resultSet.getString(6),  // e_address
                        resultSet.getString(7),  // join_date
                        resultSet.getString(8),  // age
                        resultSet.getString(9),  // email
                        resultSet.getString(10), // contact
                        resultSet.getString(11),
                        resultSet.getString(12)
                );
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Save new employee
    public boolean saveEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Employee VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                dto.getEmployeeId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getDepartmentId(),
                dto.getDob(),
                dto.getEAddress(),
                dto.getJoinDate(),
                dto.getAge(),
                dto.getEmail(),
                dto.getContact(),
                dto.getPositionId()
        );
    }

    // Get all employees as a list of DTOs
    public ArrayList<EmployeeDTO> getAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employee");
        ArrayList<EmployeeDTO> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            EmployeeDTO dto = new EmployeeDTO(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12)
            );
            employeeList.add(dto);
        }
        return employeeList;
    }

    // Update existing employee
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE Employee SET first_name = ?, last_name = ?, department_id = ?, dob = ?, e_address = ?, join_date = ?, age = ?, email = ?, contact = ?, position_id = ? WHERE employee_id = ?",
                dto.getFirstName(),
                dto.getLastName(),
                dto.getDepartmentId(),
                dto.getDob(),
                dto.getEAddress(),
                dto.getJoinDate(),
                dto.getAge(),
                dto.getEmail(),
                dto.getContact(),
                dto.getPositionId(),
                dto.getEmployeeId()
        );
    }

    // Delete employee by ID
    public boolean deleteEmployee(String empId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM Employee WHERE employee_id = ?",
                empId
        );
    }

    // Find employee's first name by ID
    public String findNameById(String selectedEmpId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute(
                "SELECT first_name FROM Employee WHERE employee_id = ?",
                selectedEmpId
        );

        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}
