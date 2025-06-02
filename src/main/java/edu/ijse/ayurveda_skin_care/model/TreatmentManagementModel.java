package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.TreatmentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TreatmentManagementModel {

    public boolean saveTreatment(TreatmentDto treatmentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Treatments (Treatment_Id, Name, Description, Duration, Price) VALUES (?, ?, ?, ?, ?)",
                treatmentDto.getTreatment_Id(),
                treatmentDto.getName(),
                treatmentDto.getDescription(),
                treatmentDto.getDuration(),
                treatmentDto.getPrice());
    }

    public boolean updateTreatment(TreatmentDto treatmentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Treatments SET Name = ?, Description = ?, Duration = ?, Price = ? WHERE Treatment_Id = ?",
                treatmentDto.getName(),
                treatmentDto.getDescription(),
                treatmentDto.getDuration(),
                treatmentDto.getPrice(),
                treatmentDto.getTreatment_Id());
    }

    public boolean deleteTreatment(String treatment_Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Treatments WHERE Treatment_Id = ?", treatment_Id);
    }

    public ArrayList<TreatmentDto> getAllTreatments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Treatments");
        ArrayList<TreatmentDto> treatmentList = new ArrayList<>();

        while (resultSet.next()){
            TreatmentDto treatmentDto = new TreatmentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5)
            );
            treatmentList.add(treatmentDto);
        }
        return treatmentList;
    }

    public String getNextTreatmentId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT Treatment_Id FROM Treatments ORDER BY Treatment_Id DESC LIMIT 1");
        char tableCharacter = 'T';

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
