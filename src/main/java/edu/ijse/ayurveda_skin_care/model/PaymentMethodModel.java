package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.PaymentMethodDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentMethodModel {

    public boolean saveMethod(PaymentMethodDto paymentMethodDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Payment_Methods (Method_Id, Method) VALUES (?, ?)",
                paymentMethodDto.getMethod_Id(),
                paymentMethodDto.getMethod());
    }

    public boolean updateMethod(PaymentMethodDto paymentMethodDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Payment_Methods SET Method = ? WHERE Method_Id = ?",
                paymentMethodDto.getMethod(),
                paymentMethodDto.getMethod_Id());
    }

    public boolean deleteMethod(String method) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Payment_Methods WHERE Method_Id = ?", method);
    }

    public ArrayList<PaymentMethodDto> getAllPaymentMethod() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Payment_Methods");
        ArrayList<PaymentMethodDto> method = new ArrayList<>();

        while (resultSet.next()){
            PaymentMethodDto paymentMethodDto = new PaymentMethodDto(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
            method.add(paymentMethodDto);
        }
        return method;
    }

    public String getNextMethodId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT Method_Id FROM Payment_Methods ORDER BY Method_Id DESC LIMIT 1");
        String tableCharacter = "PM";

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableCharacter+ "1";
    }
}
