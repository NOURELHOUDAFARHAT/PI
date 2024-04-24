package com.example.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class HomeController {

    @FXML
    private Button service;
    @FXML
    private Button staff;

    @FXML
    private Button apply;

    @FXML
    public void goToServices(MouseEvent event) throws IOException {

            try {

                Parent root =FXMLLoader.load(getClass().getResource("service.fxml"));
                Scene scene =new Scene(root);
                Stage stage =new Stage();
                stage.setScene(scene);
                stage.show();



            }catch(NullPointerException e) {
                System.out.println(e);
            }

    }

    @FXML
    public void goToStaff(MouseEvent event) throws IOException {

        try {

            Parent root =FXMLLoader.load(getClass().getResource("Staff.fxml"));
            Scene scene =new Scene(root);
            Stage stage =new Stage();
            stage.setScene(scene);
            stage.show();



        }catch(NullPointerException e) {
            System.out.println(e);
        }

    }

    @FXML
    public void goToApply(MouseEvent event) throws IOException {

        try {

            Parent root =FXMLLoader.load(getClass().getResource("Apply.fxml"));
            Scene scene =new Scene(root);
            Stage stage =new Stage();
            stage.setScene(scene);
            stage.show();



        }catch(NullPointerException e) {
            System.out.println(e);
        }

    }
}
