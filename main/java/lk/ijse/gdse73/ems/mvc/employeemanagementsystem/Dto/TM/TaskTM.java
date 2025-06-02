package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class TaskTM {
    private String taskId;
    private String employeeId;
    private String description;
    private String deadline;
    private String status;

}
