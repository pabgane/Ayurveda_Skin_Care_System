package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.UserDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManageModel {

    public boolean saveUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Users (User_Id, Name, Phone, Username, Password, Email, age, User_Type_Id, Registration_Date, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                userDto.getUser_Id(),
                userDto.getName(),
                userDto.getPhone(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getAge(),
                userDto.getUser_Type_Id(),
                userDto.getRegistration_Date(),
                userDto.getStatus());
    }

    public boolean updateUser(UserDto userDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Users SET Name = ?, Phone = ?, Username = ?, Password = ?, Email = ?, age = ?, User_Type_Id = ?, Registration_Date = ?, Status = ? WHERE User_Id = ?",
                userDto.getName(),
                userDto.getPhone(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getEmail(),
                userDto.getAge(),
                userDto.getUser_Type_Id(),
                userDto.getRegistration_Date(),
                userDto.getStatus(),
                userDto.getUser_Id());
    }

    public boolean deleteUser(String user_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Users WHERE User_Id = ?", user_id);
    }

    public ArrayList<UserDto> getAllUser() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Users");
        ArrayList<UserDto> userList = new ArrayList<>();

        while (resultSet.next()){
            UserDto userDto = new UserDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10)
            );
            userList.add(userDto);
        }
        return userList;
    }

    public String getNextUserId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT User_Id FROM Users ORDER BY User_Id DESC LIMIT 1");
        char tableCharacter = 'U';

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
