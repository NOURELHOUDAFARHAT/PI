package utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {
    String url="jdbc:mysql://localhost:3306/pi";
    String login ="root";
    String pwd ="";

    private Connection connection;
    private static MySQLConnector instance;


    private MySQLConnector() {
        try {
            connection= DriverManager.getConnection(url,login,pwd);
            System.out.println("connexion établie ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static MySQLConnector getInstance(){
        if(instance==null)
            instance=new MySQLConnector();
        return instance ;
    }
    public Connection getConnection(){
        return connection ;
    }
}
