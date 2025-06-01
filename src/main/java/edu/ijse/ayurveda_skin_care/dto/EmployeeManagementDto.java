package edu.ijse.ayurveda_skin_care.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeManagementDto {

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
}
