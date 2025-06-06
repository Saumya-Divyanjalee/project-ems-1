package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.DBConnection.DBConnection;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.DeductionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.EmployeeDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.SalaryDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.SalaryTM;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryModel {
    public ArrayList<String> getAllEmployeeIds() {
        return null;
    }

    public ArrayList<SalaryTM> getAllSalaries() {
        ArrayList<SalaryTM> salaries = new ArrayList<>();

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Salary");

            while (resultSet.next()) {
                String salaryId = resultSet.getString("salary_id");
                String employeeId = resultSet.getString("employee_id");
                String employeeName = resultSet.getString("first_name");
                double basicSalary = resultSet.getDouble("basic_salary");
                double overtimePayment = resultSet.getDouble("overtime_payment");
                double totalDeductions = resultSet.getDouble("total_deductions");
                String deductionType = resultSet.getString("dtype_id");
                double netSalary = resultSet.getDouble("net_salary");

                SalaryTM salaryTM = new SalaryTM(
                        salaryId,
                        employeeId,
                        employeeName,
                        basicSalary,
                        overtimePayment,
                        totalDeductions,
                        deductionType,
                        netSalary
                );

                salaries.add(salaryTM);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return salaries;
    }


    public String generateNextSalaryId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT salary_id FROM Salary ORDER BY salary_id DESC LIMIT 1");
        char tableCharacter = 'S';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format(tableCharacter + "%03d", nextIdNumber);
        }
        return tableCharacter + "001";
    }

    public boolean saveSalary(SalaryTM salary) {


        try {
            return CrudUtil.execute(
                    "INSERT INTO Salary (salary_id, employee_id, first_name, basic_salary, overtime_payment, total_deductions, dtype_id, net_salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    generateNextSalaryId(),
                    salary.getEmployeeId(),
                    salary.getEmployeeName(),
                    salary.getBasicSalary(),
                    salary.getOtHours(),
                    salary.getDeduction(),
                    salary.getDeductionType(),
                    salary.getNetSalary()
            );
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSavedSalary(SalaryDTO salaryDTO, EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        con.setAutoCommit(false);

        try {

            boolean isSalarySaved = CrudUtil.execute(
                    "INSERT INTO Salary (salary_id, employee_id, first_name, basic_salary, overtime_payment, total_deductions, dtype_id, net_salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    salaryDTO.getSalaryId(),
                    salaryDTO.getEmployeeId(),
                    salaryDTO.getEmployeeName(),
                    salaryDTO.getBasicSalary(),
                    salaryDTO.getOtPayment(),
                    salaryDTO.getDeduction(),
                    salaryDTO.getDeductionType(),
                    salaryDTO.getNetSalary()
            );


            boolean isEmployeeUpdated = CrudUtil.execute(
                    "UPDATE Employee SET status = 'Paid' WHERE employee_id = ?",
                    employeeDTO.getEmployeeId()
            );

            if (isSalarySaved && isEmployeeUpdated) {
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


    public String getEmployeeNameById(String employeeId) {
        try {
            ResultSet rs = CrudUtil.execute("SELECT first_name FROM Employee WHERE employee_id = ?", employeeId);
            if (rs.next()) {
                return rs.getString("first_name");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



}
