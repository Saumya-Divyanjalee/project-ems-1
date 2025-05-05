package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class SalaryDto {
    private String salary_id;
    private double basic_salary;
    private int ot_hours;
    private double deduction;
    private String employee_id;
    private String dtype_id;


    public SalaryDto(String salary_id, double basic_salary, int ot_hours, double deduction, String employee_id, String dtype_id) {
        this.salary_id = salary_id;
        this.basic_salary = basic_salary;
        this.ot_hours = ot_hours;
        this.deduction = deduction;
        this.employee_id = employee_id;
        this.dtype_id = dtype_id;
    }

    public String getSalary_id() {
        return salary_id;
    }

    public void setSalary_id(String salary_id) {
        this.salary_id = salary_id;
    }

    public double getBasic_salary() {
        return basic_salary; }
    public void setBasic_salary(double basic_salary) {
        this.basic_salary = basic_salary; }
    public int getOt_hours() {
        return ot_hours; }
    public void setOt_hours(int ot_hours) {
        this.ot_hours = ot_hours; }
    public double getDeduction() {
        return deduction; }
    public void setDeduction(double deduction) {
        this.deduction = deduction; }
    public String getEmployee_id() {
        return employee_id; }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id; }
    public String getDtype_id() {
        return dtype_id; }
    public void setDtype_id(String dtype_id) {
        this.dtype_id = dtype_id; }
}
