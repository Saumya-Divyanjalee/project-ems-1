package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.EmployeeDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {

    public static ObservableList<String> getAllEmployeeid() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("select employee_id from Employee");
        ObservableList<String> employeeid = FXCollections.observableArrayList();
        while(rs.next()){
            employeeid.add(rs.getString("employee_id"));
        }
        return employeeid;
    }

    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id DESC LIMIT 1");
        char tableCharacter = 'E';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int  nextIdNUmber = lastIdNumber + 1;
            return String.format(tableCharacter + "%03d", nextIdNUmber);

        }
        return tableCharacter + "001";
    }

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
                    resultSet.getString(11)
            );
            employeeList.add(dto);
        }

        return employeeList;
    }

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

    public boolean deleteEmployee(String empId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM Employee WHERE employee_id = ?",
                empId
        );
    }






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

    public ObservableList<String> getAllEmployeeIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT employee_id FROM Employee ORDER BY employee_id ");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }

        ObservableList<String> employeeIds = FXCollections.observableArrayList();
        employeeIds.addAll(list);
        return employeeIds;
    }
}
