package edu.ijse.ayurveda_skin_care.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppoinmentTM {

    private String Appointment_Id;
    private String Customer_Id;
    private String Employee_Id;
    private String Treatment_Id;
    private String Appointment_Date;
    private String Appointment_Time;
    private String Status;
    private String Notes;
}