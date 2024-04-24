package tn.esprit.services;

import tn.esprit.entites.Staff;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceStaff implements IService<Staff> {

    private Connection connection;

    public ServiceStaff(){
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Staff staff) throws SQLException {
        String sql = "INSERT INTO staff (nom, type, num_tel) VALUES ( ?, ?, ?)";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1, staff.getNom());
        preparedStatement.setString(2, staff.getType());
        preparedStatement.setString(3, staff.getNum_tel());

        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Staff staff) throws SQLException {
        String sql = "UPDATE staff SET nom = ?, type = ?, num_tel = ? WHERE idA = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1, staff.getNom());
        preparedStatement.setString(2, staff.getType());
        preparedStatement.setString(3, staff.getNum_tel());
        preparedStatement.setInt(4, staff.getIdA());

        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql= "DELETE FROM staff WHERE idA = ?";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Staff> recuperer() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            Staff staff = new Staff();

            staff.setNom(rs.getString("nom"));
            staff.setType(rs.getString("type"));
            staff.setNum_tel(rs.getString("num_tel"));
            staffList.add(staff);
        }
        return staffList;
    }
}
