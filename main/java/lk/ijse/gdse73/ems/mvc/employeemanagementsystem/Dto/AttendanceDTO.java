package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString


public class AttendanceDTO {
    private String attendanceId;
    private String employeeId;
    private String date;
    private String checkIn;
    private String checkOut;
    private String status;



}
