package controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import org.json.JSONObject;
import securite.CustomSession;


public class meteo {
    @FXML
    private Button id_retour;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Text currentTemp;

    @FXML
    private Text felt_temp;

    @FXML
    private Button getWeather;

    @FXML
    private Text pressure;

    @FXML
    private Text wind;
    @FXML
    private Label id_nom1;
    @FXML
    private Label userEmailLabel;
    private static String loggedInUserEmail;
    @FXML
    private BorderPane borderPane;



    @FXML
    void initialize() {
        if (!CustomSession.isUserLoggedIn()) {
            redirectToLoginPage();
        } else {
            String loggedInEmail = CustomSession.getLoggedInUserEmail();
            id_nom1.setText(loggedInUserEmail);
            userEmailLabel.setText(loggedInEmail);

        }
        getWeather.setOnAction(
                event -> {
                    String usercity = city.getText().trim();
                    if(!usercity.isEmpty()){
                        String output = getContent("http://api.weatherapi.com/v1/current.json?key=805f609064bc43ec8d2105338241502%20&q="+ usercity +"&aqi=no");

                        JSONObject object = new JSONObject(output);
                        currentTemp.setText(String.valueOf(object.getJSONObject("current").getDouble("temp_c")));
                        felt_temp.setText(String.valueOf(object.getJSONObject("current").getDouble("feelslike_c")));
                        wind.setText(String.valueOf(object.getJSONObject("current").getDouble("wind_kph")));
                        pressure.setText(String.valueOf(object.getJSONObject("current").getDouble("pressure_mb")));
                    }
                }
        );
    }
    private void redirectToLoginPage() {
        try {
            // Chargement de la page de connexion
            Parent loginPage = FXMLLoader.load(getClass().getResource("Seconnecter.fxml"));
            Scene scene = new Scene(loginPage);
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String getContent(String urlAdress){
        StringBuffer content = new StringBuffer();

        try{
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Такой город не найден!");
        }
        return content.toString();
    }
    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/activite.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Affichez une erreur si le chargement de la page de connexion échoue
            System.err.println("Erreur lors du chargement de la page de connexion : " + ex.getMessage());

        }

    }

}
