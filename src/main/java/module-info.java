module edu.ijse.ayurveda_skin_care {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens edu.ijse.ayurveda_skin_care.dto.tm to javafx.base;
    opens edu.ijse.ayurveda_skin_care.dto to javafx.base;
    opens edu.ijse.ayurveda_skin_care.controller to javafx.fxml;

    exports edu.ijse.ayurveda_skin_care;
    exports edu.ijse.ayurveda_skin_care.controller;
    exports edu.ijse.ayurveda_skin_care.dto;
    exports edu.ijse.ayurveda_skin_care.dto.tm;
}