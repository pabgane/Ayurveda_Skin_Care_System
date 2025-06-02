package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.SupplierManagementDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierManagementModel {

    public boolean saveSupplier(SupplierManagementDto supplierManagementDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Suppliers (Supplier_Id, Name, Email, Phone, Address) VALUES (?, ?, ?, ?, ?)",
                supplierManagementDto.getSupplier_Id(),
                supplierManagementDto.getName(),
                supplierManagementDto.getEmail(),
                supplierManagementDto.getPhone(),
                supplierManagementDto.getAddress());
    }

    public boolean updateSupplier(SupplierManagementDto supplierManagementDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Suppliers SET Name = ?, Email = ?, Phone = ?, Address = ? WHERE Supplier_Id = ?",
                supplierManagementDto.getName(),
                supplierManagementDto.getEmail(),
                supplierManagementDto.getPhone(),
                supplierManagementDto.getAddress(),
                supplierManagementDto.getSupplier_Id());
    }

    public boolean deleteSupplier(String supplier_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Suppliers WHERE Supplier_Id = ?", supplier_id);
    }

    public ArrayList<SupplierManagementDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Suppliers");
        ArrayList<SupplierManagementDto> supplierList = new ArrayList<>();

        while (resultSet.next()){
            SupplierManagementDto supplierManagementDto = new SupplierManagementDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            supplierList.add(supplierManagementDto);
        }
        return supplierList;
    }

    public String getNextSupplierId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT Supplier_Id FROM Suppliers ORDER BY Supplier_Id DESC LIMIT 1");
        String tableCharacter = "SUP";

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
