package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.db.DBConnection;
import edu.ijse.ayurveda_skin_care.dto.AppoinmentDto;
import edu.ijse.ayurveda_skin_care.dto.CustomerDto;
import edu.ijse.ayurveda_skin_care.dto.tm.AppoinmentTM;
import edu.ijse.ayurveda_skin_care.dto.tm.CustomerTm;
import edu.ijse.ayurveda_skin_care.model.AppoinmentManagementModel;
import edu.ijse.ayurveda_skin_care.model.CustomerManagementModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerManagementController {

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
    private TableColumn<CustomerTm, String> colAddress;

    @FXML
    private TableColumn<CustomerTm, Integer> colAge;

    @FXML
    private TableColumn<CustomerTm, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTm, String> colEmail;

    @FXML
    private TableColumn<CustomerTm, String> colName;

    @FXML
    private TableColumn<CustomerTm, String> colPhone;

    @FXML
    private TableColumn<CustomerTm, String> colRegistrationDate;

    @FXML
    private Label lblCustomerId;

    @FXML
    private TableView<CustomerTm> tblCustomerList;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtRegistrationDate;

    private final CustomerManagementModel customerManagementModel = new CustomerManagementModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customer_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("registration_Date"));

        try {
            resetPage();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }

    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblCustomerList.setItems(FXCollections.observableArrayList(
                customerManagementModel.getAllCustomers()
                        .stream()
                        .map(customerDto -> new CustomerTm(
                                customerDto.getCustomer_Id(),
                                customerDto.getName(),
                                customerDto.getEmail(),
                                customerDto.getPhone(),
                                customerDto.getAddress(),
                                customerDto.getAge(),
                                customerDto.getRegistration_Date()
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

            txtAddress.setText(null);
            txtAge.setText(null);
            txtEmail.setText(null);
            txtName.setText(null);
            txtPhone.setText(null);
            txtRegistrationDate.setText(null);


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
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String ageText = txtAge.getText();
        String registrationDate = txtRegistrationDate.getText();


        if (!isValidAppointmentData(customerId, name, email, phone, address, ageText, registrationDate)) {
            return;
        }

        CustomerDto customerDto = new CustomerDto(
                customerId,
                name,
                email,
                phone,
                address,
                Integer.parseInt(ageText),
                registrationDate
        );
        try {
            boolean isSaved = customerManagementModel.saveCustomer(customerDto);

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
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String ageText = txtAge.getText();
        String registrationDate = txtRegistrationDate.getText();


        if (!isValidAppointmentData(customerId, name, email, phone, address, ageText, registrationDate)) {
            return;
        }

        CustomerDto customerDto = new CustomerDto(
                customerId,
                name,
                email,
                phone,
                address,
                Integer.parseInt(ageText),
                registrationDate
        );
        try {
            boolean isUpdated = customerManagementModel.updateCustomer(customerDto);
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
            String cusId = lblCustomerId.getText();
            try {
                boolean isDeleted = customerManagementModel.deleteCustomer(cusId);
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
        String nextId = customerManagementModel.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        CustomerTm selectedItem = (CustomerTm) tblCustomerList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCustomerId.setText(selectedItem.getCustomer_Id());
            txtName.setText(selectedItem.getName());
            txtEmail.setText(selectedItem.getEmail());
            txtPhone.setText(selectedItem.getPhone());
            txtAddress.setText(selectedItem.getAddress());
            txtAge.setText(String.valueOf(selectedItem.getAge()));
            txtRegistrationDate.setText(selectedItem.getRegistration_Date());


            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}