package edu.ijse.ayurveda_skin_care.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnection{
    private static DBConnection dBConnection;

    private Connection connection;

    private DBConnection()throws SQLException,ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ayurveda_skin_care", "root", "pabo123");
    }

    public static DBConnection getInstance() throws SQLException,ClassNotFoundException{
        if (dBConnection==null){
            dBConnection = new DBConnection();
        }
        return dBConnection;
    }

    public Connection getConnection(){
        return connection;
    }

}