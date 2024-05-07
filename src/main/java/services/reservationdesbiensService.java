package services;

import entities.reservation_des_biens;
import utils.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class reservationdesbiensService implements IReservationdesBiens<reservationdesbiensService> {
    Connection cnx = MySQLConnector.getInstance().getConnection();
    @Override
    public void ajouter(reservation_des_biens reservation_des_biens) {
        try {
            String req = "INSERT INTO reservation_des_biens (prix, adresse, date_debut, date_fin, nombre_de_membre) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, reservation_des_biens.getPrix());
            pst.setString(2, reservation_des_biens.getAdresse());
            pst.setDate(3, new java.sql.Date(reservation_des_biens.getDate_debut().getTime()));
            pst.setDate(4, new java.sql.Date(reservation_des_biens.getDate_fin().getTime()));
            pst.setInt(5, reservation_des_biens.getNombre_de_membre());
            pst.executeUpdate();
            System.out.println("Reservation ajoutée !");
        } catch (SQLException e) {
            System.out.println("Error adding reservation: " + e.getMessage());
        }
    }






    @Override
    public void supprimer(reservation_des_biens reservation_des_biens) {
        try {
            String req = "DELETE FROM reservation_des_biens WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, reservation_des_biens.getId());
            pst.executeUpdate();
            System.out.println("reservattion des biens  suprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(reservation_des_biens reservation_des_biens) {
        try {
            String req = "UPDATE reservation_des_biens SET prix=?, adresse=?, date_debut=? ,date_fin=? ,nombre_de_membre=?  WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, reservation_des_biens.getPrix());
            pst.setString(2, reservation_des_biens.getAdresse());
            pst.setDate(3, new java.sql.Date(reservation_des_biens.getDate_debut().getTime()));
            pst.setDate(4, new java.sql.Date(reservation_des_biens.getDate_fin().getTime()));
            pst.setInt(5, reservation_des_biens.getNombre_de_membre());
            pst.setInt(6, reservation_des_biens.getId());
            pst.executeUpdate();
            System.out.println("reservation  " + reservation_des_biens.getAdresse() + " Modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
        public List<reservation_des_biens> afficher() {
            List<reservation_des_biens> list = new ArrayList<>();
            try {
                String req = "SELECT * FROM reservation_des_biens";
                PreparedStatement pst = cnx.prepareStatement(req);
                var rs = pst.executeQuery();
                while (rs.next()) {
                    reservation_des_biens r = new reservation_des_biens();
                    r.setId(rs.getInt("id"));
                    r.setPrix(rs.getInt("prix"));
                    r.setAdresse(rs.getString("adresse"));
                    r.setDate_debut(rs.getDate("date_debut"));
                    r.setDate_fin(rs.getDate("date_fin"));
                    r.setNombre_de_membre(rs.getInt("nombre_de_membre"));
                    list.add(r);
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return list;
        }
    }

