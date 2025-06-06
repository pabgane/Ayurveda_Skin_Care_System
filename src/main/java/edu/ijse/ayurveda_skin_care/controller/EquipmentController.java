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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EquipmentController implements Initializable {

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
    private TableColumn<EquipmentTM, String> colEquipmentId;

    @FXML
    private TableColumn<EquipmentTM, String> colLastMaintenanceDate;

    @FXML
    private TableColumn<EquipmentTM, String> colName;

    @FXML
    private TableColumn<EquipmentTM, String > colPerchaseDate;

    @FXML
    private TableColumn<EquipmentTM, String> colSerialNumber;

    @FXML
    private TableColumn<EquipmentTM, String> colStatus;

    @FXML
    private Label lblEquipmentId;

    @FXML
    private TableView<EquipmentTM> tblEquipmentList;

    @FXML
    private TextField txtLastMaintenanceDate;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPerchaseDate;

    @FXML
    private TextField txtSerialNumber;

    @FXML
    private TextField txtStatus;


    private final EquipmentModel equipmentModel = new EquipmentModel();

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

            txtName.setText(null);
            txtSerialNumber.setText(null);
            txtPerchaseDate.setText(null);
            txtLastMaintenanceDate.setText(null);
            txtStatus.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private boolean validateEquipmentForm() {
        String name = txtName.getText();
        String serial = txtSerialNumber.getText(); // Assuming Serial Number is used as description
        String purchaseDate = txtPerchaseDate.getText();
        String LastMaintenanceDate = txtLastMaintenanceDate.getText(); // Assuming Last Maintenance Date is used as cost
        String maintenanceSchedule = txtStatus.getText(); // Assuming St
        String status = txtStatus.getText();

        if (name.isEmpty() || serial.isEmpty() || purchaseDate.isEmpty() || maintenanceSchedule.isEmpty() || LastMaintenanceDate.isEmpty() ||
                status.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields.").show();
            return false;
        }
        if (!isValidDate(purchaseDate)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Purchase Date format. Use yyyy-MM-dd.").show();
            return false;
        }
        if (!isValidDate(LastMaintenanceDate)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Last Maintenance Date format. Use yyyy-MM-dd.").show();
            return false;
        }
        if (!status.matches("Available|Unavailable")) {
            new Alert(Alert.AlertType.WARNING, "Status must be either 'Available' or 'Unavailable'.").show();
            return false;
        }
        if (!serial.matches("[A-Za-z0-9]{10}")) {
            new Alert(Alert.AlertType.WARNING, "Serial Number must be exactly 10 digits and alphabetic characters.").show();
            return false;
        }



        return true;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date); // Assumes format yyyy-MM-dd
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        if (!validateEquipmentForm()) {
            return; // Stop if validation fails
        }

        String equipmentId = lblEquipmentId.getText();
        String name = txtName.getText();
        String last_MainTaine = txtSerialNumber.getText(); // Assuming Serial Number is used as description
        String purchaseDate = txtPerchaseDate.getText();
        String serial = txtLastMaintenanceDate.getText(); // Assuming Last Maintenance Date is used as cost
        String status = txtStatus.getText(); // Assuming Status is used as Maintenance Schedule


        EquipmentDto equipmentDto = new EquipmentDto(
                equipmentId,
                name,
                last_MainTaine,
                purchaseDate,
                serial,
                status
        );
        try {
            boolean isSaved = equipmentModel.saveEquipment(equipmentDto);

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

        if (!validateEquipmentForm()) {
            return; // Stop if validation fails
        }

        String equipmentId = lblEquipmentId.getText();
        String name = txtName.getText();
        String last_MainTaine = txtSerialNumber.getText(); // Assuming Serial Number is used as description
        String purchaseDate = txtPerchaseDate.getText();
        String serial = txtLastMaintenanceDate.getText(); // Assuming Last Maintenance Date is used as cost
        String status = txtStatus.getText(); // Assuming Status is used as Maintenance Schedule


        EquipmentDto equipmentDto = new EquipmentDto(
                equipmentId,
                name,
                last_MainTaine,
                purchaseDate,
                serial,
                status
        );
        try {
            boolean isUpdated = equipmentModel.updateEquipment(equipmentDto);
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
            String equipmentId = lblEquipmentId.getText();
            try {
                boolean isDeleted = equipmentModel.deleteEquipment(equipmentId);
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
