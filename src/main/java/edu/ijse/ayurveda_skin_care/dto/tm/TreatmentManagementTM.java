package edu.ijse.ayurveda_skin_care.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TreatmentManagementTM {

    private String Treatment_Id;
    private String Name;
    private String Description;
    private int Duration;
    private double Price;
}