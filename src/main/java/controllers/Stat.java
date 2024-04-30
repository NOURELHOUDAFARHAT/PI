package controllers;


import entities.Bien;
import services.ServiceBien;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.MySQLConnector;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Stat implements Initializable {
    private Connection connection;

    public Stat() {
        connection = MySQLConnector.getInstance().getConnection();
    }
    @FXML
    private Button btn_ret;

    @FXML
    private PieChart pieChart;

    public void initialize (URL url, ResourceBundle resourceBundle) {

        btn_ret.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AfficherBien.fxml"));
                    Parent root = fxmlLoader.load();

                    Scene scene = new Scene(root);

                    Stage stage = (Stage) btn_ret.getScene().getWindow();

                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Appartement", 0),
                new PieChart.Data("Villa", 0),
                new PieChart.Data("Studio", 0),
                new PieChart.Data("Maison", 0)
        );
        pieChart.setData(pieChartData);

        // Appeler la méthode de recherche pour obtenir les informations des voyageurs depuis la base de données
        ServiceBien service = new ServiceBien();
        try {
            List<Bien> voyageurs = service.rechercherAll(); // Appel à la méthode rechercherAll

            // Mettre à jour les données du PieChart avec les informations des voyageurs
            for (Bien Bien : voyageurs) {
                switch (Bien.getType()) {
                    case "Appartement":
                        pieChartData.get(0).setPieValue(pieChartData.get(0).getPieValue() + 1);
                        break;
                    case "Villa":
                        pieChartData.get(1).setPieValue(pieChartData.get(1).getPieValue() + 1);
                        break;
                    case "Studio":
                        pieChartData.get(2).setPieValue(pieChartData.get(2).getPieValue() + 1);
                        break;
                    case "Maison":
                        pieChartData.get(3).setPieValue(pieChartData.get(3).getPieValue() + 1);
                        break;
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Mettre à jour le PieChart avec les nouvelles valeurs
        pieChart.setData(pieChartData);

    }
    public static void updatePieChartWithData(PieChart pieChart, List<Bien> voyageurs) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        int totalVoyageurs = voyageurs.size();
        int singleCount = 0;
        int marriedCount = 0;
        int divorcedCount = 0;
        int widowedCount = 0;

        for (Bien Bien : voyageurs) {
            switch (Bien.getType()) {
                case "Appertement":
                    singleCount++;
                    break;
                case "Villa":
                    marriedCount++;
                    break;
                case "Studio":
                    divorcedCount++;
                    break;
                case "Maison":
                    widowedCount++;
                    break;
            }
        }

        pieChartData.add(new PieChart.Data("Appertement", calculatePercentage(singleCount, totalVoyageurs)));
        pieChartData.add(new PieChart.Data("Villa", calculatePercentage(marriedCount, totalVoyageurs)));
        pieChartData.add(new PieChart.Data("Studio", calculatePercentage(divorcedCount, totalVoyageurs)));
        pieChartData.add(new PieChart.Data("Maison", calculatePercentage(widowedCount, totalVoyageurs)));

        pieChart.setData(pieChartData);

    }

    private static double calculatePercentage(int count, int total) {
        return count > 0 ? ((double) count / total) * 100 : 0;
    }
}

