package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class OT {
    private String ot_id;
    private int ot_hours;
    private java.sql.Date ot_date;
    private double rate_per_hours;
    private String employee_id;
    private String salary_id;


    public OT(String ot_id, int ot_hours, java.sql.Date ot_date, double rate_per_hours, String employee_id, String salary_id) {
        this.ot_id = ot_id;
        this.ot_hours = ot_hours;
        this.ot_date = ot_date;
        this.rate_per_hours = rate_per_hours;
        this.employee_id = employee_id;
        this.salary_id = salary_id;
    }

    public String getOt_id() {
        return ot_id; }
    public void setOt_id(String ot_id) {
        this.ot_id = ot_id; }
    public int getOt_hours() {
        return ot_hours; }
    public void setOt_hours(int ot_hours) {
        this.ot_hours = ot_hours; }
    public java.sql.Date getOt_date() {
        return ot_date; }
    public void setOt_date(java.sql.Date ot_date) {
        this.ot_date = ot_date; }
    public double getRate_per_hours() {
        return rate_per_hours; }
    public void setRate_per_hours(double rate_per_hours) {
        this.rate_per_hours = rate_per_hours; }
    public String getEmployee_id() {
        return employee_id; }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id; }
    public String getSalary_id() {
        return salary_id; }
    public void setSalary_id(String salary_id) {
        this.salary_id = salary_id; }
}
