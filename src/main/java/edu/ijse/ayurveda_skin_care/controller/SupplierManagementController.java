package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.SupplierManagementDto;
import edu.ijse.ayurveda_skin_care.dto.tm.SupplierManagementTM;
import edu.ijse.ayurveda_skin_care.model.SupplierManagementModel;
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
import java.util.regex.Pattern;

public class SupplierManagementController implements Initializable {

    @FXML
    private AnchorPane ancSupplierManagement;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SupplierManagementTM, String> colAddress;

    @FXML
    private TableColumn<SupplierManagementTM, String> colCustomerId;

    @FXML
    private TableColumn<SupplierManagementTM, String> colEmail;

    @FXML
    private TableColumn<SupplierManagementTM, String> colName;

    @FXML
    private TableColumn<SupplierManagementTM, String> colPhone;

    @FXML
    private Label lblSupplierId;

    @FXML
    private TableView<SupplierManagementTM> tblSupplierList;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    private final SupplierManagementModel supplierManagementModel = new SupplierManagementModel();

    // Regex patterns
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]{3,50}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[\\w\\s.,'-]{5,100}$");

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Supplier_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));

        try {
            resetPage();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblSupplierList.setItems(FXCollections.observableArrayList(
                supplierManagementModel.getAllSuppliers()
                        .stream()
                        .map(supplierManagementDto -> new SupplierManagementTM(
                                supplierManagementDto.getSupplier_Id(),
                                supplierManagementDto.getName(),
                                supplierManagementDto.getEmail(),
                                supplierManagementDto.getPhone(),
                                supplierManagementDto.getAddress()
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
            txtPhone.setText(null);
            txtAddress.setText(null);
            txtEmail.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private boolean validateInputs() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        if (name == null || !NAME_PATTERN.matcher(name).matches()) {
            showAlert("Invalid Name", "Name should contain only letters and spaces (3-50 characters).");
            return false;
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches()) {
            showAlert("Invalid Phone", "Phone number should be exactly 10 digits.");
            return false;
        }
        if (address == null || !ADDRESS_PATTERN.matcher(address).matches()) {
            showAlert("Invalid Address", "Address should be 5-100 characters and can contain letters, numbers, spaces, and basic punctuation.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return;
        }

        String supplierId = lblSupplierId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        SupplierManagementDto supplierManagementDto = new SupplierManagementDto(
                supplierId,
                name,
                email,
                phone,
                address
        );
        try {
            boolean isSaved = supplierManagementModel.saveSupplier(supplierManagementDto);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save supplier.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (!validateInputs()) {
            return;
        }

        String supplierId = lblSupplierId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();

        SupplierManagementDto supplierManagementDto = new SupplierManagementDto(
                supplierId,
                name,
                email,
                phone,
                address
        );
        try {
            boolean isUpdated = supplierManagementModel.updateSupplier(supplierManagementDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Updated").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update supplier.").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
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

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String supplierId = lblSupplierId.getText();
            try {
                boolean isDeleted = supplierManagementModel.deleteSupplier(supplierId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete supplier.").show();
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
        String nextId = supplierManagementModel.getNextSupplierId();
        lblSupplierId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        SupplierManagementTM selectedItem = tblSupplierList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblSupplierId.setText(selectedItem.getSupplier_Id());
            txtName.setText(selectedItem.getName());
            txtEmail.setText(selectedItem.getEmail());
            txtPhone.setText(selectedItem.getPhone());
            txtAddress.setText(selectedItem.getAddress());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
