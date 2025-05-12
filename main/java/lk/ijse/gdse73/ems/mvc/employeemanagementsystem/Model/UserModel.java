package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Model;

import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.UserDTO;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {

    public static String getNextUserId() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT user_id FROM users ORDER BY user_id DESC LIMIT 1");
        char prefix = 'U';

        if (rs.next()) {
            String lastId = rs.getString(1);
            int number = Integer.parseInt(lastId.substring(1));
            number++;
            return String.format(prefix + "%03d", number);
        }
        return prefix + "001";
    }

    public static boolean saveUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO users (user_id, user_name, u_mobile, u_email, u_role) VALUES (?, ?, ?, ?, ?)",
                dto.getUser_id(), dto.getUser_name(), dto.getU_mobile(), dto.getU_email(), dto.getU_role()
        );
    }

    public static boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE users SET user_name = ?, u_mobile = ?, u_email = ?, u_role = ? WHERE user_id = ?",
                dto.getUser_name(), dto.getU_mobile(), dto.getU_email(), dto.getU_role(), dto.getUser_id()
        );
    }

    public static boolean deleteUser(String userId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM users WHERE user_id = ?", userId);
    }

    public static ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM users");
        ArrayList<UserDTO> userList = new ArrayList<>();

        while (rs.next()) {
            userList.add(new UserDTO(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("u_mobile"),
                    rs.getString("u_email"),
                    rs.getString("u_role")
            ));
        }

        return userList;
    }



    public static UserDTO findUserById(String userId) throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM users WHERE user_id = ?", userId);

        if (rs.next()) {
            return new UserDTO(
                    rs.getString("user_id"),
                    rs.getString("user_name"),
                    rs.getString("u_mobile"),
                    rs.getString("u_email"),
                    rs.getString("u_role")
            );
        }

        return null;
    }
}
