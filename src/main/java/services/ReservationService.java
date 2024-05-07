package services;

import entities.ReservationVoitureDto;
import entities.reservation_des_voitures;
import utils.MySQLConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IResarvationVoiture<reservation_des_voitures> {

    Connection cnx = MySQLConnector.getInstance().getConnection();

    @Override
    public void ajouter(reservation_des_voitures reservation_des_voitures) {
        try {
            if (cnx != null) {
                String req = "INSERT INTO reservation_des_voitures (date_debut, date_fin, prix, voiture_id) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = cnx.prepareStatement(req);
                pst.setDate(1, new Date(reservation_des_voitures.getDate_debut().getTime()));
                pst.setDate(2, new Date(reservation_des_voitures.getDate_fin().getTime()));
                pst.setDouble(3, reservation_des_voitures.getPrix());
                pst.setInt(4, reservation_des_voitures.getVoiture_id());
                pst.executeUpdate();
                System.out.println("Reservation ajoutée !");
            }
        } catch (SQLException e) {
            System.out.println("Error adding reservation: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(reservation_des_voitures reservationV) {
        try {
            String req = "DELETE FROM reservation_des_voitures WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, reservationV.getId());
            pst.executeUpdate();
            System.out.println("Reservation supprimée !");
        } catch (SQLException ex) {
            System.out.println("Error deleting reservation: " + ex.getMessage());
        }
    }

    @Override
    public void modifier(reservation_des_voitures reservationV) {
        try {
            String req = "UPDATE reservation_des_voitures SET date_debut=?, date_fin=?, prix=?, voiture_id=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setDate(1, new Date(reservationV.getDate_debut().getTime()));
            pst.setDate(2, new Date(reservationV.getDate_fin().getTime()));
            pst.setDouble(3, reservationV.getPrix());
            pst.setInt(4, reservationV.getVoiture_id());
            pst.setInt(5, reservationV.getId());
            pst.executeUpdate();
            System.out.println("Reservation modifiée !");
        } catch (SQLException e) {
            System.out.println("Error updating reservation: " + e.getMessage());
        }
    }

    @Override
    public List<reservation_des_voitures> afficher() {
        return null;
    }


    public List<ReservationVoitureDto> affichage() {
        List<ReservationVoitureDto> list = new ArrayList<>();
        try {
            if (cnx != null) {
                Connection cnx = MySQLConnector.getInstance().getConnection();

                String req = "SELECT r.id, r.date_debut, r.date_fin, r.prix, r.voiture_id " +
                        "FROM reservation_des_voitures r " +
                        "JOIN voiture vt ON r.voiture_id = vt.id"; // Assuming voiture_id in r table corresponds to id in vt table

                PreparedStatement pst = cnx.prepareStatement(req);

                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    ReservationVoitureDto reservationVoitureDto = new ReservationVoitureDto(
                            rs.getInt("id"),
                            rs.getDate("date_debut"),
                            rs.getDate("date_fin"),
                            rs.getDouble("prix"),
                            rs.getInt("voiture_id")

                    );
                    list.add(reservationVoitureDto);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }



}