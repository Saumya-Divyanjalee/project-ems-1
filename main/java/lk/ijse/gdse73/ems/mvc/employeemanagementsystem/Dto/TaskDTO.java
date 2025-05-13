package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class TaskDTO {
    private String taskId;
    private String description;
    private String deadline;
    private String status;
    private String employeeId;



}
