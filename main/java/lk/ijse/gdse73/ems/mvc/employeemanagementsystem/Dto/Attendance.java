package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class Attendance {
    private String attendance_id;
    private java.sql.Date date;
    private java.sql.Time check_in;
    private java.sql.Time check_out;
    private String status;
    private String employee_id;


    public Attendance(String attendance_id, java.sql.Date date, java.sql.Time check_in, java.sql.Time check_out, String status, String employee_id) {
        this.attendance_id = attendance_id;
        this.date = date;
        this.check_in = check_in;
        this.check_out = check_out;
        this.status = status;
        this.employee_id = employee_id;
    }

    public String getAttendance_id() {
        return attendance_id; }
    public void setAttendance_id(String attendance_id) {
        this.attendance_id = attendance_id; }
    public java.sql.Date getDate() {
        return date; }
    public void setDate(java.sql.Date date) {
        this.date = date; }
    public java.sql.Time getCheck_in() {
        return check_in; }
    public void setCheck_in(java.sql.Time check_in) {
        this.check_in = check_in; }
    public java.sql.Time getCheck_out() {
        return check_out; }
    public void setCheck_out(java.sql.Time check_out) {
        this.check_out = check_out; }
    public String getStatus() {
        return status; }
    public void setStatus(String status) {
        this.status = status; }
    public String getEmployee_id() {
        return employee_id; }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id; }
}
