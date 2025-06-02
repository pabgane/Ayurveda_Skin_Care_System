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

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String treatmentId = lblTreatmentId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        int duration = Integer.parseInt(txtDuration.getText());
        Double price = Double.valueOf(txtPrice.getText());

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
                new Alert(Alert.AlertType.ERROR, "Fail").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String treatmentId = lblTreatmentId.getText();
        String name = txtName.getText();
        String description = txtDescription.getText();
        int duration = Integer.parseInt(txtDuration.getText());
        Double price = Double.valueOf(txtPrice.getText());

        TreatmentDto treatmentDto = new TreatmentDto(
                treatmentId,
                name,
                description,
                duration,
                price
        );
        try {
            boolean isUpdated = treatmentManagementModel.updateTreatment(treatmentDto);
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
            String treatmentId = lblTreatmentId.getText();
            try {
                boolean isDeleted = treatmentManagementModel.deleteTreatment(treatmentId);
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
