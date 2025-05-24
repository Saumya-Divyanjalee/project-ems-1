package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class EmployeeTM {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String departmentId;
    private Date dob;
    private String eAddress;
    private Date joinDate;
    private String age;
    private String email;
    private String contact;
    private String positionId;


}
