package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.OtDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OtModel {

    // Get all OT records from the database
    public ArrayList<OtDTO> getAllOt() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM OT");

        ArrayList<OtDTO> otDTOArrayList = new ArrayList<>();
        while (resultSet.next()) {
            OtDTO ot = new OtDTO(
                    resultSet.getString("ot_id"),
                    resultSet.getString("employee_id"),
                    resultSet.getString("ot_hours"),
                    resultSet.getString("rate_per_hours"),
                    resultSet.getString("ot_date"),
                    resultSet.getString("overtime_payment") // Assuming your table has this column
            );
            otDTOArrayList.add(ot);
        }

        return otDTOArrayList;
    }

    // Save OT record including calculated overtime payment
    public boolean saveOt(OtDTO dto) throws SQLException, ClassNotFoundException {
        // Calculate overtime payment from otHours and ratePerHours
        double otHours = Double.parseDouble(dto.getOtHours());
        double ratePerHours = Double.parseDouble(dto.getRatePerHours());
        double overtimePayment = otHours * ratePerHours;

        return CrudUtil.execute(
                "INSERT INTO OT (ot_id, employee_id, salary_id, ot_hours, rate_per_hours, ot_date, overtime_payment) VALUES (?, ?, ?, ?, ?, ?, ?)",
                dto.getOtId(),
                dto.getEmployeeId(),

                dto.getOtHours(),
                dto.getRatePerHours(),
                dto.getOtDate(),
                String.valueOf(overtimePayment)
        );
    }

    // Update OT record including recalculated overtime payment
    public boolean updateOt(OtDTO dto) throws SQLException, ClassNotFoundException {
        double otHours = Double.parseDouble(dto.getOtHours());
        double ratePerHours = Double.parseDouble(dto.getRatePerHours());
        double overtimePayment = otHours * ratePerHours;

        return CrudUtil.execute(
                "UPDATE OT SET ot_hours=?, ot_date=?, rate_per_hours=?, employee_id=?, salary_id=?, overtime_payment=? WHERE ot_id=?",
                dto.getOtHours(),
                dto.getOtDate(),
                dto.getRatePerHours(),
                dto.getEmployeeId(),

                String.valueOf(overtimePayment),
                dto.getOtId()
        );
    }

    // Delete OT record by otId
    public boolean deleteOt(String otId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM OT WHERE ot_id=?",
                otId
        );
    }

    // Generate the next OT id
    public String getNextOtId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT ot_id FROM OT ORDER BY ot_id DESC LIMIT 1");
        char tableCharacter = 'T';

        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            return String.format("%c%03d", tableCharacter, nextIdNumber);
        }
        return tableCharacter + "001";
    }

    public String generateNextOtId() {
        return null;
    }
}
