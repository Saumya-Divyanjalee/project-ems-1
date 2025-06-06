package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OtDTO {
    private String otId;
    private String employeeId;
    private double otHours;
    private double ratePerHours;
    private String otDate;
    private double overtimePayment;


    public Object getRatePerHour() {
        return ratePerHours;
    }
}
