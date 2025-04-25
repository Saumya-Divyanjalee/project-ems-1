package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class Employee_Equipment {
    private String e_id;
    private String employee_id;
    private String equipment_id;


    public Employee_Equipment(String e_id, String employee_id, String equipment_id) {
        this.e_id = e_id;
        this.employee_id = employee_id;
        this.equipment_id = equipment_id;
    }

    public String getE_id() {
        return e_id; }
    public void setE_id(String e_id) {
        this.e_id = e_id; }
    public String getEmployee_id() {
        return employee_id; }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id; }
    public String getEquipment_id() {
        return equipment_id; }
    public void setEquipment_id(String equipment_id) {
        this.equipment_id = equipment_id; }
}
