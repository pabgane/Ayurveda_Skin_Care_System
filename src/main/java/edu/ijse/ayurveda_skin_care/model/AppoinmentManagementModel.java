package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.AppoinmentDto;
import edu.ijse.ayurveda_skin_care.dto.EquipmentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AppoinmentManagementModel {

    public boolean saveAppoinment(AppoinmentDto appoinmentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Appointments (Appointment_Id, Customer_Id, Employee_Id, Treatment_Id, Appointment_Date, Appointment_Time, Status, Notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                appoinmentDto.getAppointment_Id(),
                appoinmentDto.getCustomer_Id(),
                appoinmentDto.getEmployee_Id(),
                appoinmentDto.getTreatment_Id(),
                appoinmentDto.getAppointment_Date(),
                appoinmentDto.getAppointment_Time(),
                appoinmentDto.getStatus(),
                appoinmentDto.getNotes());
    }

    public boolean updateAppoinment(AppoinmentDto appoinmentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Appointments SET Customer_Id = ?, Employee_Id = ?, Treatment_Id = ?, Appointment_Date = ?, Appointment_Time = ?, Status = ?, Notes = ? WHERE Appointment_Id = ?",
                appoinmentDto.getCustomer_Id(),
                appoinmentDto.getEmployee_Id(),
                appoinmentDto.getTreatment_Id(),
                appoinmentDto.getAppointment_Date(),
                appoinmentDto.getAppointment_Time(),
                appoinmentDto.getStatus(),
                appoinmentDto.getNotes(),
                appoinmentDto.getAppointment_Id());
    }

    public boolean deleteAppionment(String appoinment) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Appointments WHERE Appointment_Id = ?", appoinment);
    }

    public ArrayList<AppoinmentDto> getAllAppoinment() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Appointments");
        ArrayList<AppoinmentDto> appoinment = new ArrayList<>();

        while (resultSet.next()){
            AppoinmentDto appoinmentDto = new AppoinmentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            );
            appoinment.add(appoinmentDto);
        }
        return appoinment;
    }

    public String getNextAppoinmentId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT Appointment_Id FROM Appointments ORDER BY Appointment_Id DESC LIMIT 1");
        String tableCharacter = "A";

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableCharacter+ "1";
    }
}
