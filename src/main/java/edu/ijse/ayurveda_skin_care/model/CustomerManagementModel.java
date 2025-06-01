package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerManagementModel {

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Customers (Customer_Id, Name, age, Email, Phone, Address, Registration_Date) VALUES (?, ?, ?, ?, ?, ?, ?)",
                customerDto.getCustomer_Id(),
                customerDto.getName(),
                customerDto.getAge(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                customerDto.getAddress(),
                customerDto.getRegistration_Date());
    }

    public boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Customers SET Name = ?, age = ?, Email = ?, Phone = ?, Address = ?, Registration_Date = ? WHERE Customer_Id = ?",
                customerDto.getName(),
                customerDto.getAge(),
                customerDto.getEmail(),
                customerDto.getPhone(),
                customerDto.getAddress(),
                customerDto.getRegistration_Date(),
                customerDto.getCustomer_Id());
    }

    public boolean deleteCustomer(String customer_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Customers WHERE Customer_Id = ?", customer_id);
    }

    public ArrayList<CustomerDto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customers");
        ArrayList<CustomerDto> customerList = new ArrayList<>();

        while (resultSet.next()){
            CustomerDto customerDto = new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
            customerList.add(customerDto);
        }
        return customerList;
    }
}