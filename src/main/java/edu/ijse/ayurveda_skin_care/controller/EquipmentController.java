package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.db.DBConnection;
import edu.ijse.ayurveda_skin_care.dto.EquipmentDto;
import edu.ijse.ayurveda_skin_care.dto.tm.EmployeeManagementTm;
import edu.ijse.ayurveda_skin_care.dto.tm.EquipmentTM;
import edu.ijse.ayurveda_skin_care.model.EquipmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private TableColumn<EquipmentTM, String> colPerchaseDate;

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

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String equipmentId = lblEquipmentId.getText();
        String name = txtName.getText();
        String serialNumber = txtSerialNumber.getText();
        String perchaseDate = txtPerchaseDate.getText();
        String lastMaintenanceDate = txtLastMaintenanceDate.getText();
        String status = txtStatus.getText();

        EquipmentDto equipmentDto = new EquipmentDto(
                equipmentId,
                name,
                serialNumber,
                perchaseDate,
                lastMaintenanceDate,
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
        String equipmentId = lblEquipmentId.getText();
        String name = txtName.getText();
        String serialNumber = txtSerialNumber.getText();
        String perchaseDate = txtPerchaseDate.getText();
        String lastMaintenanceDate = txtLastMaintenanceDate.getText();
        String status = txtStatus.getText();

        EquipmentDto equipmentListDto = new EquipmentDto(
                equipmentId,
                name,
                serialNumber,
                perchaseDate,
                lastMaintenanceDate,
                status
        );
        try {
            boolean isUpdated = equipmentModel.updateEquipment(equipmentListDto);
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
        EquipmentTM selectedItem = (EquipmentTM) tblEquipmentList.getSelectionModel().getSelectedItem();

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
