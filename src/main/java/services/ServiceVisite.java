package services;
import entities.Visite;
import utils.MySQLConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
/**
 *
 */
public class ServiceVisite implements IServices<Visite> {

    private Connection connection;

    public ServiceVisite() {
        connection = MySQLConnector.getInstance().getConnection();
    }

    @Override
    public void ajouter(Visite visite) throws SQLException {
        String sql = "INSERT INTO visite (ref_b, numero, date_visite ,email ,name) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, visite.getRef_B());
        preparedStatement.setInt(2, visite.getNumero());
        preparedStatement.setDate(3, Date.valueOf(visite.getDate_visite()));
        preparedStatement.setString(4, visite.getEmail());
        preparedStatement.setString(5, visite.getName());
        preparedStatement.executeUpdate();
    }
    public List<Visite> getAllVisites() throws SQLException {
        String sql = "SELECT * FROM visite";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Visite> visites = new ArrayList<>();
        while (rs.next()) {
            Visite visite = new Visite();
            visite.setId(rs.getInt("id"));
            visite.setRef_B(rs.getInt("ref_b"));
            visite.setNumero(rs.getInt("numero"));
            visite.setDate_visite(rs.getDate("date_visite").toLocalDate());
            visite.setEmail(rs.getString("email"));
            visite.setName(rs.getString("name"));
            visites.add(visite);
        }
        return visites;
    }

    @Override
    public void modifier(Visite visite) throws SQLException {
        String sql = "UPDATE visite SET ref_b = ?, numero = ?, date_visite = ?, email = ?, name = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, visite.getRef_B());
        preparedStatement.setInt(2, visite.getNumero());
        preparedStatement.setDate(3, Date.valueOf(visite.getDate_visite()));
        preparedStatement.setString(4, visite.getEmail());
        preparedStatement.setString(5, visite.getName());
        preparedStatement.setInt(6, visite.getId());

        preparedStatement.executeUpdate();
    }
    @Override
    public void supprimer(Visite visite) throws SQLException {
        String sql = "DELETE FROM visite WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, visite.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Visite> afficher() throws SQLException {
        String sql = "SELECT id, ref_b, numero, date_visite, email, name FROM visite ORDER BY id DESC";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Visite> visites = new ArrayList<>();
        while (rs.next()) {
            Visite visite = new Visite();
            visite.setId(rs.getInt("id"));
            visite.setRef_B(rs.getInt("ref_b"));
            visite.setNumero(rs.getInt("numero"));
            visite.setDate_visite(rs.getDate("date_visite").toLocalDate());
            visite.setEmail(rs.getString("email"));
            visite.setName(rs.getString("name"));
            visites.add(visite);
        }
        return visites;
    }

    public List<Visite> rechercher(String rechercheText) throws SQLException {
        String sql = "SELECT * FROM visite WHERE name LIKE ? OR email LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + rechercheText + "%");
        preparedStatement.setString(2, "%" + rechercheText + "%");
        ResultSet rs = preparedStatement.executeQuery();

        List<Visite> visites = new ArrayList<>();
        while (rs.next()) {
            Visite visite = new Visite();
            visite.setId(rs.getInt("id"));
            visite.setRef_B(rs.getInt("ref_b"));
            visite.setNumero(rs.getInt("numero"));
            visite.setDate_visite(rs.getDate("date_visite").toLocalDate());
            visite.setEmail(rs.getString("email"));
            visite.setName(rs.getString("name"));
            visites.add(visite);
        }
        return visites;
    }


}