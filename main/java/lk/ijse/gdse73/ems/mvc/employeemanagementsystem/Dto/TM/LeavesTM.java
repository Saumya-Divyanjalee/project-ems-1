package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class LeavesTM {
    private String leaveId;
    private String leaveType;
    private String startDate;
    private String endDate;
    private String status;
    private String employeeId;
}
