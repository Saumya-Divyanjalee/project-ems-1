package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class SalaryDTO {
    private String salaryId;
    private String employeeId;
    private String employeeName;
    private double basicSalary;
    private double otPayment;
    private double deduction;
    private String deductionType;
    private double netSalary;




}
