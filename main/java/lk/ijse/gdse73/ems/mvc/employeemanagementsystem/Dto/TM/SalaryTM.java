package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class SalaryTM {
    private String salaryId;
    private String employeeId;
    private double basicSalary;
    private double otPayment;
    private double deduction;
    private String deductionType;
    private double netSalary;

}
