package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class Task {
    private String task_id;
    private String description;
    private java.sql.Date deadline;
    private String status;
    private String employee_id;


    public Task(String task_id, String description, java.sql.Date deadline, String status, String employee_id) {
        this.task_id = task_id;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.employee_id = employee_id;
    }

    public String getTask_id() {
        return task_id; }
    public void setTask_id(String task_id) {
        this.task_id = task_id; }
    public String getDescription() {
        return description; }
    public void setDescription(String description) {
        this.description = description; }
    public java.sql.Date getDeadline() {
        return deadline; }
    public void setDeadline(java.sql.Date deadline) {
        this.deadline = deadline; }
    public String getStatus() {
        return status; }
    public void setStatus(String status) {
        this.status = status; }
    public String getEmployee_id() {
        return employee_id; }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id; }
}
