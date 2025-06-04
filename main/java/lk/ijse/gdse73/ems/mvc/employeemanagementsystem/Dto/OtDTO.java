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
    private double otHours;        // Correct type
    private double ratePerHours;   // Correct type
    private String otDate;         // Can be String or java.sql.Date
    private double overtimePayment;

    // Optional convenience method (only if needed)
    public double getRatePerHour() {
        return ratePerHours;
    }

    // Optional alias for clarity or UI binding
    public String getEmpId() {
        return employeeId;
    }
}
