package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto.TM;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class TaskTM {
    private String task_id;
    private String description;
    private java.sql.Date deadline;
    private String status;
    private String employee_id;
}
