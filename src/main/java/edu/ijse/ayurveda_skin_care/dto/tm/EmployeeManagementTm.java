package edu.ijse.ayurveda_skin_care.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
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
}
