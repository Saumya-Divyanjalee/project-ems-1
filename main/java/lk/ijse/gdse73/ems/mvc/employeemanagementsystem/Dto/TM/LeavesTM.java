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


    public LeavesTM(String leaveId, String employeeId, String leaveType, Date startDate, Date endDate, String status) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }




}
