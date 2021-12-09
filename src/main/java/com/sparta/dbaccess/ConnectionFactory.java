package com.sparta.dbaccess;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static Connection connection = null;
    private static String url;
    private static String user;
    private static String password;

    public static Connection getConnection(){
        if(connection == null){
            Properties properties = new Properties();
            try {
                connection = newConnection(properties);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection () throws SQLException {
        if(connection != null) connection.close();
    }

    public Connection getConnectionInstance() {
        Connection connectionInstance = null;
        Properties properties = new Properties();
        try {
            connectionInstance = newConnection(properties);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return connectionInstance;
    }

    public void closeConnectionInstance(Connection connection) throws SQLException {
        if (connection != null) connection.close();
    }

    private static void getProperties(Properties properties) {
        url = properties.getProperty("dbUrl");
        user = properties.getProperty("dbUser");
        password = properties.getProperty("dbPassword");
    }

    private static Connection newConnection(Properties properties) throws IOException, SQLException {
        properties.load(new FileReader("connection.properties"));
        getProperties(properties);
        return DriverManager.getConnection(url, user, password);
    }

}
