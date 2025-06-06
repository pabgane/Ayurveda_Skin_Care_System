package edu.ijse.ayurveda_skin_care.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentManagementTM {
    private String Payment_Id;
    private String Customer_Id;
    private String Appointment_Id;
    private Double Amount;
    private String Payment_Date;
    private String Method_Id;
    private String Status;
}