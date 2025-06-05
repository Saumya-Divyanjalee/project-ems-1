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


    public DeductionsDTO(String string, String string1, String string2, String string3, Object o, String string4, String string5) {
    }
}
