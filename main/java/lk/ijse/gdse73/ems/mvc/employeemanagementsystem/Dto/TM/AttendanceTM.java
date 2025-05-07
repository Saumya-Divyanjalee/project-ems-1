package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class AttendanceTM {
    private String attendanceId;
    private String employeeId;
    private String date;
    private String checkIn;
    private String checkOut;



    }
