package services.ahmed;



import entities.ahmed.Service;
import utils.MySQLConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSERV implements SERVInterface<Service> {
    Connection connection = MySQLConnector.getInstance().getConnection();


    @Override
    public void create(Service svr) throws SQLException {
        String sql = "INSERT INTO service (name, adress, phone, type, rdv) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, svr.getName());
        preparedStatement.setString(2, svr.getAdress());
        preparedStatement.setInt(3, svr.getPhone());
        preparedStatement.setString(4, svr.getType());
        preparedStatement.setDate(5, new Date(svr.getRdv().getTime()));
        preparedStatement.executeUpdate();
    }

    @Override
    public void modify(Service svr) throws SQLException {
        String sql = "UPDATE service SET name = ?, adress = ?, phone = ?, type = ?, rdv=? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, svr.getName());
        preparedStatement.setString(2, svr.getAdress());
        preparedStatement.setInt(3, svr.getPhone());
        preparedStatement.setString(4, svr.getType());
        preparedStatement.setDate(5, new Date(svr.getRdv().getTime()));

        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id ) throws SQLException {
        String sql = "DELETE FROM service WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Service> show() throws SQLException {
        String sql = "SELECT * FROM service";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Service> services = new ArrayList<>();
        while (rs.next()) {
            Service svr = new Service();
            svr.setId(rs.getInt("id"));
            svr.setName(rs.getString("name"));
            svr.setAdress(rs.getString("adress"));
            svr.setPhone(rs.getInt("phone"));
            svr.setType(rs.getString("type"));
            svr.setRdv(rs.getDate("rdv"));
            services.add(svr);
        }
        return services;
    }

    public List<Service> search(String searchText) throws SQLException {
        String sql = "SELECT * FROM service WHERE name LIKE ? OR phone LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + searchText + "%");
        preparedStatement.setString(2, "%" + searchText + "%");

        ResultSet rs = preparedStatement.executeQuery();

        List<Service> services = new ArrayList<>();
        while (rs.next()) {
            Service svr = new Service();
            svr.setName(rs.getString("name"));
            svr.setAdress(rs.getString("adress"));
            svr.setPhone(rs.getInt("phone"));
            svr.setType(rs.getString("type"));
            svr.setRdv(rs.getDate("rdv"));
            services.add(svr);
        }
        return services;
    }
}
