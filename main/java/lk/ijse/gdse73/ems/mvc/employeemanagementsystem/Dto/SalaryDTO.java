package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class SalaryDTO {
    private String salary_id;
    private double basic_salary;
    private int ot_hours;
    private double deduction;
    private String employee_id;
    private String dtype_id;



}
