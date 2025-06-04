package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class LeavesTM {
    private String leaveId;
    private String leaveType;
    private Date startDate;
    private Date endDate;
    private String status;
    private String employeeId;
}
