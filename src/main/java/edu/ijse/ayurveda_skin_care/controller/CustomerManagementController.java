package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.CustomerDto;
import edu.ijse.ayurveda_skin_care.dto.tm.CustomerTm;
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

    private final CustomerManagementModel model = new CustomerManagementModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Customer_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("Registration_Date"));

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
                model.getAllCustomers()
                        .stream()
                        .map(customerDto -> new CustomerTm(
                                customerDto.getCustomer_Id(),
                                customerDto.getName(),
                                customerDto.getAge(),
                                customerDto.getEmail(),
                                customerDto.getPhone(),
                                customerDto.getAddress(),
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

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!validateInput()) return;

        CustomerDto customerDto = new CustomerDto(
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
        if (!validateInput()) return;

        CustomerDto customerDto = new CustomerDto(
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

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String customerId = lblCustomerId.getText();
            try {
                boolean isDeleted = model.deleteCustomer(customerId);
                if (isDeleted) {
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
        String nextId = model.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        CustomerTm selectedItem = tblCustomerList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCustomerId.setText(selectedItem.getCustomer_Id());
            txtName.setText(selectedItem.getName());
            txtAge.setText(String.valueOf(selectedItem.getAge()));
            txtEmail.setText(selectedItem.getEmail());
            txtPhone.setText(selectedItem.getPhone());
            txtAddress.setText(selectedItem.getAddress());
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
