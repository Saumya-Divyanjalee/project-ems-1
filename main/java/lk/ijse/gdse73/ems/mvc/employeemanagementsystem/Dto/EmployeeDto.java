package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString


public class EmployeeDto {
    private String employee_id;
    private String first_name;
    private String last_name;
    private java.sql.Date dob;
    private int age;
    private String email;
    private String contact;
    private String e_address;
    private java.sql.Date join_date;
    private String department_id;
    private String position_id;


  }
