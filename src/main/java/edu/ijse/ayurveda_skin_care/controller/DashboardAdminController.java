package edu.ijse.ayurveda_skin_care.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DashboardAdminController {


    @FXML
    public Pane sidePane;
    @FXML
    private AnchorPane ancDashBoardAdmin;

    @FXML
    void btnLogoutOnAction( ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            ancDashBoardAdmin.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            ancDashBoardAdmin.getChildren().add(load);
        }
    }

    @FXML
    public void btnEmployeeManagementOnAction(ActionEvent actionEvent) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/EmployeeManagement.fxml"));
        sidePane.getChildren().add(load);
    }
    @FXML
    public void btnCustomerManagementOnAction(ActionEvent actionEvent) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/CustomerManagement.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    public void btnEquipmentOnAction(ActionEvent actionEvent) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/EquipmentManagement.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    public void btnTreatmentOnAction(ActionEvent actionEvent) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/Treatment.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    public void btnUserManagementOnAction(ActionEvent actionEvent) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/UserManagement.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    public void btnInventoryItemOnAction(ActionEvent actionEvent) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/InventaryManagement.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnAppointmentManagementOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/AppoinmentManagement.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnPaymentMethodManagementOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/PaymentMethodManagement.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnPaymentRecordOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/PaymentRecordManagement.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnSupplierManagementOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/SupplierManagement.fxml"));
        sidePane.getChildren().add(load);
    }

    @FXML
    void btnUserTypeOnAction(ActionEvent event) throws IOException {
        sidePane.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/UserTypes.fxml"));
        sidePane.getChildren().add(load);
    }
}


