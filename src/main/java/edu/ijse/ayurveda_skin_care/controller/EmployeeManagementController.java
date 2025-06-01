package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.db.DBConnection;
import edu.ijse.ayurveda_skin_care.dto.EmployeeManagementDto;
import edu.ijse.ayurveda_skin_care.dto.tm.EmployeeManagementTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.Optional;

public class EmployeeManagementController {

    @FXML
    private Label lblEmployeeId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtEmergencyContact;

    @FXML
    private TextField txtHireDate;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtStatus;

    @FXML
    private TableView<EmployeeManagementTm> tblEmployeeList;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colEmergencyContact;

    @FXML
    private TableColumn<?, ?> colHireDate;

    @FXML
    private TableColumn<?, ?> colPosition;

    @FXML
    private TableColumn<?, ?> colStatus;

    public void initialize() {
        setCellValueFactory();
        generateNextEmployeeId();
        loadAllEmployees();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Employee_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colEmergencyContact.setCellValueFactory(new PropertyValueFactory<>("emergency_contact"));
        colHireDate.setCellValueFactory(new PropertyValueFactory<>("Hire_Date"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
    }

    private void generateNextEmployeeId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT Employee_Id FROM Employees ORDER BY Employee_Id DESC LIMIT 1;");

            if (rst.next()) {
                String id = rst.getString("Employee_Id");
                int newEmployeeId = Integer.parseInt(id.replace("E", "")) + 1;
                lblEmployeeId.setText(String.format("E%03d", newEmployeeId));
            } else {
                lblEmployeeId.setText("E001");
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate employee ID").show();
            e.printStackTrace();
        }
    }

    private void loadAllEmployees() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Employees");

            ObservableList<EmployeeManagementTm> obList = FXCollections.observableArrayList();

            while (rst.next()) {
                obList.add(new EmployeeManagementTm(
                        rst.getString("Employee_Id"),
                        rst.getString("Name"),
                        rst.getString("Email"),
                        rst.getString("Phone"),
                        rst.getInt("age"),
                        rst.getString("Address"),
                        rst.getDouble("salary"),
                        rst.getString("emergency_contact"),
                        rst.getString("Hire_Date"),
                        rst.getString("Position"),
                        rst.getString("Status")
                ));
            }
            tblEmployeeList.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Employees").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        EmployeeManagementDto dto = new EmployeeManagementDto(
                lblEmployeeId.getText(),
                txtName.getText(),
                txtEmail.getText(),
                txtPhone.getText(),
                Integer.parseInt(txtAge.getText()),
                txtAddress.getText(),
                Double.parseDouble(txtSalary.getText()),
                txtEmergencyContact.getText(),
                txtHireDate.getText(),
                txtPosition.getText(),
                txtStatus.getText()
        );

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Employees VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            pstm.setString(1, dto.getEmployee_Id());
            pstm.setString(2, dto.getName());
            pstm.setString(3, dto.getEmail());
            pstm.setString(4, dto.getPhone());
            pstm.setInt(5, dto.getAge());
            pstm.setString(6, dto.getAddress());
            pstm.setDouble(7, dto.getSalary());
            pstm.setString(8, dto.getEmergency_contact());
            pstm.setString(9, dto.getHire_Date());
            pstm.setString(10, dto.getPosition());
            pstm.setString(11, dto.getStatus());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Saved!").show();
                clearFields();
                loadAllEmployees();
                generateNextEmployeeId();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save employee").show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update this employee?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            EmployeeManagementDto dto = new EmployeeManagementDto(
                    lblEmployeeId.getText(),
                    txtName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    Integer.parseInt(txtAge.getText()),
                    txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText()),
                    txtEmergencyContact.getText(),
                    txtHireDate.getText(),
                    txtPosition.getText(),
                    txtStatus.getText()
            );

            try {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE Employees SET Name=?, Email=?, Phone=?, age=?, Address=?, salary=?, emergency_contact=?, Hire_Date=?, Position=?, Status=? WHERE Employee_Id=?");
                pstm.setString(1, dto.getName());
                pstm.setString(2, dto.getEmail());
                pstm.setString(3, dto.getPhone());
                pstm.setInt(4, dto.getAge());
                pstm.setString(5, dto.getAddress());
                pstm.setDouble(6, dto.getSalary());
                pstm.setString(7, dto.getEmergency_contact());
                pstm.setString(8, dto.getHire_Date());
                pstm.setString(9, dto.getPosition());
                pstm.setString(10, dto.getStatus());
                pstm.setString(11, dto.getEmployee_Id());

                int affectedRows = pstm.executeUpdate();
                if (affectedRows > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Updated!").show();
                    clearFields();
                    loadAllEmployees();
                    generateNextEmployeeId();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update employee").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if (lblEmployeeId.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select an employee first").show();
            return;
        }

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this employee?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {
            try {
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement("DELETE FROM Employees WHERE Employee_Id=?");
                pstm.setString(1, lblEmployeeId.getText());

                int affectedRows = pstm.executeUpdate();
                if (affectedRows > 0) {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Deleted!").show();
                    clearFields();
                    loadAllEmployees();
                    generateNextEmployeeId();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete employee").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete employee").show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextEmployeeId();
    }

    @FXML
    void getData(MouseEvent event) {
        EmployeeManagementTm selectedEmployee = tblEmployeeList.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            lblEmployeeId.setText(selectedEmployee.getEmployee_Id());
            txtName.setText(selectedEmployee.getName());
            txtEmail.setText(selectedEmployee.getEmail());
            txtPhone.setText(selectedEmployee.getPhone());
            txtAge.setText(String.valueOf(selectedEmployee.getAge()));
            txtAddress.setText(selectedEmployee.getAddress());
            txtSalary.setText(String.valueOf(selectedEmployee.getSalary()));
            txtEmergencyContact.setText(selectedEmployee.getEmergency_contact());
            txtHireDate.setText(selectedEmployee.getHire_Date());
            txtPosition.setText(selectedEmployee.getPosition());
            txtStatus.setText(selectedEmployee.getStatus());
        }
    }

    private void clearFields() {
        txtName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtAge.clear();
        txtAddress.clear();
        txtSalary.clear();
        txtEmergencyContact.clear();
        txtHireDate.clear();
        txtPosition.clear();
        txtStatus.clear();
    }
}