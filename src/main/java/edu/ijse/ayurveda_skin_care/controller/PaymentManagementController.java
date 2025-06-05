package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.PaymentManagementDto;
import edu.ijse.ayurveda_skin_care.dto.PaymentMethodDto;
import edu.ijse.ayurveda_skin_care.dto.tm.PaymentManagementTM;
import edu.ijse.ayurveda_skin_care.dto.tm.PaymentMethodTM;
import edu.ijse.ayurveda_skin_care.model.PaymentMethodModel;
import edu.ijse.ayurveda_skin_care.model.PaymentModel;
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

public class PaymentManagementController implements Initializable {

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
    private TableColumn<PaymentManagementTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentManagementTM, String> colAppoinment;

    @FXML
    private TableColumn<PaymentManagementTM, String> colCustomerId;

    @FXML
    private TableColumn<PaymentManagementTM, String> colMethodId;

    @FXML
    private TableColumn<PaymentManagementTM, String> colPaymentDate;

    @FXML
    private TableColumn<PaymentManagementTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentManagementTM, ?> colStatus;

    @FXML
    private Label lblPaymentId;

    @FXML
    private TableView<PaymentManagementTM> tblPaymentList;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtAppoinmentId;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtMethodId;

    @FXML
    private TextField txtPaymentDate;

    @FXML
    private TextField txtStatus;


    private final PaymentModel paymentModel = new PaymentModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("Payment_Id"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Customer_Id"));
        colAppoinment.setCellValueFactory(new PropertyValueFactory<>("Appointment_Id"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("Payment_Date"));
        colMethodId.setCellValueFactory(new PropertyValueFactory<>("Method_Id"));
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
        tblPaymentList.setItems(FXCollections.observableArrayList(
                paymentModel.getAllPRecords()
                        .stream()
                        .map(paymentManagementDto -> new PaymentManagementTM(
                                paymentManagementDto.getPayment_Id(),
                                paymentManagementDto.getCustomer_Id(),
                                paymentManagementDto.getAppointment_Id(),
                                paymentManagementDto.getAmount(),
                                paymentManagementDto.getPayment_Date(),
                                paymentManagementDto.getMethod_Id(),
                                paymentManagementDto.getStatus()
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

            txtAmount.setText(null);
            txtAppoinmentId.setText(null);
            txtCustomerId.setText(null);
            txtMethodId.setText(null);
            txtPaymentDate.setText(null);
            txtStatus.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String paymentId = lblPaymentId.getText();
        String customerId = txtCustomerId.getText();
        String appointmentId = txtAppoinmentId.getText();
        Double amount = Double.parseDouble(txtAmount.getText());
        String paymentDate = txtPaymentDate.getText();
        String methodId = txtMethodId.getText();
        String status = txtStatus.getText();


        PaymentManagementDto paymentManagementDto = new PaymentManagementDto(
                paymentId,
                customerId,
                appointmentId,
                amount,
                paymentDate,
                methodId,
                status
        );
        try {
            boolean isSaved = paymentModel.savePRecord(paymentManagementDto);

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
        String paymentId = lblPaymentId.getText();
        String customerId = txtCustomerId.getText();
        String appointmentId = txtAppoinmentId.getText();
        Double amount = Double.parseDouble(txtAmount.getText());
        String paymentDate = txtPaymentDate.getText();
        String methodId = txtMethodId.getText();
        String status = txtStatus.getText();


        PaymentManagementDto paymentManagementDto = new PaymentManagementDto(
                paymentId,
                customerId,
                appointmentId,
                amount,
                paymentDate,
                methodId,
                status
        );
        try {
            boolean isUpdated = paymentModel.updatePRecord(paymentManagementDto);
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
            String pRecordId = lblPaymentId.getText();
            try {
                boolean isDeleted = paymentModel.deletePRecord(pRecordId);
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
        String nextId = paymentModel.getNextPRecordId();
        lblPaymentId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        PaymentManagementTM selectedItem = (PaymentManagementTM) tblPaymentList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblPaymentId.setText(selectedItem.getPayment_Id());
            txtCustomerId.setText(selectedItem.getCustomer_Id());
            txtAppoinmentId.setText(selectedItem.getAppointment_Id());
            txtAmount.setText(String.valueOf(selectedItem.getAmount()));
            txtPaymentDate.setText(selectedItem.getPayment_Date());
            txtMethodId.setText(selectedItem.getMethod_Id());
            txtStatus.setText(selectedItem.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
