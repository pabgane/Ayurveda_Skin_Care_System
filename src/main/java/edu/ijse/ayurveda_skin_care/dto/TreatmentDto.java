package edu.ijse.ayurveda_skin_care.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TreatmentDto {

    private String Treatment_Id;
    private String Name;
    private String Description;
    private int Duration;
    private double Price;
}
