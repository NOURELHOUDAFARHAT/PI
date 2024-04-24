package com.example.service;


import dataBase.DbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.cj.xdevapi.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;




public class StaffController implements Initializable {

    @FXML
    private Button modify;
    @FXML
    private Button click;
    @FXML
    private Button add;
    @FXML
    private Button delete;

    @FXML
    private TextField nameC;

    @FXML
    private TextField idC;

    @FXML
    private TextField jobC;
    @FXML
    private TextField scoreC;

    @FXML
    private TableView<Staff> StaffView;

    @FXML
    private TableColumn<Staff, Integer> I;
    @FXML
    private TableColumn<Staff, String> N;
    @FXML
    private TableColumn<Staff,String > J;
    @FXML
    private TableColumn<Staff, Integer> S;

    ObservableList<Staff> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        list.clear();
        try {
            List<Staff> staffs= DbConnection.staffs();
            for(Staff staff:staffs) {

                list.add(new Staff(staff.getStaffId(),
                        staff.getName(),
                        staff.getJob(),
                        staff.getScore()
                ));
            }

        }catch(Exception e) {

            e.printStackTrace();

        }
        I.setCellValueFactory(new PropertyValueFactory<Staff,Integer>("staffId"));
        N.setCellValueFactory(new PropertyValueFactory<Staff,String>("name"));
        J.setCellValueFactory(new PropertyValueFactory<Staff,String>("job"));
        S.setCellValueFactory(new PropertyValueFactory<Staff,Integer>("score"));
        StaffView.setItems(list);
    }

    @FXML
    void Add(MouseEvent event)  {

        try {

            Connection c =DbConnection.Connection();

            java.sql.Statement st =c.createStatement();
            //rs=stat.executeUpdate();

            String name = nameC.getText();
            String job = jobC.getText();
            String score = scoreC.getText();
            String sql = "insert into staff(name,job,score) values('"+name+"','"+job+"','"+score+"')";
            st.execute(sql);
            nameC.setText(null);
            jobC.setText(null);
            scoreC.setText(null);

            initialize(null, null);



        }catch(Exception e) {
            e.printStackTrace();
        }


    }
    @FXML
    void Delete(MouseEvent event) {


        try {
            Connection c = DbConnection.Connection();
            int staffId = Integer.parseInt(idC.getText());

            String sql = "DELETE FROM staff WHERE staffId = ?";
            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setInt(1, staffId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("Staff deleted!");
                alert.show();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Staff with ID " + staffId + " not found!");
                alert.show();
            }

            initialize(null, null);
            idC.setText(null);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please enter a valid number for staff ID!");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("An error occurred while deleting the staff!");
            alert.show();
        }

    }
    @FXML
    void Modify(MouseEvent event) {


        try {
            Connection c = DbConnection.Connection();

            int id = Integer.parseInt(idC.getText());

            // Check if scoreC is not empty before parsing its value
            String scoreText = scoreC.getText();
            if (scoreText.isEmpty()) {
                throw new NumberFormatException("Score cannot be empty");
            }
            int score = Integer.parseInt(scoreText);

            // Use parameterized query to avoid SQL injection
            String sql = "UPDATE staff SET score = ?, name = ?, job = ? WHERE staffId = ?";
            PreparedStatement stmt = c.prepareStatement(sql);

            // Set parameters for PreparedStatement
            stmt.setInt(1, score);
            stmt.setString(2, nameC.getText());
            stmt.setString(3, jobC.getText());
            stmt.setInt(4, id);

            stmt.executeUpdate();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Staff modified!");
            alert.show();

            // Clear text fields
            idC.setText(null);
            nameC.setText(null);
            jobC.setText(null);
            scoreC.setText(null);

            initialize(null, null);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please enter a valid number for score!");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("An error occurred while modifying the staff!");
            alert.show();
        }



    }
    @FXML
    void Click(MouseEvent event) {
        if (StaffView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please select a staff to modify!");
            alert.show();
            return; // Exit the method if no staff is selected
        }

        // Get the selected staff from the TableView
        Staff selectedStaff = StaffView.getSelectionModel().getSelectedItem();

        // Populate the modifying form fields with the selected staff information
        idC.setText(String.valueOf(selectedStaff.getStaffId()));
        nameC.setText(selectedStaff.getName());
        jobC.setText(selectedStaff.getJob());
        scoreC.setText(String.valueOf(selectedStaff.getScore()));
    }
}