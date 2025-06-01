package edu.ijse.ayurveda_skin_care.dto.tm;

public class EmployeeManagementTm {

    private String Employee_Id;
    private String Name;
    private String Email;
    private String Phone;
    private int age;
    private String Address;
    private Double salary;
    private String emergency_contact;
    private String Hire_Date;
    private String Position;
    private String Status;

    public EmployeeManagementTm() {
    }

    public EmployeeManagementTm(String employee_Id, String name, String email, String phone, int age, String address, Double salary, String emergency_contact, String hire_Date, String position, String status) {
        Employee_Id = employee_Id;
        Name = name;
        Email = email;
        Phone = phone;
        this.age = age;
        Address = address;
        this.salary = salary;
        this.emergency_contact = emergency_contact;
        Hire_Date = hire_Date;
        Position = position;
        Status = status;
    }

    public String getEmployee_Id() {
        return Employee_Id;
    }

    public void setEmployee_Id(String employee_Id) {
        Employee_Id = employee_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getEmergency_contact() {
        return emergency_contact;
    }

    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }

    public String getHire_Date() {
        return Hire_Date;
    }

    public void setHire_Date(String hire_Date) {
        Hire_Date = hire_Date;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
