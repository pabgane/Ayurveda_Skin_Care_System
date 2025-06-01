package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.db.DBConnection;
import edu.ijse.ayurveda_skin_care.dto.CustomerDto;
import edu.ijse.ayurveda_skin_care.dto.tm.CustomerTm;
import edu.ijse.ayurveda_skin_care.model.CustomerManagementModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerManagementController {

    @FXML
    private TextField lblCustomerId, txtName, txtAge, txtEmail, txtPhone, txtAddress;

    @FXML
    private DatePicker datePickerRegistration;

    @FXML
    private TableView<CustomerTm> tblCustomerList;

    @FXML
    private TableColumn<CustomerTm, String> colCustomerId, colName, colEmail, colPhone, colAddress, colRegistrationDate;

    @FXML
    private TableColumn<CustomerTm, Integer> colAge;

    private final CustomerManagementModel model = new CustomerManagementModel();

    public void initialize() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Customer_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("Registration_Date"));

        loadAllCustomers();
        generateCustomerId();
    }

    private void loadAllCustomers() {
        try {
            ArrayList<CustomerDto> customers = model.getAllCustomers();
            ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
            for (CustomerDto c : customers) {
                tmList.add(new CustomerTm(
                        c.getCustomer_Id(),
                        c.getName(),
                        c.getAge(),
                        c.getEmail(),
                        c.getPhone(),
                        c.getAddress(),
                        c.getRegistration_Date()
                ));
            }
            tblCustomerList.setItems(tmList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error loading customer data: " + e.getMessage());
        }
    }

    private void generateCustomerId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT Customer_Id FROM Customers ORDER BY Customer_Id DESC LIMIT 1;");

            if (rst.next()) {
                String id = rst.getString("Customer_Id");
                int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
                lblCustomerId.setText(String.format("C%03d", newCustomerId));
            } else {
                lblCustomerId.setText("C001");
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate customer ID").show();
            e.printStackTrace();
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        CustomerDto dto = getCustomerDataFromFields();
        if (dto == null) return;

        try {
            boolean isSaved = model.saveCustomer(dto);
            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Customer saved successfully.");
                loadAllCustomers();
                clearFields();
                generateCustomerId();
            } else {
                showAlert(Alert.AlertType.WARNING, "Failed to save customer.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        CustomerDto dto = getCustomerDataFromFields();
        if (dto == null) return;

        try {
            boolean isUpdated = model.updateCustomer(dto);
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Customer updated successfully.");
                loadAllCustomers();
                clearFields();
                generateCustomerId();
            } else {
                showAlert(Alert.AlertType.WARNING, "Failed to update customer.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = lblCustomerId.getText();
        if (id.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select a customer to delete.");
            return;
        }

        try {
            boolean isDeleted = model.deleteCustomer(id);
            if (isDeleted) {
                showAlert(Alert.AlertType.INFORMATION, "Customer deleted successfully.");
                loadAllCustomers();
                clearFields();
                generateCustomerId();
            } else {
                showAlert(Alert.AlertType.WARNING, "Failed to delete customer.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
        generateCustomerId();
    }

    public void getData(MouseEvent mouseEvent) {
        CustomerTm selected = tblCustomerList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lblCustomerId.setText(selected.getCustomer_Id());
            txtName.setText(selected.getName());
            txtAge.setText(String.valueOf(selected.getAge()));
            txtEmail.setText(selected.getEmail());
            txtPhone.setText(selected.getPhone());
            txtAddress.setText(selected.getAddress());
            datePickerRegistration.setValue(LocalDate.parse(selected.getRegistration_Date()));
        }
    }

    private CustomerDto getCustomerDataFromFields() {
        try {
            String id = lblCustomerId.getText();
            String name = txtName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String email = txtEmail.getText();
            String phone = txtPhone.getText();
            String address = txtAddress.getText();
            String regDate = datePickerRegistration.getValue().toString();

            return new CustomerDto(id, name, age, email, phone, address, regDate);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Invalid input: " + e.getMessage());
            return null;
        }
    }

    private void clearFields() {
        lblCustomerId.clear();
        txtName.clear();
        txtAge.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtAddress.clear();
        datePickerRegistration.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String msg) {
        new Alert(type, msg).show();
    }
}