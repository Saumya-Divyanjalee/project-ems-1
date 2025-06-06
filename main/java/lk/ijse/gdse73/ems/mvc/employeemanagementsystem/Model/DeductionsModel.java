package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.DeductionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeductionsModel {

    public static String getNextDeductionId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT dtype_id FROM deductions ORDER BY dtype_id DESC LIMIT 1");
        char tableCharacter = 'D';
        if (rs.next()) {
            String lastId = rs.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableCharacter + "%03d", nextIdNumber);
        }
        return tableCharacter + "001";
    }

    public static boolean saveDeduction(DeductionsDTO dto) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        try {
            boolean isSaved = CrudUtil.execute(
                    "INSERT INTO deductions (dtype_id, employee_id, date, deduction_percentage, total_deduction, basic_salary) VALUES (?, ?, ?, ?, ?, ?)",
                    dto.getDeductionId(),
                    dto.getEmployeeId(),
                    dto.getDate(),
                    dto.getDeductionPercentage(),
                    dto.getTotalDeduction(),
                    dto.getBasicSalary()
            );

            if (isSaved) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
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
        Connection con = DBConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        try {
            boolean isUpdated = CrudUtil.execute(
                    "UPDATE deductions SET employee_id = ?, deduction_percentage = ?, total_deduction = ?, basic_salary = ? WHERE dtype_id = ?",
                    dto.getEmployeeId(),
                    dto.getDeductionPercentage(),
                    dto.getTotalDeduction(),
                    dto.getBasicSalary(),
                    dto.getDeductionId()
            );

            if (isUpdated) {
                con.commit();
                return true;
            } else {
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
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

    public DeductionsDTO getDeductionByEmployeeId(String employeeId) {
        try {
            ResultSet rs = CrudUtil.execute("SELECT dtype_id, employee_id, deduction_percentage, total_Deductions, basic_salary FROM deductions WHERE employee_id = ?", employeeId);
            if (rs.next()) {
                DeductionsDTO deductionsDTO = new DeductionsDTO();
                deductionsDTO.setEmployeeId(rs.getString("employee_id"));
                deductionsDTO.setDeductionPercentage(rs.getString("deduction_percentage"));
                deductionsDTO.setTotalDeduction(rs.getString("total_Deductions"));
                deductionsDTO.setBasicSalary(rs.getString("basic_salary"));
                return deductionsDTO;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
