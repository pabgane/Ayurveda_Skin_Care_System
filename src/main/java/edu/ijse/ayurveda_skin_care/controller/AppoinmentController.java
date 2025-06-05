package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.AppoinmentDto;
import edu.ijse.ayurveda_skin_care.dto.EquipmentDto;
import edu.ijse.ayurveda_skin_care.dto.tm.AppoinmentTM;
import edu.ijse.ayurveda_skin_care.dto.tm.EquipmentTM;
import edu.ijse.ayurveda_skin_care.model.AppoinmentManagementModel;
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

public class AppoinmentController implements Initializable {

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
    private TableColumn<AppoinmentTM, String> colAppoinmentDate;

    @FXML
    private TableColumn<AppoinmentTM, String> colAppoinmentId;

    @FXML
    private TableColumn<AppoinmentTM, String> colAppoinmentTime;

    @FXML
    private TableColumn<AppoinmentTM, String> colCustomerId;

    @FXML
    private TableColumn<AppoinmentTM, String> colEmployeeId;

    @FXML
    private TableColumn<AppoinmentTM, String> colNotes;

    @FXML
    private TableColumn<AppoinmentTM, String> colStatus;

    @FXML
    private TableColumn<AppoinmentTM, String> colTreatmentId;

    @FXML
    private Label lblAppoinmentId;

    @FXML
    private TableView<AppoinmentTM> tblAppoinmentList;

    @FXML
    private TextField txtAppoinmentDate;

    @FXML
    private TextField txtAppoinmentTime;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtNotes;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtTreatmentId;

    private final AppoinmentManagementModel appoinmentManagementModel = new AppoinmentManagementModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colAppoinmentId.setCellValueFactory(new PropertyValueFactory<>("Appointment_Id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Customer_Id"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("Employee_Id"));
        colTreatmentId.setCellValueFactory(new PropertyValueFactory<>("Treatment_Id"));
        colAppoinmentDate.setCellValueFactory(new PropertyValueFactory<>("Appointment_Date"));
        colAppoinmentTime.setCellValueFactory(new PropertyValueFactory<>("Appointment_Time"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("Notes"));

        try {
            resetPage();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }

    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblAppoinmentList.setItems(FXCollections.observableArrayList(
                appoinmentManagementModel.getAllAppoinment()
                        .stream()
                        .map(appoinmentDto -> new AppoinmentTM(
                                appoinmentDto.getAppointment_Id(),
                                appoinmentDto.getCustomer_Id(),
                                appoinmentDto.getEmployee_Id(),
                                appoinmentDto.getTreatment_Id(),
                                appoinmentDto.getAppointment_Date(),
                                appoinmentDto.getAppointment_Time(),
                                appoinmentDto.getStatus(),
                                appoinmentDto.getNotes()
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

            txtAppoinmentDate.setText(null);
            txtAppoinmentTime.setText(null);
            txtCustomerId.setText(null);
            txtEmployeeId.setText(null);
            txtTreatmentId.setText(null);
            txtNotes.setText(null);
            txtStatus.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private boolean isValidAppointmentData(String appointmentId, String customerId, String employeeId,
                                           String treatmentId, String date, String time, String status) {

        if (appointmentId.isEmpty() || customerId.isEmpty() || employeeId.isEmpty() ||
                treatmentId.isEmpty() || date.isEmpty() || time.isEmpty() || status.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all required fields.").show();
            return false;
        }

        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            new Alert(Alert.AlertType.WARNING, "Date must be in format YYYY-MM-DD.").show();
            return false;
        }

        if (!time.matches("\\d{2}:\\d{2}:\\d{2}")) {
            new Alert(Alert.AlertType.WARNING, "Time must be in format HH:mm.").show();
            return false;
        }

        if (!status.matches("(?i)Active|Inactive|Cancelled")) {
            new Alert(Alert.AlertType.WARNING, "Status must be: Active,, Inactive, or Cancelled.").show();
            return false;
        }


        return true;
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String appointmentId = lblAppoinmentId.getText();
        String customerId = txtCustomerId.getText();
        String employeeId = txtEmployeeId.getText();
        String treatmentId = txtTreatmentId.getText();
        String appointmentDate = txtAppoinmentDate.getText();
        String appointmentTime = txtAppoinmentTime.getText();
        String status = txtStatus.getText();
        String notes = txtNotes.getText();

        if (!isValidAppointmentData(appointmentId, customerId, employeeId, treatmentId, appointmentDate, appointmentTime, status)) {
            return;
        }

        AppoinmentDto appoinmentDto = new AppoinmentDto(
                appointmentId,
                customerId,
                employeeId,
                treatmentId,
                appointmentDate,
                appointmentTime,
                status,
                notes
        );
        try {
            boolean isSaved = appoinmentManagementModel.saveAppoinment(appoinmentDto);

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
        String appointmentId = lblAppoinmentId.getText();
        String customerId = txtCustomerId.getText();
        String employeeId = txtEmployeeId.getText();
        String treatmentId = txtTreatmentId.getText();
        String appointmentDate = txtAppoinmentDate.getText();
        String appointmentTime = txtAppoinmentTime.getText();
        String status = txtStatus.getText();
        String notes = txtNotes.getText();

        if (!isValidAppointmentData(appointmentId, customerId, employeeId, treatmentId, appointmentDate, appointmentTime, status)) {
            return;
        }

        AppoinmentDto appoinmentDto = new AppoinmentDto(
                appointmentId,
                customerId,
                employeeId,
                treatmentId,
                appointmentDate,
                appointmentTime,
                status,
                notes
        );
        try {
            boolean isUpdated = appoinmentManagementModel.updateAppoinment(appoinmentDto);
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
            String appoinmentId = lblAppoinmentId.getText();
            try {
                boolean isDeleted = appoinmentManagementModel.deleteAppionment(appoinmentId);
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
        String nextId = appoinmentManagementModel.getNextAppoinmentId();
        lblAppoinmentId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        AppoinmentTM selectedItem = (AppoinmentTM) tblAppoinmentList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblAppoinmentId.setText(selectedItem.getAppointment_Id());
            txtCustomerId.setText(selectedItem.getCustomer_Id());
            txtEmployeeId.setText(selectedItem.getEmployee_Id());
            txtTreatmentId.setText(selectedItem.getTreatment_Id());
            txtAppoinmentDate.setText(selectedItem.getAppointment_Date());
            txtAppoinmentTime.setText(selectedItem.getAppointment_Time());
            txtStatus.setText(selectedItem.getStatus());
            txtNotes.setText(selectedItem.getNotes());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

}
