package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class AttendanceTM {
    private String attendanceId;
    private String employeeId;
    private Date date;
    private String checkIn;
    private String checkOut;
    private String status;



    }
