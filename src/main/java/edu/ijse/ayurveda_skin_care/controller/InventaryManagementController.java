package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.InventoryItemDto;
import edu.ijse.ayurveda_skin_care.dto.tm.InventoryItemTM;
import edu.ijse.ayurveda_skin_care.model.InventoryItemModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventaryManagementController implements Initializable {
    @FXML
    private AnchorPane ancCustomerManagement;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<InventoryItemTM, String> colDescription;

    @FXML
    private TableColumn<InventoryItemTM, String> colExpiryDate;

    @FXML
    private TableColumn<InventoryItemTM, String> colInventoryItemId;

    @FXML
    private TableColumn<InventoryItemTM, String> colName;

    @FXML
    private TableColumn<InventoryItemTM, String> colQuantity;

    @FXML
    private TableColumn<InventoryItemTM, String> colSupplierId;

    @FXML
    private TableColumn<InventoryItemTM, String> colUnitPrice;

    @FXML
    private Label lblInventoryItemId;

    @FXML
    private TableView<InventoryItemTM> tblInventoryItemList;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtExpiryDate;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtUnitPrice;

    private final InventoryItemModel inventoryItemModel = new InventoryItemModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInventoryItemId.setCellValueFactory(new PropertyValueFactory<>("Inventory_Item_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("Unit_Price"));
        colExpiryDate.setCellValueFactory(new PropertyValueFactory<>("Expiry_Date"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("Supplier_Id"));

        try {
            resetPage();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }

    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblInventoryItemList.setItems(FXCollections.observableArrayList(
                inventoryItemModel.getAllInventoryItems()
                        .stream()
                        .map(inventoryItemDto -> new InventoryItemTM(
                                inventoryItemDto.getInventory_Item_Id(),
                                inventoryItemDto.getName(),
                                inventoryItemDto.getDescription(),
                                inventoryItemDto.getQuantity(),
                                inventoryItemDto.getUnit_Price(),
                                inventoryItemDto.getExpiry_Date(),
                                inventoryItemDto.getSupplier_Id()
                        )).toList()
        ));
    }

    private void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtName.setText(null);
            txtDescription.setText(null);
            txtQuantity.setText(null);
            txtUnitPrice.setText(null);
            txtExpiryDate.setText(null);
            txtSupplierId.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String inventoryItemId = lblInventoryItemId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        Double unitPrice = Double.valueOf(txtUnitPrice.getText());
        String expiryDate = txtExpiryDate.getText();
        String supplierId = txtSupplierId.getText();


        InventoryItemDto inventoryItemDto = new InventoryItemDto(
                inventoryItemId,
                name,
                description,
                quantity,
                unitPrice,
                expiryDate,
                supplierId
        );
        try {
            boolean isSaved = inventoryItemModel.saveInventoryItem(inventoryItemDto);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String inventoryItemId = lblInventoryItemId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        Double unitPrice = Double.valueOf(txtUnitPrice.getText());
        String expiryDate = txtExpiryDate.getText();
        String supplierId = txtSupplierId.getText();


        InventoryItemDto inventoryItemDto = new InventoryItemDto(
                inventoryItemId,
                name,
                description,
                quantity,
                unitPrice,
                expiryDate,
                supplierId
        );

        try {
            boolean isUpdated = inventoryItemModel.updateInventoryItem(inventoryItemDto);
            if(isUpdated){
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Updated").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail").show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are You Sure ? ",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> response = alert.showAndWait();

        if(response.isPresent() && response.get() == ButtonType.YES){
            String inventoryItemId = lblInventoryItemId.getText();
            try {
                boolean isDeleted = inventoryItemModel.deleteInventoryItem(inventoryItemId);
                if(isDeleted){
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Fail").show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = inventoryItemModel.getNextInventoryItemId();
        lblInventoryItemId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        InventoryItemTM selectedItem = tblInventoryItemList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblInventoryItemId.setText(selectedItem.getInventory_Item_Id());
            txtName.setText(selectedItem.getName());
            txtDescription.setText(selectedItem.getDescription());
            txtQuantity.setText(String.valueOf(selectedItem.getQuantity()));
            txtUnitPrice.setText(String.valueOf(selectedItem.getUnit_Price()));
            txtExpiryDate.setText(selectedItem.getExpiry_Date());
            txtSupplierId.setText(selectedItem.getSupplier_Id());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

}
