package tn.esprit.services;

import tn.esprit.entites.activite;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceActivite implements IService<activite> {

    private Connection connection;

    public ServiceActivite(){
        connection= MyDataBase.getInstance().getConnection();
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

}

