package com.example.service;

import dataBase.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceController implements Initializable {


    @FXML
    private TableColumn<service, String> N;
    @FXML
    private TableColumn<service, Integer> P;
    @FXML
    private TableColumn<service, Integer> I;
    @FXML
    private TableColumn<service, String> A;
    @FXML
    private TableColumn<service, String> T;
    @FXML
    private TableColumn<service, Date> R;
    @FXML
    private TableView<service> serviceView;


    ObservableList<service> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection con = DbConnection.Connection();
            Statement st = con.createStatement();
            List<service> services = new ArrayList<service>();
            ResultSet result = st.executeQuery("select * from service");
            while (result.next()) {
                int I = result.getInt("id");
                String N = result.getString("name");
                String T = result.getString("type");
                int P = result.getInt("phone");
                Date R = result.getDate("rdv");
                String A = result.getString("adress");
                services.add(new service(I, N, T, P, A, R));
            }

            for (service serv : services) {
                list.add(new service(
                        serv.getId(),
                        serv.getName(),
                        serv.getType(),
                        serv.getPhone(),
                        serv.getAdress(),
                        serv.getRdv()
                ));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set cell value factories after populating the list
        I.setCellValueFactory(new PropertyValueFactory<service, Integer>("id"));
        T.setCellValueFactory(new PropertyValueFactory<service, String>("type"));
        N.setCellValueFactory(new PropertyValueFactory<service, String>("name"));
        P.setCellValueFactory(new PropertyValueFactory<service, Integer>("phone"));
        A.setCellValueFactory(new PropertyValueFactory<service, String>("adress"));
        R.setCellValueFactory(new PropertyValueFactory<service, Date>("rdv"));
        serviceView.setItems(list);
    }



}

