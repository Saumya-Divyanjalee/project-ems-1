package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class UserDTO {
    private String user_id;
    private String user_name;
    private String u_mobile;
    private String u_email;
    private String u_role;


}
