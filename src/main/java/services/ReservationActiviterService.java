package services;

import entities.reservationsdesactiviter;
import utils.MySQLConnector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationActiviterService implements IReservationActiviter<ReservationActiviterService> {
    Connection cnx = MySQLConnector.getInstance().getConnection();
    @Override
    public void addReservationActiviter(reservationsdesactiviter reservationsdesactiviter) {
        try {
            String req = "INSERT INTO reservations_des_activiter (nom, type, prix, date) VALUES ( ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(req);


            pst.setString(1, reservationsdesactiviter.getNom());
            pst.setString(2, reservationsdesactiviter.getType());
            pst.setInt(3, reservationsdesactiviter.getPrix());
            pst.setDate(4, (Date) reservationsdesactiviter.getDate());

            pst.executeUpdate();
            System.out.println("Voiture ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void supprimer(reservationsdesactiviter reservationsdesactiviter) {
        try {
            String req = "DELETE FROM reservations_des_activiter WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, reservationsdesactiviter.getId());
            pst.executeUpdate();
            System.out.println("reservation des biens  suprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override

        public List<reservationsdesactiviter> afficher() {
            List<reservationsdesactiviter> list = new ArrayList<>();
            try {
                String req = "SELECT * FROM reservations_des_activiter";
                PreparedStatement pst = cnx.prepareStatement(req);
                var rs = pst.executeQuery();
                while (rs.next()) {
                    reservationsdesactiviter r = new reservationsdesactiviter();
                    r.setId(rs.getInt("id"));
                    r.setPrix(rs.getInt("prix"));
                    r.setNom(rs.getString("nom"));
                    r.setDate(rs.getDate("date"));
                    r.setType(rs.getString("type"));

                    list.add(r);
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return list;
        }



    @Override
    public void modifier(reservationsdesactiviter reservationsdesactiviter) {
        try {
            String req = "UPDATE reservations_des_activiter SET nom=?, type=?, prix=? ,date=?  WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, reservationsdesactiviter.getNom());
            pst.setString(2, reservationsdesactiviter.getType());
            pst.setInt(3, reservationsdesactiviter.getPrix());
            pst.setDate(4, new Date(reservationsdesactiviter.getDate().getTime()));
            pst.setInt(5, reservationsdesactiviter.getId());
            pst.executeUpdate();
            System.out.println("reservation  " + reservationsdesactiviter.getNom() + " Modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<reservationsdesactiviter> triNom() {

        List<reservationsdesactiviter> list1 = new ArrayList<>();
        List<reservationsdesactiviter> list2 = afficher();

        list1 = list2.stream().sorted(Comparator.comparing(reservationsdesactiviter::getNom)).collect(Collectors.toList());
        return list1;

    }
    public List<reservationsdesactiviter> triType() {

        List<reservationsdesactiviter> list1 = new ArrayList<>();
        List<reservationsdesactiviter> list2 = afficher();

        list1 = list2.stream().sorted(Comparator.comparing(reservationsdesactiviter::getType)).collect(Collectors.toList());
        return list1;

    }
}
