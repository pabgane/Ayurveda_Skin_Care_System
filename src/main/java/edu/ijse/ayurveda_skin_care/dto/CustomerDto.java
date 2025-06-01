package edu.ijse.ayurveda_skin_care.dto;

public class CustomerDto {

    private String Customer_Id;
    private String Name;
    private int age;
    private String Email;
    private String Phone;
    private String Address;
    private String Registration_Date;

    public CustomerDto(String customer_Id, String name, int age, String email, String phone, String address, String registration_Date) {
        Customer_Id = customer_Id;
        Name = name;
        this.age = age;
        Email = email;
        Phone = phone;
        Address = address;
        Registration_Date = registration_Date;
    }

    public String getCustomer_Id() {
        return Customer_Id;
    }

    public void setCustomer_Id(String customer_Id) {
        Customer_Id = customer_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRegistration_Date() {
        return Registration_Date;
    }

    public void setRegistration_Date(String registration_Date) {
        Registration_Date = registration_Date;
    }
}
