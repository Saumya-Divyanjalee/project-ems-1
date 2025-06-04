package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class LeavesDTO {
    private String leaveId;
    private String leaveType;
    private Date startDate;
    private Date endDate;
    private String status;
    private String employeeId;


    public LeavesDTO(String leaveId, String leaveType, String startDate, String endDate, String status, String employeeId) {
    }
}
