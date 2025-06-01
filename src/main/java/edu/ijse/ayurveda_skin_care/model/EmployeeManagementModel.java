package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.EmployeeManagementDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeManagementModel {

    public boolean saveEmployee(EmployeeManagementDto employeeManagementDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Employees (Employee_Id, Name, Email, Phone, age, Address, salary, emergency_contact, Hire_Date, Position, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                employeeManagementDto.getEmployee_Id(),
                employeeManagementDto.getName(),
                employeeManagementDto.getEmail(),
                employeeManagementDto.getPhone(),
                employeeManagementDto.getAge(),
                employeeManagementDto.getAddress(),
                employeeManagementDto.getSalary(),
                employeeManagementDto.getEmergency_contact(),
                employeeManagementDto.getHire_Date(),
                employeeManagementDto.getPosition(),
                employeeManagementDto.getStatus());
    }

    public boolean updateEmployee(EmployeeManagementDto employeeManagementDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Employees SET Name = ?, Email = ?, Phone = ?, age = ?, Address = ?, salary = ?, emergency_contact = ?, Hire_Date = ?, Position = ? WHERE Employee_Id = ?",
                employeeManagementDto.getName(),
                employeeManagementDto.getEmail(),
                employeeManagementDto.getPhone(),
                employeeManagementDto.getAge(),
                employeeManagementDto.getAddress(),
                employeeManagementDto.getSalary(),
                employeeManagementDto.getEmergency_contact(),
                employeeManagementDto.getHire_Date(),
                employeeManagementDto.getPosition(),
                employeeManagementDto.getEmployee_Id());
    }

    public boolean deleteEmployee(String employee_Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Employees WHERE Employee_Id = ?", employee_Id);
    }

    public ArrayList<EmployeeManagementDto> getAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Employees");
        ArrayList<EmployeeManagementDto> employeeList = new ArrayList<>();

        while (resultSet.next()){
            EmployeeManagementDto employeeManagementDto = new EmployeeManagementDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getString(6),
                    resultSet.getDouble(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11)
            );
            employeeList.add(employeeManagementDto);
        }
        return employeeList;
    }

    public String getNextEmployeeId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT Employee_Id FROM Employees ORDER BY Employee_Id DESC LIMIT 1");
        char tableCharacter = 'E';

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
