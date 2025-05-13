package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class SalaryTM {
    private String salaryId;
    private String basicSalary;
    private String otHours;
    private String deduction;
    private String employeeId;
    private String dtypeId;

}
