package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

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



}
