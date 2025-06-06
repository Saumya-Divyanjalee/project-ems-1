package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

import javafx.scene.control.TableColumn;
import lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM.EmployeeTM;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString


public class EmployeeDTO {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String departmentId;
    private String dob;
    private String eAddress;
    private String joinDate;
    private String age;
    private String email;
    private String contact;
    private String positionId;
    private String status = "Paid";


    public EmployeeDTO(String id, String firstName, String lastName, String deptId, String dob, String address, String joinDate, String age, String email, String contact, String positionId, TableColumn<EmployeeTM, String> colStatus) {
    }
}
