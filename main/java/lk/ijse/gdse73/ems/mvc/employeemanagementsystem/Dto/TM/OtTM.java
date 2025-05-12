package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class OtTM {
    private String ot_id;
    private int ot_hours;
    private java.sql.Date ot_date;
    private double rate_per_hours;
    private String employee_id;
    private String salary_id;


}
