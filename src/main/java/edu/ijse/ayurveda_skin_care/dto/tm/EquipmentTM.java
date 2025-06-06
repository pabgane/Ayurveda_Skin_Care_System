package edu.ijse.ayurveda_skin_care.dto.tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EquipmentTM {

    private String Equipment_Id;
    private String Name;
    private String Serial_Number;
    private String Purchase_Date;
    private String Last_Maintenance_Date;
    private String Status;
}