package edu.ijse.ayurveda_skin_care.controller;

import edu.ijse.ayurveda_skin_care.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

public class LoginPageController {

    @FXML
    public Pane mainCont;

    @FXML
    private AnchorPane ancMainPage;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    void btnLoginOnAction( ActionEvent actionEvent) throws IOException {
        String inputUserName = txtUserName.getText();
        String inputPassword = txtPassword.getText();

        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT user_Type_Id FROM Users  WHERE Username = ? AND Password  = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, inputUserName);
            stmt.setString(2, inputPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (Objects.equals(inputUserName, "admin")) {
                    ancMainPage.getChildren().clear();
                    AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DashboardAdmin.fxml"));
                    ancMainPage.getChildren().add(load);
                }
                else if (Objects.equals(inputUserName,"cashier")) {
                    ancMainPage.getChildren().clear();
                    AnchorPane load = FXMLLoader.load(getClass().getResource("/view/DashBoardCashier.fxml"));
                    ancMainPage.getChildren().add(load);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password.");
                alert.showAndWait();
                txtUserName.clear();
                txtPassword.clear();
                txtUserName.requestFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
        }
    }


    public void btnForgotPasswordOnAction(ActionEvent actionEvent) throws IOException {
//        mainCont.getChildren().clear();
//        Pane load = FXMLLoader.load(getClass().getResource("/view/ForgotPassword.fxml"));
//        mainCont.getChildren().add(load);
    }
}
