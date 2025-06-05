package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.DeductionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeductionsModel {

    public static String getNextDeductionId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT dtype_id FROM deductions ORDER BY dtype_id DESC LIMIT 1");
        char tableCharacter = 'D';
        if (rs.next()) {
            String lastId = rs.getString(1);
            String lastIdNumberString = lastId.substring(1); // remove 'D'
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableCharacter + "%03d", nextIdNumber);
        }
        return tableCharacter + "001";
    }

    public static boolean saveDeduction(DeductionsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO Deductions (deduction_id, employee_id, date, deduction_percentage, total_deduction, basic_salary) VALUES (?, ?, ?, ?, ?, ?)",
                dto.getDeductionId(),
                dto.getEmployeeId(),
                dto.getDate(),
                dto.getDeductionPercentage(),
                dto.getTotalDeduction(),
                dto.getBasicSalary()  // ← Set from position
        );
    }


    public static ArrayList<DeductionsDTO> getAllDeductions() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM deductions");
        ArrayList<DeductionsDTO> list = new ArrayList<>();

        while (rs.next()) {
            DeductionsDTO dto = new DeductionsDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
            );
            list.add(dto);
        }

        return list;
    }

    public static boolean updateDeduction(DeductionsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE deductions SET employee_id = ?, type_name = ?, total_Deductions = ? WHERE dtype_id = ?",
                dto.getEmployeeId(), dto.getDeductionName(), dto.getTotalDeduction(), dto.getDeductionId()
        );
    }

    public static boolean deleteDeduction(String deductionId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM deductions WHERE dtype_id = ?", deductionId
        );
    }

    public static ArrayList<String> getAllDeductionIds() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT dtype_id FROM deductions");
        ArrayList<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }

    public static String findNameByDeductionId(String deductionId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT type_name FROM deductions WHERE dtype_id = ?", deductionId);
        if (rs.next()) {
            return rs.getString("type_name");
        }
        return null;
    }

    public static double getBasicSalaryByEmployeeId(String employeeId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute(
                "SELECT p.basic_salary FROM Positions p " +
                        "JOIN Employee e ON e.position_id = p.position_id " +
                        "WHERE e.employee_id = ?", employeeId);

        if (rs.next()) {
            return rs.getDouble("basic_salary");
        }
        return 0.0;
    }

}
