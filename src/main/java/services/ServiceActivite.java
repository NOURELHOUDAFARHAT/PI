package services;

import javafx.collections.FXCollections;
import controllers.ActiviteController;
import entities.activite;
import utils.MySQLConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceActivite implements IService<activite> {

    private Connection connection;

    public ServiceActivite(){
        connection= MySQLConnector.getInstance().getConnection();
    }




    @Override
    public void ajouter(activite activite) throws SQLException {
        String sql = "INSERT INTO activite (nom, type, date_heure, prix, description, image,idA_staff) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1, activite.getNom());
        preparedStatement.setString(2, activite.getType());
        preparedStatement.setTimestamp(3, activite.getDate_heure());
        preparedStatement.setDouble(4, activite.getPrix());
        preparedStatement.setString(5, activite.getImage());
        preparedStatement.setString(6, activite.getDescription());
        preparedStatement.setInt(7, activite.getIdA_staff());

        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(activite activite) throws SQLException {
        String sql = "UPDATE activite SET nom = ?, type = ?, date_heure = ?, prix = ?, image = ?, description = ?, idA_staff = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, activite.getNom());
        preparedStatement.setString(2, activite.getType());
        preparedStatement.setTimestamp(3, activite.getDate_heure());
        preparedStatement.setDouble(4, activite.getPrix());
        preparedStatement.setString(5, activite.getImage());
        preparedStatement.setString(6, activite.getDescription());
        preparedStatement.setInt(7, activite.getIdA_staff());
        preparedStatement.setInt(8, activite.getId());
        preparedStatement.executeUpdate();
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql= "delete from activite where id = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<activite> recuperer() throws SQLException {
        List<activite> activites= new ArrayList<>();
        String sql = "select * from activite";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            activite a = new activite();
            a.setId(rs.getInt("id"));
            a.setNom(rs.getString("nom"));
            a.setType(rs.getString("type"));
            a.setDate_heure(rs.getTimestamp("date_heure"));
            a.setPrix(rs.getDouble("prix"));

            a.setDescription(rs.getString("description"));
            a.setImage(rs.getString("image"));
            a.setIdA_staff(rs.getInt("idA_staff"));


            activites.add(a);
        }
        return activites;
    }

    @Override
    public void add(activite activite) {

    }

    @Override
    public void update(activite activite, int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<activite> getAll() {
        return null;
    }

    @Override
    public activite getById(int id) {
        return null;
    }

    public List<activite> recupererParIntervalPrix(double minPrice, double maxPrice) throws SQLException {
        List<activite> activites = new ArrayList<>();
        String sql = "SELECT * FROM activite WHERE prix BETWEEN ? AND ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, minPrice);
        statement.setDouble(2, maxPrice);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            activite a = new activite();
            a.setId(rs.getInt("id"));
            a.setNom(rs.getString("nom"));
            a.setType(rs.getString("type"));
            a.setDate_heure(rs.getTimestamp("date_heure"));
            a.setPrix(rs.getDouble("prix"));
            a.setDescription(rs.getString("description"));
            a.setImage(rs.getString("image"));
            a.setIdA_staff(rs.getInt("idA_staff"));
            activites.add(a);
        }
        return FXCollections.observableArrayList(activites);
    }


}

