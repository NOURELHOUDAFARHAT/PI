package services;



import entities.Voiture;
import utils.MySQLConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoitureService implements IVoiture<Voiture> {
    Connection cnx = MySQLConnector.getInstance().getConnection();

    @Override
    public void ajouter(Voiture voiture) {
        try {
            String req = "INSERT INTO voiture (num_immatriculation, modele, couleur, disponible, prix_per_day) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(req);

            if (voiture.getNum_immatriculation() == null || voiture.getNum_immatriculation().isEmpty()) {
                pst.setNull(1, Types.VARCHAR);
            } else {
                pst.setString(1, voiture.getNum_immatriculation());
            }

            pst.setString(2, voiture.getModele());
            pst.setString(3, voiture.getCouleur());
            pst.setInt(4, voiture.getDisponible());
            pst.setDouble(5, voiture.getPrix_per_day());

            pst.executeUpdate();
            System.out.println("Voiture ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimer(Voiture voiture) {
        try{
            String req = "DELETE FROM voiture WHERE id =?";
            PreparedStatement pst =  cnx.prepareStatement(req);
            pst.setInt(1, voiture.getId());
            pst.executeUpdate();
            System.out.println("voiture supprimée !");

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public Voiture getById(int id) {
        Voiture voiture = null;
        try {
            String req = "SELECT * FROM voiture WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                voiture = new Voiture();
                voiture.setId(rs.getInt("id"));
                voiture.setNum_immatriculation(rs.getString("num_immatriculation"));
                voiture.setModele(rs.getString("modele"));
                voiture.setCouleur(rs.getString("couleur"));
                voiture.setDisponible(rs.getInt("disponible"));
                voiture.setPrix_per_day(rs.getDouble("prix_per_day"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return voiture;
    }

    @Override
    public void modifier(Voiture voiture) {
        try {
            String req = "UPDATE voiture SET num_immatriculation = ?, modele = ?, couleur = ?, disponible = ?, prix_per_day = ? WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(req);

            pst.setString(1, voiture.getNum_immatriculation());
            pst.setString(2, voiture.getModele());
            pst.setString(3, voiture.getCouleur());
            pst.setInt(4, voiture.getDisponible());
            pst.setDouble(5, voiture.getPrix_per_day());
            pst.setInt(6, voiture.getId());  // Set the value for the id parameter

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Voiture mise à jour !");
            } else {
                System.out.println("Aucune voiture correspondante trouvée pour la mise à jour.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Voiture> afficher() {
        List<Voiture> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM voiture";
            PreparedStatement pst = cnx.prepareStatement(req);
            var rs = pst.executeQuery();
            while (rs.next()) {
                Voiture r = new Voiture();
                r.setId(rs.getInt("id"));
                r.setNum_immatriculation(rs.getString("Num_immatriculation"));
                r.setCouleur(rs.getString("couleur"));
                r.setModele(rs.getString("modele"));
                r.setDisponible(rs.getInt("disponible"));
                r.setPrix_per_day(rs.getDouble("prix_per_day"));
                list.add(r);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    public List<Voiture> search(String t) {
        List<Voiture> filteredList = new ArrayList<>();
        List<Voiture> allreclamation = afficher();

        for (Voiture Voiture : allreclamation) {
            if (Voiture.getModele().startsWith(t) ||
                    Voiture.getNum_immatriculation().startsWith(t)) {
                filteredList.add(Voiture);
            }
        }

        return filteredList;
    }


}
