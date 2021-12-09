package com.sparta.dbaccess;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static Connection connection = null;

    public static Connection getConnection(){
        if(connection == null){
            Properties properties = new Properties();
            try {
                properties.load(new FileReader("connection.properties"));
                String url = properties.getProperty("dbUrl");
                String user = properties.getProperty("dbUser");
                String password = properties.getProperty("dbPassword");
                connection = DriverManager.getConnection(url, user, password);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection () throws SQLException {
        if(connection != null) connection.close();
    }

    public static Connection getConnectionInstance(){
        Properties properties = new Properties();
        Connection connectionInst = null;
        try {
            properties.load(new FileReader("connection.properties"));
            String url = properties.getProperty("dbUrl");
            String user = properties.getProperty("dbUser");
            String password = properties.getProperty("dbPassword");
            connectionInst = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return connectionInst;
    }



}
