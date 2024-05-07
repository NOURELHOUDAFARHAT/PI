package services.ahmed;


import entities.ahmed.Staff;
import utils.MySQLConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffSERV implements SERVInterface<Staff> {
    Connection connection = MySQLConnector.getInstance().getConnection();

    @Override
    public void create(Staff staff) throws SQLException {
        String sql = "INSERT INTO stafff (numStaff, nameStaff, phoneStaff, jobStaff,status, scoreStaff,service) VALUES (?, ?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, staff.getNumStaff());
        preparedStatement.setString(2, staff.getNameStaff());
        preparedStatement.setInt(3, staff.getPhoneStaff());
        preparedStatement.setString(4, staff.getJobStaff());
        preparedStatement.setBoolean(5, staff.isStatus());
        preparedStatement.setInt(6, staff.getScoreStaff());
        preparedStatement.setInt(7, staff.getService());

        preparedStatement.executeUpdate();
    }

    @Override
    public void modify(Staff staff) throws SQLException {
        String sql = "UPDATE stafff SET nameStaff = ?, phoneStaff = ?, jobStaff = ?,`status` = ?, `scoreStaff` = ? , `service` = ? WHERE numStaff = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, staff.getNameStaff());
        preparedStatement.setInt(2, staff.getPhoneStaff());
        preparedStatement.setString(3, staff.getJobStaff());
        preparedStatement.setBoolean(4,staff.isStatus());
        preparedStatement.setInt(5, staff.getScoreStaff());
        preparedStatement.setInt(6, staff.getService());

        preparedStatement.setInt(7, staff.getNumStaff());

        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int numStaff ) throws SQLException {
        String sql = "DELETE FROM stafff WHERE numStaff = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,numStaff);
        preparedStatement.executeUpdate();
    }

    @Override
    public  List<Staff> show() throws SQLException {
        String sql = "SELECT * FROM stafff";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Staff> staffs = new ArrayList<>();
        while (rs.next()) {
            Staff staff = new Staff();
            staff.setNumStaff(rs.getInt("numStaff"));
            staff.setNameStaff(rs.getString("nameStaff"));
            staff.setPhoneStaff(rs.getInt("phoneStaff"));
            staff.setJobStaff(rs.getString("jobStaff"));
            staff.setScoreStaff(rs.getInt("scoreStaff"));
            staff.setStatus(rs.getBoolean("status"));
            staff.setService(rs.getInt("service"));

            staffs.add(staff);
        }
        return staffs;
    }



    public List<Staff> search(String searchText) throws SQLException {
        String sql = "SELECT * FROM stafff WHERE nameStaff LIKE ? OR phoneStaff LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + searchText + "%");
        preparedStatement.setString(2, "%" + searchText + "%");

        ResultSet rs = preparedStatement.executeQuery();

        List<Staff> staffs = new ArrayList<>();
        while (rs.next()) {
            Staff staff = new Staff();
            staff.setNumStaff(rs.getInt("numStaff"));
            staff.setNameStaff(rs.getString("nameStaff"));
            staff.setPhoneStaff(rs.getInt("phoneStaff"));
            staff.setJobStaff(rs.getString("jobStaff"));
            staff.setScoreStaff(rs.getInt("scoreStaff"));
            staff.setStatus(rs.getBoolean("status"));
            staff.setStatus(rs.getBoolean("service"));
            staffs.add(staff);
        }
        return staffs;
    }


}
