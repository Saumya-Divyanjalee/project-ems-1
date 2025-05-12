package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.DBConnection;

import lombok.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@AllArgsConstructor
@Setter
@Getter
public class DBConnection {
    private static DBConnection dBConnection;

    private Connection connection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeemanagementsystem", "root", "1234");
    }

    public static DBConnection getInstance() throws ClassNotFoundException, SQLException{
        if(dBConnection == null){
            dBConnection = new DBConnection();
        }
        return dBConnection;
    }

}