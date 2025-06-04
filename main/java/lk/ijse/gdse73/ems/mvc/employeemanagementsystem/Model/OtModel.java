//package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;
//
//import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.OtDTO;
//import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//public class OtModel {
//
//    // Retrieve all OT records from the database
//    public ArrayList<OtDTO> getAllOt() throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = CrudUtil.execute("SELECT * FROM OT");
//
//        ArrayList<OtDTO> otDTOArrayList = new ArrayList<>();
//        while (resultSet.next()) {
//            OtDTO ot = new OtDTO(
//                    resultSet.getString("ot_id"),
//                    resultSet.getString("employee_id"),
//                    resultSet.getDouble("ot_hours"),
//                    resultSet.getDouble("rate_per_hours"),
//                    resultSet.getString("ot_date"),
//                    resultSet.getDouble("overtime_payment")
//            );
//            otDTOArrayList.add(ot);
//        }
//
//        return otDTOArrayList;
//    }
//
//    // Save OT record including calculated overtime payment
//    public boolean saveOt(OtDTO dto) throws SQLException, ClassNotFoundException {
//        double otHours = dto.getOtHours();
//        double ratePerHours = dto.getRatePerHours();
//        double overtimePayment = otHours * ratePerHours;
//
//        return CrudUtil.execute(
//                "INSERT INTO OT (ot_id, employee_id, ot_hours, rate_per_hours, ot_date, overtime_payment) VALUES (?, ?, ?, ?, ?, ?)",
//                dto.getOtId(),
//                dto.getEmployeeId(),
//                otHours,
//                ratePerHours,
//                dto.getOtDate(),
//                overtimePayment
//        );
//    }
//
//    // Update existing OT record including recalculated overtime payment
//    public boolean updateOt(OtDTO dto) throws SQLException, ClassNotFoundException {
//        double otHours = dto.getOtHours();
//        double ratePerHours = dto.getRatePerHours();
//        double overtimePayment = otHours * ratePerHours;
//
//        return CrudUtil.execute(
//                "UPDATE OT SET employee_id = ?, ot_hours = ?, rate_per_hours = ?, ot_date = ?, overtime_payment = ? WHERE ot_id = ?",
//                dto.getEmployeeId(),
//                otHours,
//                ratePerHours,
//                dto.getOtDate(),
//                overtimePayment,
//                dto.getOtId()
//        );
//    }
//
//    // Delete OT record by ot_id
//    public boolean deleteOt(String otId) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("DELETE FROM OT WHERE ot_id = ?", otId);
//    }
//
//    // Search for a specific OT record by ot_id
//    public OtDTO searchOt(String otId) throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = CrudUtil.execute("SELECT * FROM OT WHERE ot_id = ?", otId);
//
//        if (resultSet.next()) {
//            return new OtDTO(
//                    resultSet.getString("ot_id"),
//                    resultSet.getString("employee_id"),
//                    resultSet.getDouble("ot_hours"),
//                    resultSet.getDouble("rate_per_hours"),
//                    resultSet.getString("ot_date"),
//                    resultSet.getDouble("overtime_payment")
//            );
//        }
//        return null;
//    }
//}
