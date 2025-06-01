package edu.ijse.ayurveda_skin_care.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDto {

    private String Customer_Id;
    private String Name;
    private int age;
    private String Email;
    private String Phone;
    private String Address;
    private String Registration_Date;

}
