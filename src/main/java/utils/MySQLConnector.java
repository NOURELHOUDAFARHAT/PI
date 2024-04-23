package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector {

    private final String URL ="jdbc:mysql://localhost:3306/pi";
    private  final String USER="root";
    private  final String PWD="";

    private Connection connection;

    private static MySQLConnector instance ;

    private MySQLConnector (){
        try {
            connection = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("coonected to DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }}

    public  static MySQLConnector getInstance(){
        if (instance==null){
            instance = new MySQLConnector();
        }return instance;


    }

    public Connection getConnection() {
        return connection;
    }
}