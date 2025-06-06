package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.TreatmentDto;
import edu.ijse.ayurveda_skin_care.dto.tm.TreatmentManagementTM;
import edu.ijse.ayurveda_skin_care.model.TreatmentManagementModel;
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

public class TreatmentManagementController implements Initializable {

    @FXML
    private AnchorPane ancTreatmentList;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TreatmentManagementTM, String> colDescription;

    @FXML
    private TableColumn<TreatmentManagementTM, String> colDuration;

    @FXML
    private TableColumn<TreatmentManagementTM, String> colName;

    @FXML
    private TableColumn<TreatmentManagementTM, String> colPrice;

    @FXML
    private TableColumn<TreatmentManagementTM, String> colTreatmentId;

    @FXML
    private Label lblTreatmentId;

    @FXML
    private TableView<TreatmentManagementTM> tblTreatmentList;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    private final TreatmentManagementModel treatmentManagementModel = new TreatmentManagementModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTreatmentId.setCellValueFactory(new PropertyValueFactory<>("Treatment_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("Duration"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        try {
            resetPage();
            loadNextId();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblTreatmentList.setItems(FXCollections.observableArrayList(
                treatmentManagementModel.getAllTreatments()
                        .stream()
                        .map(treatmentDto -> new TreatmentManagementTM(
                                treatmentDto.getTreatment_Id(),
                                treatmentDto.getName(),
                                treatmentDto.getDescription(),
                                treatmentDto.getDuration(),
                                treatmentDto.getPrice()
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
            txtDescription.setText(null);
            txtDuration.setText(null);
            txtPrice.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    private boolean validateInputs(String name, String description, String durationStr, String priceStr) {
        String namePattern = "^[A-Za-z ]{3,50}$";
        String descriptionPattern = "^[A-Za-z0-9 ,.]{5,200}$";
        String durationPattern = "^[1-9][0-9]{0,2}$"; // 1 to 999
        String pricePattern = "^\\d+(\\.\\d{1,2})?$"; // decimal with up to 2 decimal places

        if (!name.matches(namePattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name! Only letters and spaces allowed (3-50 characters).").show();
            return false;
        }

        if (!description.matches(descriptionPattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Description! Letters, numbers, spaces, commas and periods allowed (5-200 characters).").show();
            return false;
        }

        if (!durationStr.matches(durationPattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Duration! Enter a number between 1 and 999.").show();
            return false;
        }

        if (!priceStr.matches(pricePattern)) {
            new Alert(Alert.AlertType.ERROR, "Invalid Price! Enter a valid decimal number with up to 2 decimal places.").show();
            return false;
        }

        return true;
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String treatmentId = lblTreatmentId.getText();
        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();
        String durationStr = txtDuration.getText().trim();
        String priceStr = txtPrice.getText().trim();

        if (!validateInputs(name, description, durationStr, priceStr)) {
            return; // Invalid inputs - stop processing
        }

        int duration = Integer.parseInt(durationStr);
        Double price = Double.valueOf(priceStr);

        TreatmentDto treatmentDto = new TreatmentDto(
                treatmentId,
                name,
                description,
                duration,
                price
        );
        try {
            boolean isSaved = treatmentManagementModel.saveTreatment(treatmentDto);

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
        String treatmentId = lblTreatmentId.getText();
        String name = txtName.getText().trim();
        String description = txtDescription.getText().trim();
        String durationStr = txtDuration.getText().trim();
        String priceStr = txtPrice.getText().trim();

        if (!validateInputs(name, description, durationStr, priceStr)) {
            return; // Invalid inputs - stop processing
        }

        int duration = Integer.parseInt(durationStr);
        Double price = Double.valueOf(priceStr);

        TreatmentDto treatmentDto = new TreatmentDto(
                treatmentId,
                name,
                description,
                duration,
                price
        );
        try {
            boolean isUpdated = treatmentManagementModel.updateTreatment(treatmentDto);
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
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure?",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> response = alert.showAndWait();

        if (response.isPresent() && response.get() == ButtonType.YES) {
            String treatmentId = lblTreatmentId.getText();
            try {
                boolean isDeleted = treatmentManagementModel.deleteTreatment(treatmentId);
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
        String nextId = treatmentManagementModel.getNextTreatmentId();
        lblTreatmentId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        TreatmentManagementTM selectedItem = tblTreatmentList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblTreatmentId.setText(selectedItem.getTreatment_Id());
            txtName.setText(selectedItem.getName());
            txtDescription.setText(selectedItem.getDescription());
            txtDuration.setText(String.valueOf(selectedItem.getDuration()));
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
