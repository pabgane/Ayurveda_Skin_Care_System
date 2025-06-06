package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.EquipmentDto;
import edu.ijse.ayurveda_skin_care.dto.tm.EquipmentTM;
import edu.ijse.ayurveda_skin_care.model.EquipmentModel;
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

public class EquipmentController implements Initializable {

    @FXML
    private AnchorPane ancCustomerManagement;

    @FXML
    private Button btnClear, btnDelete, btnSave, btnUpdate;

    @FXML
    private TableColumn<EquipmentTM, String> colEquipmentId, colLastMaintenanceDate, colName, colPerchaseDate, colSerialNumber, colStatus;

    @FXML
    private Label lblEquipmentId;

    @FXML
    private TableView<EquipmentTM> tblEquipmentList;

    @FXML
    private TextField txtLastMaintenanceDate, txtName, txtPerchaseDate, txtSerialNumber, txtStatus;

    private final EquipmentModel equipmentModel = new EquipmentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEquipmentId.setCellValueFactory(new PropertyValueFactory<>("Equipment_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colSerialNumber.setCellValueFactory(new PropertyValueFactory<>("Serial_Number"));
        colPerchaseDate.setCellValueFactory(new PropertyValueFactory<>("Purchase_Date"));
        colLastMaintenanceDate.setCellValueFactory(new PropertyValueFactory<>("Last_Maintenance_Date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        try {
            resetPage();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblEquipmentList.setItems(FXCollections.observableArrayList(
                equipmentModel.getAllEquipments()
                        .stream()
                        .map(equipmentDto -> new EquipmentTM(
                                equipmentDto.getEquipment_Id(),
                                equipmentDto.getName(),
                                equipmentDto.getSerial_Number(),
                                equipmentDto.getPurchase_Date(),
                                equipmentDto.getLast_Maintenance_Date(),
                                equipmentDto.getStatus()
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

            txtName.clear();
            txtSerialNumber.clear();
            txtPerchaseDate.clear();
            txtLastMaintenanceDate.clear();
            txtStatus.clear();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private boolean validateInputs() {
        // Name validation: Letters and spaces only, 3-50 chars
        if (txtName.getText().isEmpty()) {
            showValidationError("Please enter equipment name.");
            txtName.requestFocus();
            return false;
        }
        if (!txtName.getText().matches("^[A-Za-z ]{3,50}$")) {
            showValidationError("Name should only contain letters and spaces (3-50 characters).");
            txtName.requestFocus();
            return false;
        }

        // Serial Number: Uppercase letters, digits, hyphens, 3-30 chars
        if (txtSerialNumber.getText().isEmpty()) {
            showValidationError("Please enter serial number.");
            txtSerialNumber.requestFocus();
            return false;
        }
        if (!txtSerialNumber.getText().matches("^[a-zA-Z0-9]{3,30}$")) {
            showValidationError("Serial number should contain only letters or numbers (3-30 characters).");
            txtSerialNumber.requestFocus();
            return false;
        }

        // Purchase Date: Validate date format yyyy-MM-dd
        if (txtPerchaseDate.getText().isEmpty()) {
            showValidationError("Please enter purchase date.");
            txtPerchaseDate.requestFocus();
            return false;
        }
        if (!txtPerchaseDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            showValidationError("Purchase date must be in format YYYY-MM-DD.");
            txtPerchaseDate.requestFocus();
            return false;
        }

        // Last Maintenance Date: Validate date format yyyy-MM-dd
        if (txtLastMaintenanceDate.getText().isEmpty()) {
            showValidationError("Please enter last maintenance date.");
            txtLastMaintenanceDate.requestFocus();
            return false;
        }
        if (!txtLastMaintenanceDate.getText().matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            showValidationError("Last maintenance date must be in format YYYY-MM-DD.");
            txtLastMaintenanceDate.requestFocus();
            return false;
        }

        // Status: Letters only, 3-20 chars
        if (txtStatus.getText().isEmpty()) {
            showValidationError("Please enter status.");
            txtStatus.requestFocus();
            return false;
        }

        return true;
    }

    private void showValidationError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        String equipmentId = lblEquipmentId.getText();
        String name = txtName.getText();
        String serialNumber = txtSerialNumber.getText();
        String purchaseDate = txtPerchaseDate.getText();
        String lastMaintenanceDate = txtLastMaintenanceDate.getText();
        String status = txtStatus.getText();

        EquipmentDto dto = new EquipmentDto(
                equipmentId, name, serialNumber, purchaseDate, lastMaintenanceDate, status
        );

        try {
            boolean isSaved = equipmentModel.saveEquipment(dto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save Failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) return;

        String equipmentId = lblEquipmentId.getText();
        String name = txtName.getText();
        String serialNumber = txtSerialNumber.getText();
        String purchaseDate = txtPerchaseDate.getText();
        String lastMaintenanceDate = txtLastMaintenanceDate.getText();
        String status = txtStatus.getText();

        EquipmentDto dto = new EquipmentDto(
                equipmentId, name, serialNumber, purchaseDate, lastMaintenanceDate, status
        );

        try {
            boolean isUpdated = equipmentModel.updateEquipment(dto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Updated").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update Failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String equipmentId = lblEquipmentId.getText();
            try {
                boolean isDeleted = equipmentModel.deleteEquipment(equipmentId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete Failed").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = equipmentModel.getNextEquipmentId();
        lblEquipmentId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        EquipmentTM selectedItem = tblEquipmentList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblEquipmentId.setText(selectedItem.getEquipment_Id());
            txtName.setText(selectedItem.getName());
            txtSerialNumber.setText(selectedItem.getSerial_Number());
            txtPerchaseDate.setText(selectedItem.getPurchase_Date());
            txtLastMaintenanceDate.setText(selectedItem.getLast_Maintenance_Date());
            txtStatus.setText(selectedItem.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
