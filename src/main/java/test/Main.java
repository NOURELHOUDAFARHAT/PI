package test;

import entities.Bien;
import entities.Visite;
import utils.MySQLConnector;
import services.ServiceBien;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;


public class Main {

    public static void main(String[] args) throws IOException {
        MySQLConnector db1 = MySQLConnector.getInstance();
     //   Bien bien =new Bien(34,"nawara","tunis",4,400,"Villa","image.png");
       // ServiceBien serviceBien =new ServiceBien();
        //ServiceBien.ajouter(bien);


      //  LocalDate date1 = LocalDate.of(2024, 4, 1);
       // Visite visite =new Visite(63,21,55451945,date1,"nour.farhat@esprit.tn","java");
        /*ServiceVisite serviceVisite =new ServiceVisite();
        try {
            serviceVisite.modifier(visite);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

}