package edu.ijse.ayurveda_skin_care.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InventoryItemTM {

    private String Inventory_Item_Id;
    private String Name;
    private String Description;
    private int Quantity;
    private Double Unit_Price;
    private String Expiry_Date;
    private String Supplier_Id;
}