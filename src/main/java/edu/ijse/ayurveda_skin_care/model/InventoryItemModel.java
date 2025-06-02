package edu.ijse.ayurveda_skin_care.model;

import edu.ijse.ayurveda_skin_care.Util.CrudUtil;
import edu.ijse.ayurveda_skin_care.dto.InventoryItemDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryItemModel {

    public boolean saveInventoryItem(InventoryItemDto inventoryItemDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Inventory_Items (Inventory_Item_Id, Name, Description, Quantity, Unit_Price, Expiry_Date, Supplier_Id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                inventoryItemDto.getInventory_Item_Id(),
                inventoryItemDto.getName(),
                inventoryItemDto.getDescription(),
                inventoryItemDto.getQuantity(),
                inventoryItemDto.getUnit_Price(),
                inventoryItemDto.getExpiry_Date(),
                inventoryItemDto.getSupplier_Id());
    }

    public boolean updateInventoryItem(InventoryItemDto inventoryItemDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Inventory_Items SET Name = ?, Description = ?, Quantity = ?, Unit_Price = ?, Expiry_Date = ?, Supplier_Id = ? WHERE Inventory_Item_Id = ?",
                inventoryItemDto.getName(),
                inventoryItemDto.getDescription(),
                inventoryItemDto.getQuantity(),
                inventoryItemDto.getUnit_Price(),
                inventoryItemDto.getExpiry_Date(),
                inventoryItemDto.getSupplier_Id(),
                inventoryItemDto.getInventory_Item_Id());
    }

    public boolean deleteInventoryItem(String Inventory_Item_Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Inventory_Items WHERE Inventory_Item_Id = ?", Inventory_Item_Id);
    }

    public ArrayList<InventoryItemDto> getAllInventoryItems() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Inventory_Items");
        ArrayList<InventoryItemDto> inventoryItemList = new ArrayList<>();

        while (resultSet.next()){
            InventoryItemDto inventoryItemDto = new InventoryItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
            inventoryItemList.add(inventoryItemDto);
        }
        return inventoryItemList;
    }

    public String getNextInventoryItemId() throws SQLException , ClassNotFoundException{
        ResultSet resultSet = CrudUtil.execute("SELECT Inventory_Item_Id FROM Inventory_Items ORDER BY Inventory_Item_Id DESC LIMIT 1");
        String tableCharacter = "INV";

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
