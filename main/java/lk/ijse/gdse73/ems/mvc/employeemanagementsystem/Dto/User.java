package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class User {
    private String user_id;
    private String user_name;
    private String u_mobile;
    private String u_email;
    private String u_role;


    public User(String user_id, String user_name, String u_mobile, String u_email, String u_role) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.u_mobile = u_mobile;
        this.u_email = u_email;
        this.u_role = u_role;
    }

    public String getUser_id() {
        return user_id; }
    public void setUser_id(String user_id) {
        this.user_id = user_id; }
    public String getUser_name() {
        return user_name; }
    public void setUser_name(String user_name) {
        this.user_name = user_name; }
    public String getU_mobile() {
        return u_mobile; }
    public void setU_mobile(String u_mobile) {
        this.u_mobile = u_mobile; }
    public String getU_email() {
        return u_email; }
    public void setU_email(String u_email) {
        this.u_email = u_email; }
    public String getU_role() {
        return u_role; }
    public void setU_role(String u_role) {
        this.u_role = u_role; }
}
