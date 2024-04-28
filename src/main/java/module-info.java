module com.example.projectpi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires google.api.client;
    requires com.google.api.client;
    requires google.api.services.calendar.v3.rev305;
    requires com.google.api.client.json.jackson2;
    opens controllers;
    opens entities to javafx.base;
    exports controllers;
    exports test;
}
