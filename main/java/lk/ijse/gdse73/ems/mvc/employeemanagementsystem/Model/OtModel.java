package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.OtDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OtModel {

    public ArrayList<OtDTO> getAllOtRecords() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM OT");
        ArrayList<OtDTO> list = new ArrayList<>();
        while (rs.next()) {
            OtDTO dto = new OtDTO(
                    rs.getString("ot_id"),
                    rs.getString("employee_id"),
                    rs.getDouble("ot_hours"),
                    rs.getDouble("rate_per_hours"),
                    rs.getString("ot_date"),
                    rs.getDouble("overtime_payment")
            );
            list.add(dto);
        }
        return list;
    }

    public boolean saveOtRecord(OtDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO OT (ot_id, employee_id, ot_hours, rate_per_hours, ot_date, overtime_payment) VALUES (?, ?, ?, ?, ?, ?)",
                dto.getOtId(),
                dto.getEmployeeId(),
                dto.getOtHours(),
                dto.getRatePerHour(),
                dto.getOtDate(),
                dto.getOvertimePayment()
        );
    }

    public boolean updateOtRecord(OtDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE OT SET employee_id = ?, ot_hours = ?, rate_per_hours = ?, ot_date = ?, overtime_payment = ? WHERE ot_id = ?",
                dto.getEmployeeId(),
                dto.getOtHours(),
                dto.getRatePerHour(),
                dto.getOtDate(),
                dto.getOvertimePayment(),
                dto.getOtId()
        );
    }

    public boolean deleteOtRecord(String otId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM OT WHERE ot_id = ?", otId);
    }

    public String generateNextOtId() {
        try {
            ResultSet rs = CrudUtil.execute("SELECT ot_id FROM OT ORDER BY CAST(SUBSTRING(ot_id, 2) AS UNSIGNED) DESC LIMIT 1");
            if (rs.next()) {
                String lastId = rs.getString(1);
                int nextId = Integer.parseInt(lastId.substring(1)) + 1;
                return String.format("T%03d", nextId);
            } else {
                return "T001";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "T001";
        }
    }

    public OtDTO getOtByEmployeeId(String employeeId) {
        try {
            ResultSet rs = CrudUtil.execute("SELECT * FROM OT WHERE employee_id = ?", employeeId);
            if (rs.next()) {
                return new OtDTO(
                        rs.getString("ot_id"),
                        rs.getString("employee_id"),
                        rs.getDouble("ot_hours"),
                        rs.getDouble("rate_per_hours"),
                        rs.getString("ot_date"),
                        rs.getDouble("overtime_payment")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null; // No record found or error occurred
    }


}
