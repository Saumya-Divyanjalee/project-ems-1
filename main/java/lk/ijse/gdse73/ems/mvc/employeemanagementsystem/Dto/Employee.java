package lk.ijse.gdse73.ems.mvc.employeemanagementsystem.Dto;

public class Employee {
    private String employee_id;
    private String first_name;
    private String last_name;
    private java.sql.Date dob;
    private int age;
    private String email;
    private String contact;
    private String e_address;
    private java.sql.Date join_date;
    private String department_id;
    private String position_id;


    public Employee(String employee_id, String first_name, String last_name, java.sql.Date dob, int age, String email, String contact, String e_address, java.sql.Date join_date, String department_id, String position_id) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.age = age;
        this.email = email;
        this.contact = contact;
        this.e_address = e_address;
        this.join_date = join_date;
        this.department_id = department_id;
        this.position_id = position_id;
    }

    public String getEmployee_id() {
        return employee_id; }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id; }
    public String getFirst_name() {
        return first_name; }
    public void setFirst_name(String first_name) {
        this.first_name = first_name; }
    public String getLast_name() {
        return last_name; }
    public void setLast_name(String last_name) {
        this.last_name = last_name; }
    public java.sql.Date getDob() {
        return dob; }
    public void setDob(java.sql.Date dob) {
        this.dob = dob; }
    public int getAge() {
        return age; }
    public void setAge(int age) {
        this.age = age; }
    public String getEmail() {
        return email; }
    public void setEmail(String email) {
        this.email = email; }
    public String getContact() {
        return contact; }
    public void setContact(String contact) {
        this.contact = contact; }
    public String getE_address() {
        return e_address; }
    public void setE_address(String e_address) {
        this.e_address = e_address; }
    public java.sql.Date getJoin_date() {
        return join_date; }
    public void setJoin_date(java.sql.Date join_date) {
        this.join_date = join_date; }
    public String getDepartment_id() {
        return department_id; }
    public void setDepartment_id(String department_id) {
        this.department_id = department_id; }
    public String getPosition_id() {
        return position_id; }
    public void setPosition_id(String position_id) {
        this.position_id = position_id; }
}
