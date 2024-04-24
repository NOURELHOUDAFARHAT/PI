package com.example.service;

import dataBase.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ApplyController  {
    @FXML
    private RadioButton cleaning;
    @FXML
    private RadioButton gardening;
    @FXML
    private RadioButton plumbing;
    @FXML
    private ToggleGroup type;
    @FXML
    private DatePicker rdv;
    @FXML
    private TextField adress;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private Button save;

    @FXML
    private Label msj;



    @FXML
    public void initialize() {
        // Initialize the toggle group and add radio buttons to it
        type = new ToggleGroup();
        cleaning.setToggleGroup(type);
        gardening.setToggleGroup(type);
        plumbing.setToggleGroup(type);
    }

    @FXML
    void Save() {
        if (validateFields()) {
            try {
                // Get the selected radio button
                RadioButton selectedRadioButton = (RadioButton) type.getSelectedToggle();
                String serviceType = selectedRadioButton.getText();

                // Get the selected date
                LocalDate selectedDate = rdv.getValue();

                // Get other form data
                String serviceName = name.getText();
                String serviceAddress = adress.getText();
                String phoneNumber = phone.getText();

                // Insert the service data into the database
                String sql = "INSERT INTO service (name, adress, phone, type, rdv) VALUES (?, ?, ?, ?, ?)";
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dev", "Ahmed", "Ahmed");
                     PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, serviceName);
                    statement.setString(2, serviceAddress);
                    statement.setString(3, phoneNumber);
                    statement.setString(4, serviceType);
                    statement.setDate(5, Date.valueOf(selectedDate));
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        showAlert("Success", "Service request added successfully!");
                    } else {
                        showAlert("Error", "Failed to add service request.");
                    }
                } catch (SQLException ex) {
                    showAlert("Error", "An error occurred while adding service request: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } catch (Exception e) {
                showAlert("Error", "An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Please fill in all required fields.");
        }
    }

    private boolean validateFields() {
        return !name.getText().isEmpty()
                && !adress.getText().isEmpty()
                && !phone.getText().isEmpty()
                && type.getSelectedToggle() != null
                && rdv.getValue() != null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    }









