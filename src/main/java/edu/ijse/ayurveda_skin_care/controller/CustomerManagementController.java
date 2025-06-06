package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.CustomerDto;
import edu.ijse.ayurveda_skin_care.dto.tm.CustomerTm;
import edu.ijse.ayurveda_skin_care.model.AppoinmentManagementModel;
import edu.ijse.ayurveda_skin_care.model.CustomerManagementModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerManagementController {

    @FXML private AnchorPane ancCustomerManagement;
    @FXML private Button btnClear, btnDelete, btnSave, btnUpdate;
    @FXML private TableColumn<CustomerTm, String> colAddress, colCustomerId, colEmail, colName, colPhone, colRegistrationDate;
    @FXML private TableColumn<CustomerTm, Integer> colAge;
    @FXML private Label lblCustomerId;
    @FXML private TableView<CustomerTm> tblCustomerList;
    @FXML private TextField txtAddress, txtAge, txtEmail, txtName, txtPhone, txtRegistrationDate;

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

            txtAddress.clear();
            txtAge.clear();
            txtEmail.clear();
            txtName.clear();
            txtPhone.clear();
            txtRegistrationDate.clear();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

<<<<<<< HEAD
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
=======
    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;
>>>>>>> refs/remotes/origin/main


        if (!isValidAppointmentData(customerId, name, email, phone, address, ageText, registrationDate)) {
            return;
        }

        CustomerDto customerDto = new CustomerDto(
<<<<<<< HEAD
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

=======
                lblCustomerId.getText(),
                txtName.getText(),
                Integer.parseInt(txtAge.getText()),
                txtEmail.getText(),
                txtPhone.getText(),
                txtAddress.getText(),
                txtRegistrationDate.getText()
        );
        try {
            boolean isSaved = model.saveCustomer(customerDto);
>>>>>>> refs/remotes/origin/main
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Save failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
<<<<<<< HEAD
        String customerId = lblCustomerId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String ageText = txtAge.getText();
        String registrationDate = txtRegistrationDate.getText();
=======
        if (!validateInput()) return;
>>>>>>> refs/remotes/origin/main


        if (!isValidAppointmentData(customerId, name, email, phone, address, ageText, registrationDate)) {
            return;
        }

        CustomerDto customerDto = new CustomerDto(
<<<<<<< HEAD
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
=======
                lblCustomerId.getText(),
                txtName.getText(),
                Integer.parseInt(txtAge.getText()),
                txtEmail.getText(),
                txtPhone.getText(),
                txtAddress.getText(),
                txtRegistrationDate.getText()
        );
        try {
            boolean isUpdated = model.updateCustomer(customerDto);
            if (isUpdated) {
>>>>>>> refs/remotes/origin/main
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Updated").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> response = alert.showAndWait();

<<<<<<< HEAD
        if(response.isPresent() && response.get() == ButtonType.YES){
            String cusId = lblCustomerId.getText();
            try {
                boolean isDeleted = customerManagementModel.deleteCustomer(cusId);
                if(isDeleted){
=======
        if (response.isPresent() && response.get() == ButtonType.YES) {
            String customerId = lblCustomerId.getText();
            try {
                boolean isDeleted = model.deleteCustomer(customerId);
                if (isDeleted) {
>>>>>>> refs/remotes/origin/main
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete failed").show();
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
        String nextId = customerManagementModel.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        CustomerTm selectedItem = tblCustomerList.getSelectionModel().getSelectedItem();

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

    private boolean validateInput() {
        String name = txtName.getText();
        String age = txtAge.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String registrationDate = txtRegistrationDate.getText();

        if (!name.matches("^[A-Za-z ]{3,30}$")) {
            showAlert("Invalid name. Use only letters (3-30 characters).");
            return false;
        }
        if (!age.matches("^[1-9][0-9]?$|^1[01][0-9]$|^120$")) {
            showAlert("Invalid age. Enter a valid age between 1-120.");
            return false;
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showAlert("Invalid email format.");
            return false;
        }
        if (!phone.matches("^(07\\d{8})|(0\\d{9})$")) {
            showAlert("Invalid phone. Use format like 0712345678.");
            return false;
        }
        if (address == null || address.trim().isEmpty()) {
            showAlert("Address cannot be empty.");
            return false;
        }
        if (!registrationDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            showAlert("Invalid date. Use format yyyy-mm-dd.");
            return false;
        }

        return true;
    }

    private void showAlert(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }
}
