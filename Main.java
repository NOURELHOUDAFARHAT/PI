/*
package tn.esprit.test;


import tn.esprit.entites.activite;
import tn.esprit.services.ServiceActivite;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
   public static void main(String[] args) {
       /*


//        System.out.println(MyDataBase.getInstance());
//        System.out.println(MyDataBase.getInstance());

       ServiceActivite sp = new ServiceActivite();

       try {
           // Créez un objet Timestamp pour la date_heure
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
           Date parsedDate = dateFormat.parse("20240101000000"); // Date à modifier selon vos besoins
           Timestamp timestamp = new Timestamp(parsedDate.getTime());

           // Utilisez le constructeur de Activite avec les types de données corrects
           sp.ajouter(new activite("koussay", "b", timestamp, 257, "tgthhy", "fdfdf"));
       } catch (SQLException | ParseException e) {
           System.out.println(e.getMessage());
       }

       try {
           // Créez un objet Activite avec les valeurs à modifier
           activite activite = new activite();
           activite.setId(1); // ID de l'activité à modifier
           activite.setNom("koussay");
           activite.setType("b");
           activite.setPrix(17); // Nouveau prix à définir

           // Appelez la méthode modifier avec l'objet Activite
           sp.modifier(activite);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }

       try {
           sp.supprimer(1);
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }

       try {
           System.out.println(sp.recuperer());
       } catch (SQLException e) {
           System.out.println(e.getMessage());
       }


   }

        */
