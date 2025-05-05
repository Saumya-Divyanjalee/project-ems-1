package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class LeavesDto {
    private String leave_id;
    private String leave_type;
    private java.sql.Date start_date;
    private java.sql.Date end_date;
    private String status;
    private String employee_id;


    public LeavesDto(String leave_id, String leave_type, java.sql.Date start_date, java.sql.Date end_date, String status, String employee_id) {
        this.leave_id = leave_id;
        this.leave_type = leave_type;
        this.start_date = start_date;
        this.end_date = end_date;
        this.status = status;
        this.employee_id = employee_id;
    }

    public String getLeave_id() {
        return leave_id; }
    public void setLeave_id(String leave_id) {
        this.leave_id = leave_id; }
    public String getLeave_type() {
        return leave_type; }
    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type; }
    public java.sql.Date getStart_date() {
        return start_date; }
    public void setStart_date(java.sql.Date start_date) {
        this.start_date = start_date; }
    public java.sql.Date getEnd_date() {
        return end_date; }
    public void setEnd_date(java.sql.Date end_date) {
        this.end_date = end_date; }
    public String getStatus() {
        return status; }
    public void setStatus(String status) {
        this.status = status; }
    public String getEmployee_id() {
        return employee_id; }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id; }
}
