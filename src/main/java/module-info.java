module edu.ijse.ayurveda_skin_care {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens edu.ijse.ayurveda_skin_care.controller to javafx.fxml;
    exports edu.ijse.ayurveda_skin_care;
}