package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.UserDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {

    public static String getNextUserId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT user_id FROM user ORDER BY user_id DESC LIMIT 1");
        char tableCharacter= 'U';

        if (rs.next()) {
            String lastId = rs.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextId = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d",nextId);
            return nextIdString;
        }
        return tableCharacter + "001";
    }

    public static boolean saveUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO user VALUES (?, ?, ?, ?, ?)",
                dto.getUser_id(), dto.getUser_name(), dto.getU_mobile(), dto.getU_email(), dto.getU_role()
        );
    }

    public static ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM user");
        ArrayList<UserDTO> userDTOArrayList = new ArrayList<>();

        while (rs.next()) {
            UserDTO userDTO = new UserDTO(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
            );
userDTOArrayList.add(userDTO);
        }

        return userDTOArrayList;
    }


    public static boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE user SET user_name = ?, u_mobile = ?, u_email = ?, u_role = ? WHERE user_id = ?",
                dto.getUser_name(), dto.getU_mobile(), dto.getU_email(), dto.getU_role(), dto.getUser_id()
        );
    }

    public static boolean deleteUser(String userId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM user WHERE user_id = ?", userId);
    }

    public ArrayList<String> getAllUserIds() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT user_id FROM user");
        ArrayList<String> list = new ArrayList<>();
        while (rs.next()) {
            String userId = rs.getString(1);
            list.add(userId);
        }
        return list;
    }




    public  String findUserById(String userId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM user WHERE user_id = ?", userId);

        if (rs.next()) {
            return rs.getString(1);
        }

        return null;
    }
}
