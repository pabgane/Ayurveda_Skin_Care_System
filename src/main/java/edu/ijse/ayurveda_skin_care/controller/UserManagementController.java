package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.dto.UserDto;
import edu.ijse.ayurveda_skin_care.dto.tm.UserManagementTM;
import edu.ijse.ayurveda_skin_care.model.UserManageModel;
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

public class UserManagementController implements Initializable {

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
    private TableColumn<UserManagementTM, String> colAge;

    @FXML
    private TableColumn<UserManagementTM, String> colEmail;

    @FXML
    private TableColumn<UserManagementTM, String> colName;

    @FXML
    private TableColumn<UserManagementTM, String> colPassword;

    @FXML
    private TableColumn<UserManagementTM, String> colPhone;

    @FXML
    private TableColumn<UserManagementTM, String> colRegistrationDate;

    @FXML
    private TableColumn<UserManagementTM, String> colStatus;

    @FXML
    private TableColumn<UserManagementTM, String> colUserId;

    @FXML
    private TableColumn<UserManagementTM, String> colUserName;

    @FXML
    private TableColumn<UserManagementTM, String> colUserTypeId;

    @FXML
    private Label lblUserId;

    @FXML
    private TableView<UserManagementTM> tblUserList;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtRegistrationDate;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtUserTypeId;

    private final UserManageModel userManageModel = new UserManageModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("User_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("Username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colUserTypeId.setCellValueFactory(new PropertyValueFactory<>("User_Type_Id"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("Registration_Date"));
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
        tblUserList.setItems(FXCollections.observableArrayList(
                userManageModel.getAllUser()
                        .stream()
                        .map(userDto -> new UserManagementTM(
                                userDto.getUser_Id(),
                                userDto.getName(),
                                userDto.getPhone(),
                                userDto.getUsername(),
                                userDto.getPassword(),
                                userDto.getEmail(),
                                userDto.getAge(),
                                userDto.getUser_Type_Id(),
                                userDto.getRegistration_Date(),
                                userDto.getStatus()
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
            txtUserName.setText(null);
            txtPassword.setText(null);
            txtEmail.setText(null);
            txtAge.setText(null);
            txtUserTypeId.setText(null);
            txtRegistrationDate.setText(null);
            txtStatus.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String userId = lblUserId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        int age = Integer.parseInt(txtAge.getText());
        String userTypeId = txtUserTypeId.getText();
        String registrationDate = txtRegistrationDate.getText();
        String status = txtStatus.getText();


        UserDto userDto = new UserDto(
                userId,
                name,
                phone,
                username,
                password,
                email,
                age,
                userTypeId,
                registrationDate,
                status
        );
        try {
            boolean isSaved = userManageModel.saveUser(userDto);

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
        String userId = lblUserId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        int age = Integer.parseInt(txtAge.getText());
        String userTypeId = txtUserTypeId.getText();
        String registrationDate = txtRegistrationDate.getText();
        String status = txtStatus.getText();


        UserDto userDto = new UserDto(
                userId,
                name,
                phone,
                username,
                password,
                email,
                age,
                userTypeId,
                registrationDate,
                status
        );
        try {
            boolean isUpdated = userManageModel.updateUser(userDto);
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
            String userId = lblUserId.getText();
            try {
                boolean isDeleted = userManageModel.deleteUser(userId);
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
        String nextId = userManageModel.getNextUserId();
        lblUserId.setText(nextId);
    }

    public void getData(MouseEvent mouseEvent) {
        UserManagementTM selectedItem = tblUserList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblUserId.setText(selectedItem.getUser_Id());
            txtName.setText(selectedItem.getName());
            txtPhone.setText(selectedItem.getPhone());
            txtUserName.setText(selectedItem.getUsername());
            txtPassword.setText(selectedItem.getPassword());
            txtEmail.setText(selectedItem.getEmail());
            txtAge.setText(String.valueOf(selectedItem.getAge()));
            txtUserTypeId.setText(selectedItem.getUser_Type_Id());
            txtRegistrationDate.setText(selectedItem.getRegistration_Date());
            txtStatus.setText(selectedItem.getStatus());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }
}
