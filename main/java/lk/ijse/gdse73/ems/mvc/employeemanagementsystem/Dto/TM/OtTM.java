package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OtTM {
    private String otId;
    private String employeeId;
    private String otHours;
    private String ratePerHours;
    private String otDate;
    private String overtimePayment;


    public OtTM(String otId, String employeeId, String otHours, String ratePerHours, String string, double payment) {
    }
}
