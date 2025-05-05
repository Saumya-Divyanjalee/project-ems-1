package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class EquipmentDto {
    private String equipment_id;
    private String equipment_name;
    private String employee_id;


    public EquipmentDto(String equipment_id, String equipment_name, String employee_id) {
        this.equipment_id = equipment_id;
        this.equipment_name = equipment_name;
        this.employee_id = employee_id;
    }

    public String getEquipment_id() {
        return equipment_id; }
    public void setEquipment_id(String equipment_id) {
        this.equipment_id = equipment_id; }
    public String getEquipment_name() {
        return equipment_name; }
    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name; }
    public String getEmployee_id() {
        return employee_id; }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id; }
}
