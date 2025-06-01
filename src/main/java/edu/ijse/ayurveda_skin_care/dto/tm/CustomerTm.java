package edu.ijse.ayurveda_skin_care.dto.tm;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerTm {
    private String Customer_Id;
    private String Name;
    private int age;
    private String Email;
    private String Phone;
    private String Address;
    private String Registration_Date;
}
