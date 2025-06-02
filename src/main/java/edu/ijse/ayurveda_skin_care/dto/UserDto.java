package edu.ijse.ayurveda_skin_care.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private String User_Id;
    private String Name;
    private String Phone;
    private String Username;
    private String Password;
    private String Email;
    private int age;
    private String User_Type_Id;
    private String Registration_Date;
    private String Status;
}
