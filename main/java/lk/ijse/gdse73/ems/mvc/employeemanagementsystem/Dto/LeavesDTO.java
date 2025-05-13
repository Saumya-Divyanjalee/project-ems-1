package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class LeavesDTO {
    private String leaveId;
    private String leaveType;
    private String startDate;
    private String endDate;
    private String status;
    private String employeeId;



}
