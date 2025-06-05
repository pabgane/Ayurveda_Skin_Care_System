package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.AppoinmentDto;
import edu.ijse.ayurveda_skin_care.dto.PaymentMethodDto;
import edu.ijse.ayurveda_skin_care.dto.tm.AppoinmentTM;
import edu.ijse.ayurveda_skin_care.dto.tm.PaymentMethodTM;
import edu.ijse.ayurveda_skin_care.model.AppoinmentManagementModel;
import edu.ijse.ayurveda_skin_care.model.PaymentMethodModel;
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

public class PaymentMethodController implements Initializable {

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
    private TableColumn<PaymentMethodTM, String> colMethodId;

    @FXML
    private TableColumn<PaymentMethodTM, String> colName;

    @FXML
    private Label lblMethodId;

    @FXML
    private TableView<PaymentMethodTM> tblPaymentMethodList;

    @FXML
    private TextField txtMethod;


    private final PaymentMethodModel paymentMethodModel = new PaymentMethodModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMethodId.setCellValueFactory(new PropertyValueFactory<>("Method_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Method"));

        try {
            resetPage();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }

    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblPaymentMethodList.setItems(FXCollections.observableArrayList(
                paymentMethodModel.getAllPaymentMethod()
                        .stream()
                        .map(paymentMethodDto -> new PaymentMethodTM(
                                paymentMethodDto.getMethod_Id(),
                                paymentMethodDto.getMethod()
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

            txtMethod.setText(null);


        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String methodId = lblMethodId.getText();
        String method = txtMethod.getText();

        PaymentMethodDto paymentMethodDto = new PaymentMethodDto(
                methodId,
                method
        );
        try {
            boolean isSaved = paymentMethodModel.saveMethod(paymentMethodDto);

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
        String methodId = lblMethodId.getText();
        String method = txtMethod.getText();

        PaymentMethodDto paymentMethodDto = new PaymentMethodDto(
                methodId,
                method
        );
        try {
            boolean isUpdated = paymentMethodModel.updateMethod(paymentMethodDto);
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
            String methodId = lblMethodId.getText();
            try {
                boolean isDeleted = paymentMethodModel.deleteMethod(methodId);
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
        String nextId = paymentMethodModel.getNextMethodId();
        lblMethodId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        PaymentMethodTM selectedItem = (PaymentMethodTM) tblPaymentMethodList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblMethodId.setText(selectedItem.getMethod_Id());
            txtMethod.setText(selectedItem.getMethod());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
