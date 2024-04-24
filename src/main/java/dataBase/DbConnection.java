package dataBase;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.service.service;
import com.example.service.Staff;

import java.util.Date;
public class DbConnection {


    public static Connection Connection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dev","Ahmed","Ahmed");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;

    }
    public static List<Staff> staffs() throws SQLException {
        java.sql.Connection con= Connection();
        Statement st = con.createStatement();
        List<Staff> staffs=new ArrayList<Staff>();
        ResultSet myrs = st.executeQuery("select * from staff");
        while(myrs.next()) {
            int staffId = myrs.getInt("staffId");
            String name = myrs.getString("name");
            String job = myrs.getString("job");
            int score = myrs.getInt("score");
            staffs.add(new Staff(staffId,name,job,score));
        }

        return staffs;


    }
    public static List<service> services() throws SQLException {
        java.sql.Connection con= Connection();
        Statement st = con.createStatement();
        List<service> services=new ArrayList<service>();
        ResultSet myrs1 = st.executeQuery("select * from service");
        while(myrs1.next()) {
            int id = myrs1.getInt("id");
            String name = myrs1.getString("name");
            String adress = myrs1.getString("adress");
            int phone = myrs1.getInt("phone");
            String type = myrs1.getString("type");
            Date rdv = myrs1.getDate("rdv");

            services.add(new service(id,name,adress,phone,type,rdv));
            while(myrs1.next()) {
                System.out.println("id: " +myrs1.getString("id")+" ,libre  "+ myrs1.getString("libre") );
            }
        }

        return services;


    }

}
