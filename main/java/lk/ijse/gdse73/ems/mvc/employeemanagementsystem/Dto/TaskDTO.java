package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class TaskDTO {
    private String task_id;
    private String description;
    private java.sql.Date deadline;
    private String status;
    private String employee_id;



}
