package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.EquipmentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EquipmentModel {

    public boolean saveEquipment(EquipmentDto equipmentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Equipment (Equipment_Id, Name, Serial_Number, Purchase_Date, Last_Maintenance_Date, Status) VALUES (?, ?, ?, ?, ?, ?)",
                equipmentDto.getEquipment_Id(),
                equipmentDto.getName(),
                equipmentDto.getSerial_Number(),
                equipmentDto.getPurchase_Date(),
                equipmentDto.getLast_Maintenance_Date(),
                equipmentDto.getStatus());
    }

    public boolean updateEquipment(EquipmentDto equipmentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Equipment SET Name = ?, Serial_Number = ?, Purchase_Date = ?, Last_Maintenance_Date = ?, Status = ? WHERE Equipment_Id = ?",
                equipmentDto.getName(),
                equipmentDto.getSerial_Number(),
                equipmentDto.getPurchase_Date(),
                equipmentDto.getLast_Maintenance_Date(),
                equipmentDto.getStatus(),
                equipmentDto.getEquipment_Id());
    }

    public boolean deleteEquipment(String equipment_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Equipment WHERE Equipment_Id = ?", equipment_id);
    }

    public ArrayList<EquipmentDto> getAllEquipments() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Equipment");
        ArrayList<EquipmentDto> equipmentList = new ArrayList<>();

        while (resultSet.next()){
            EquipmentDto equipmentDto = new EquipmentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
            equipmentList.add(equipmentDto);
        }
        return equipmentList;
    }

    public String getNextEquipmentId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT Equipment_Id FROM Equipment ORDER BY Equipment_Id DESC LIMIT 1");
        String tableCharacter = "EQ";

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(2);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableCharacter+ "001";
    }
}
