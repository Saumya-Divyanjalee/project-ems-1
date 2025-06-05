package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class DeductionsDTO {
    private String deductionId;
    private String employeeId;
    private String deductionName;
    private String basicSalary;
    private String deductionPercentage;
    private String totalDeduction;




    public Object getDate() {
        return  null;
    }
}
