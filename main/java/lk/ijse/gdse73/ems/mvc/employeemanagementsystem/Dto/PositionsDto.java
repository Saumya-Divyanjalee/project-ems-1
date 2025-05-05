package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class PositionsDto {
    private String position_id;
    private String p_title;
    private String salary_grade;


    public PositionsDto(String position_id, String p_title, String salary_grade) {
        this.position_id = position_id;
        this.p_title = p_title;
        this.salary_grade = salary_grade;
    }

    public String getPosition_id() {
        return position_id; }
    public void setPosition_id(String position_id) {
        this.position_id = position_id; }
    public String getP_title() {
        return p_title; }
    public void setP_title(String p_title) {
        this.p_title = p_title; }
    public String getSalary_grade() {
        return salary_grade; }
    public void setSalary_grade(String salary_grade) {
        this.salary_grade = salary_grade; }
}
