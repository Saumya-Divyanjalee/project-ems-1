package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.DeductionsDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeductionsModel {


    public static String getNextDeductionId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT dtype_id FROM deductions ORDER BY dtype_id DESC LIMIT 1");
        char tableCharacter = 'C';
        if (rs.next()) {
            String lastId = rs.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNUmber);
            return nextIdString;
        }
        return tableCharacter + "001";


    }


    public static boolean saveDeduction(DeductionsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO deductions (dtype_id, type_name) VALUES (?, ?)",
                dto.getDeductionId(), dto.getDeductionName()
        );
    }


    public static ArrayList<DeductionsDTO> getAllDeductions() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM deductions");
        ArrayList<DeductionsDTO> list = new ArrayList<>();

        ArrayList<DeductionsDTO> deductionsDTOArrayList = null;
        while (rs.next()) {
            DeductionsDTO deductionsDTO = new DeductionsDTO(
                    rs.getString(1),
                    rs.getString(2)
            );
            deductionsDTOArrayList.add(deductionsDTO);
        }

        return deductionsDTOArrayList;
    }


    public static boolean updateDeduction(DeductionsDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE deductions SET type_name = ? WHERE dtype_id = ?",
                dto.getDeductionName(), dto.getDeductionId()
        );
    }


    public static boolean deleteDeduction(String deductionId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "DELETE FROM deductions WHERE dtype_id = ?", deductionId

        );
    }

    public ArrayList<String> getAllDeductionIds() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT dtype_id FROM Deductions ");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;

    }

    public String findNameByDeductionId(String selectDeductionId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT FROM Deductions WHERE dtype_id = ? ", selectDeductionId
        );
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}





