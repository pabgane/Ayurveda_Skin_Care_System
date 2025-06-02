package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.UserTypeDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserTypeModel {

    public boolean saveUserType(UserTypeDto userTypeDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO User_Types (User_Type_Id, Type) VALUES (?, ?)",
                userTypeDto.getUserTypeId(),
                userTypeDto.getType());
    }

    public boolean updateUserType(UserTypeDto userTypeDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE User_Types SET Type = ? WHERE User_Type_Id = ?",
                userTypeDto.getType(),
                userTypeDto.getUserTypeId());
    }

    public boolean deleteUserType(String user_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM User_Types WHERE User_Type_Id = ?", user_id);
    }

    public ArrayList<UserTypeDto> getAllUserTypes() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM User_Types");
        ArrayList<UserTypeDto> userTypeList = new ArrayList<>();

        while (resultSet.next()){
            UserTypeDto userTypeDto = new UserTypeDto(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
            userTypeList.add(userTypeDto);
        }
        return userTypeList;
    }

    public String getNextUserTypeId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT User_Type_Id FROM User_Types ORDER BY User_Type_Id DESC LIMIT 1");
        String tableCharacter = "UT";

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
