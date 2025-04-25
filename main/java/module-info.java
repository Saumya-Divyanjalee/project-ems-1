module lk.ijse.gdse73.ems.mvc.employeemanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens lk.ijse.gdse73.ems.mvc.employeemanagementsystem to javafx.fxml;
    exports lk.ijse.gdse73.ems.mvc.employeemanagementsystem;

    exports lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller;
    opens lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Controller to javafx.fxml;

}