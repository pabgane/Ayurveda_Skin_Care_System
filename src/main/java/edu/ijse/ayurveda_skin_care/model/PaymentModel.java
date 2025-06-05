package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.PaymentManagementDto;
import edu.ijse.ayurveda_skin_care.dto.PaymentMethodDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {

    public boolean savePRecord(PaymentManagementDto paymentManagementDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Payments (Payment_Id, Customer_Id, Appointment_Id, Amount, Payment_Date, Method_Id, Status) VALUES (?,?,?,?,?,?,?)",
                paymentManagementDto.getPayment_Id(),
                paymentManagementDto.getCustomer_Id(),
                paymentManagementDto.getAppointment_Id(),
                paymentManagementDto.getAmount(),
                paymentManagementDto.getPayment_Date(),
                paymentManagementDto.getMethod_Id(),
                paymentManagementDto.getStatus());
    }

    public boolean updatePRecord(PaymentManagementDto paymentManagementDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Payments SET Customer_Id = ?, Appointment_Id = ?, Amount = ?, Payment_Date = ?, Method_Id = ?, Status = ? WHERE Payment_Id = ?",
                paymentManagementDto.getCustomer_Id(),
                paymentManagementDto.getAppointment_Id(),
                paymentManagementDto.getAmount(),
                paymentManagementDto.getPayment_Date(),
                paymentManagementDto.getMethod_Id(),
                paymentManagementDto.getStatus(),
                paymentManagementDto.getPayment_Id());
    }

    public boolean deletePRecord(String paymentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Payments WHERE Payment_Id = ?", paymentId);
    }

    public ArrayList<PaymentManagementDto> getAllPRecords() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Payments");
        ArrayList<PaymentManagementDto> pay = new ArrayList<>();

        while (resultSet.next()){
            PaymentManagementDto paymentManagementDto = new PaymentManagementDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
            pay.add(paymentManagementDto);
        }
        return pay;
    }

    public String getNextPRecordId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT Payment_Id FROM Payments ORDER BY Payment_Id DESC LIMIT 1");
        String tableCharacter = "PAY";

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(3);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableCharacter+ "1";
    }
}
