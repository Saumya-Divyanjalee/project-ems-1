module lk.ijse.gdse73.ems.mvc.employeemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires net.sf.jasperreports.core;
    requires javax.mail;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;
    requires javafx.swing;



    opens lk.ijse.gdse73.ems.mvc.employeemanagementsystem to javafx.fxml;
    exports lk.ijse.gdse73.ems.mvc.employeemanagementsystem;

    exports lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;
    opens lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller to javafx.fxml;
    exports lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;
    opens lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM to javafx.fxml;

}