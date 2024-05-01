package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import entities.Bien;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceBien;
import utils.MySQLConnector;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;




public class AfficherBien {
    private String image;
    @FXML
    private Button Inserer;
    @FXML
    private TableColumn<Bien, String> TypeCol;

    @FXML
    private TableColumn<Bien, String> adressCol;

    @FXML
    private TableColumn<Bien, String> imageCol;

    @FXML
    private TableColumn<Bien, String> nameCol;

    @FXML
    private TableColumn<Bien, Integer> nbrChambreCol;
    @FXML
    private Button convertirTNDenEUR;

    @FXML
    private ComboBox<String> deviseComboBox;
    @FXML
    private TextField montantEURField;
    private double tauxChangeTND_EUR = 0.30;

    @FXML
    private TextField montantTNDField;

    @FXML
    private TableColumn<Bien, Integer> prixCol;

    @FXML
    private TableColumn<Bien, Integer> refCol;

    @FXML
    private TextField adresseModif;


    @FXML
    private ImageView image_input;
    @FXML
    private Button stat;

    @FXML
    private Button modifierBien;

    @FXML
    private TextField nomModif;

    @FXML
    private TextField nombreChambreModif;



    @FXML
    private TextField prixModif;



    @FXML
    private Button supprimerBien;

    @FXML
    private TableView<Bien> tableView;

    @FXML
    private TextField typeModif;
    @FXML
    private TextField rechercheField;
    @FXML
    private Button bntExcel;
    private Connection connection;

    @FXML
    void initialize() {
        rechercherBiens("");

         ServiceBien bienService = new ServiceBien();
        try {
            List<Bien> biens = bienService.afficher();
            ObservableList<Bien> observableList = FXCollections.observableList(biens);
            tableView.setItems(observableList);

          //  refCol.setCellValueFactory(new PropertyValueFactory<>("refB"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            adressCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
            nbrChambreCol.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
            imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        stat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Stat.fxml"));
                    Parent root = fxmlLoader.load();

                    // Create a new scene
                    Scene scene = new Scene(root);

                    // Get the stage from the button's scene
                    Stage stage = (Stage) stat.getScene().getWindow();

                    // Set the new scene
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @FXML
    void modifierBien(ActionEvent event) {
        Bien bien = tableView.getSelectionModel().getSelectedItem();
        if (bien != null) {

            String nom = nomModif.getText();
            String adresse = adresseModif.getText();
            int nbrChambre = Integer.parseInt(nombreChambreModif.getText());
            int prix = Integer.parseInt(prixModif.getText());
            String type = typeModif.getText();


            bien.setName(nom);
            bien.setAdresse(adresse);
            bien.setNbrChambre(nbrChambre);
            bien.setPrix(prix);
            bien.setType(type);
            if (image != null && !image.isEmpty()) {
                bien.setImage(image);
            }

            ServiceBien serviceBien = new ServiceBien();
            try {
                serviceBien.modifier(bien);


                initialize();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setContentText("Bien modifié");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun bien sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un bien à modifier.");
            alert.showAndWait();
        }
    }
    @FXML
    void getData(MouseEvent event) {
        Bien bien = tableView.getSelectionModel().getSelectedItem();

        if (bien != null) {
            nomModif.setText(bien.getName());
            adresseModif.setText(bien.getAdresse());
            nombreChambreModif.setText(String.valueOf(bien.getNbrChambre()));
            prixModif.setText(String.valueOf(bien.getPrix()));
            typeModif.setText(bien.getType());
            image = bien.getImage();

            double prix = bien.getPrix();
            String selectedDevise = deviseComboBox.getValue();

            if (selectedDevise != null) {
                if (selectedDevise.equals("EUR")) {
                    montantEURField.setText(String.valueOf(prix));
                } else if (selectedDevise.equals("USD")) {
                    // Convertir le prix en USD (hypothétique, taux de change à définir)
                    double tauxChangeEUR_USD = 1.19; // Par exemple
                    double prixUSD = convertirEurToUsd(prix, tauxChangeEUR_USD);
                    montantEURField.setText(String.valueOf(prixUSD));
                }} else {
                System.err.println("montantEURField is null");
            }

            if (bien.getImage() != null && !bien.getImage().isEmpty()) {
                File file = new File(bien.getImage());
                if (file.exists()) {
                    image_input.setImage(new Image("file:" + bien.getImage()));
                }
            }
        }
    }
    private double convertirEurToUsd(double montantEur, double tauxChange) {
        return montantEur * tauxChange;
    }
    @FXML
    void insertImage (ActionEvent event){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image de bien");


        File file = fileChooser.showOpenDialog(modifierBien.getScene().getWindow());
        if (file != null) {

            Image image1 = new Image(file.toURI().toString());
            image_input.setImage(image1);

            image = file.getAbsolutePath();
        }
    }
    @FXML
    void supprimerBien(ActionEvent event) {
        Bien bien = tableView.getSelectionModel().getSelectedItem();
        if (bien != null) {
            ServiceBien serviceBien = new ServiceBien();
            try {
                serviceBien.supprimer(bien);
                tableView.getItems().remove(bien);

                serviceBien.afficher();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression réussie");
                alert.setHeaderText(null);
                alert.setContentText("Le bien a été supprimé avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la suppression");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la suppression du bien : " + e.getMessage());
                alert.showAndWait();
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun bien sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un bien.");
            alert.showAndWait();
        }
    }
    @FXML
    void onRechercheFieldTextChanged(KeyEvent event) {
        String rechercheText = rechercheField.getText().trim();
        rechercherBiens(rechercheText);
    }
    private void rechercherBiens(String rechercheText) {
        ServiceBien bienService = new ServiceBien();
        try {
            List<Bien> biens = bienService.rechercher(rechercheText);

            ObservableList<Bien> observableList = FXCollections.observableList(biens);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void naviguezVersAjout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajoutBien.fxml"));
            nomModif.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void bntExcel(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        String sql = "SELECT * FROM bien";
        this.connection = MySQLConnector.getInstance().getConnection();
        PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("Détails Bien");
        HSSFRow header = sheet.createRow(0);
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Adresse");
        header.createCell(3).setCellValue("Nombre de chambre");
        header.createCell(4).setCellValue("Prix");
        header.createCell(5).setCellValue("Type");
        header.createCell(6).setCellValue("Image ");

        int index = 1;
        while (resultSet.next()) {

            HSSFRow row = sheet.createRow(index);
            row.createCell(1).setCellValue(resultSet.getString("name"));
            row.createCell(2).setCellValue(resultSet.getString("adresse"));
            row.createCell(3).setCellValue(resultSet.getString("nbr_chambre"));
            row.createCell(4).setCellValue(resultSet.getString("prix"));
            row.createCell(5).setCellValue(resultSet.getString("type"));
            row.createCell(6).setCellValue(resultSet.getString("image"));


            index++;
        }

        String filePath = "C:/Users/R I B/IdeaProjects/ProjectPi/src/main/java/excel/Bien.xls";
        FileOutputStream fileOut = new FileOutputStream(filePath);
        wb.write(fileOut);
        fileOut.close();
        resultSet.close();

        // Ouvrir le fichier Excel après l'avoir créé dans un thread séparé
        new Thread(() -> {
            try {
                openExcelFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    /*  private void openExcelFile(String filePath) throws IOException {
          File file = new File(filePath);
          Desktop desktop = Desktop.getDesktop();
          desktop.open(file);
          JOptionPane.showMessageDialog(null, "Exportation 'EXCEL' effectuée avec succès");
      }*/
    private void openExcelFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
            JOptionPane.showMessageDialog(null, "Exportation 'EXCEL' effectuée avec succès");
        } else {
            JOptionPane.showMessageDialog(null, "Le fichier Excel n'a pas été trouvé.");
        }
    }

    @FXML
    void naviguezVersVisit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherVisite.fxml"));
            prixModif.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
  /*  private double convertirTNDenEUR(double montantTND, double tauxChange) {
        return montantTND / tauxChange;
    }*/


}