package services;

import entities.Bien;

import utils.MySQLConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceBien implements IServices<Bien> {

    private Connection connection;

    public ServiceBien() {
        connection = MySQLConnector.getInstance().getConnection();
    }

    @Override
    public void ajouter(Bien bien) throws SQLException {
        String sql = "INSERT INTO bien (ref_b, name, adresse, nbr_chambre, prix, type, image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, bien.getRefB());
        preparedStatement.setString(2, bien.getName());
        preparedStatement.setString(3, bien.getAdresse());
        preparedStatement.setInt(4, bien.getNbrChambre());
        preparedStatement.setInt(5, bien.getPrix());
        preparedStatement.setString(6, bien.getType());
        preparedStatement.setString(7, bien.getImage());
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Bien bien) throws SQLException {
        String sql = "UPDATE bien SET name = ?, adresse = ?, nbr_chambre = ?, prix = ?, type = ?, image = ? WHERE ref_b = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, bien.getName());
        preparedStatement.setString(2, bien.getAdresse());
        preparedStatement.setInt(3, bien.getNbrChambre());
        preparedStatement.setInt(4, bien.getPrix());
        preparedStatement.setString(5, bien.getType());
        preparedStatement.setString(6, bien.getImage());
        preparedStatement.setInt(7, bien.getRefB());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(Bien bien) throws SQLException {
        String sql = "DELETE FROM bien WHERE ref_b = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, bien.getRefB());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Bien> afficher() throws SQLException {
        String sql = "SELECT * FROM bien";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Bien> biens = new ArrayList<>();
        while (rs.next()) {
            Bien bien = new Bien();
            bien.setRefB(rs.getInt("ref_b"));
            bien.setName(rs.getString("name"));
            bien.setAdresse(rs.getString("adresse"));
            bien.setNbrChambre(rs.getInt("nbr_chambre"));
            bien.setPrix(rs.getInt("prix"));
            bien.setType(rs.getString("type"));
            bien.setImage(rs.getString("image"));
            biens.add(bien);
        }
        return biens;
    }

    public List<Bien> rechercher(String rechercheText) throws SQLException {
        String sql = "SELECT * FROM bien WHERE name LIKE ? OR adresse LIKE ? OR nbr_chambre = ? OR prix LIKE ? OR type LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + rechercheText + "%");
        preparedStatement.setString(2, "%" + rechercheText + "%");
        preparedStatement.setString(3, rechercheText);
        preparedStatement.setString(4, "%" + rechercheText + "%");
        preparedStatement.setString(5, "%" + rechercheText + "%");
        ResultSet rs = preparedStatement.executeQuery();

        List<Bien> biens = new ArrayList<>();
        while (rs.next()) {
            Bien bien = new Bien();
            bien.setRefB(rs.getInt("ref_b"));
            bien.setName(rs.getString("name"));
            bien.setAdresse(rs.getString("adresse"));
            bien.setNbrChambre(rs.getInt("nbr_chambre"));
            bien.setPrix(rs.getInt("prix"));
            bien.setType(rs.getString("type"));
            bien.setImage(rs.getString("image"));
            biens.add(bien);
        }
        return biens;
    }
    public List<Bien> rechercherAll() throws SQLException {
        List<Bien> biens = new ArrayList<>();

        String sql = "SELECT * FROM bien";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Bien Bien = new Bien(
                        rs.getInt("ref_b"),
                        rs.getString("name"),
                        rs.getString("adresse"),
                        rs.getInt("nbr_chambre"),
                        rs.getInt("prix"),
                        rs.getString("type"),
                        rs.getString("image")

                );
                Bien.setRefB(rs.getInt("ref_b"));
                biens.add(Bien);
            }
        }

        return biens;
    }


}
