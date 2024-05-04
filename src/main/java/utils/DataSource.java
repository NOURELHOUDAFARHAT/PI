package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    String url="jdbc:mysql://localhost:3306/usersymfony";
    String login ="root";
    String pwd ="";

    private Connection connection;
    private static DataSource instance;


    private DataSource() {
        try {
            connection= DriverManager.getConnection(url,login,pwd);
            System.out.println("connexion établie ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static DataSource getInstance(){
        if(instance==null)
            instance=new DataSource();
        return instance ;
    }
    public Connection getConnection(){
        return connection ;
    }
}
