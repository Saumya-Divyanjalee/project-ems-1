package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class DeductionsDto {
    private String dtype_id;
    private String type_name;


    public DeductionsDto(String dtype_id, String type_name) {
        this.dtype_id = dtype_id;
        this.type_name = type_name;
    }

    public String getDtype_id() {
        return dtype_id;}
    public void setDtype_id(String dtype_id) {
        this.dtype_id = dtype_id; }
    public String getType_name() {
        return type_name; }
    public void setType_name(String type_name) {
        this.type_name = type_name; }
}
