package com.sparta.model.dbaccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MTStatementFactory {

    public static PreparedStatement getDropTable(Connection connection) throws SQLException {
            return connection
                    .prepareStatement("DROP TABLE IF EXISTS employees;");
    }


    public static PreparedStatement getCreateTable(Connection connection) throws SQLException {
            return connection
                    .prepareStatement("CREATE TABLE employees" +
                            "    (id INT(6) PRIMARY KEY ," +
                            "    namePrefix VARCHAR(5)," +
                            "    firstName VARCHAR(50)," +
                            "    initial char," +
                            "    lastName VARCHAR(50)," +
                            "    gender char ," +
                            "    email VARCHAR(50)," +
                            "    dateOfBirth DATE," +
                            "    dateOfJoining DATE," +
                            "    salary INT)" +
                            ";");
    }

    public static PreparedStatement getAllEmployees(Connection connection) throws SQLException {
            return ConnectionFactory.getConnection()
                    .prepareStatement("SELECT * FROM employees");
    }

    public static PreparedStatement getOneEmployee() throws SQLException, IOException {
            return ConnectionFactory.getConnection()
                    .prepareStatement("SELECT * FROM employees WHERE id = ?");
    }

    public static PreparedStatement getInsertEmployee(Connection connection) throws SQLException, IOException {
            return connection
                    .prepareStatement("INSERT INTO employees(id, namePrefix, firstName, initial, " +
                            "lastName, gender, email, dateOfBirth, dateOfJoining, salary)" +
                            " VALUES(?,?,?,?,?,?,?,?,?,?);");
    }

    public static PreparedStatement getUpdateAnEmployee() throws SQLException, IOException {
            return ConnectionFactory.getConnection()
                    .prepareStatement("UPDATE employees " +
                            "SET namePrefix = ? " +
                            "SET firstName =  ? "+
                            "SET initial = ? "+
                            "SET  lastName = ?" +
                            "SET gender = ? " +
                            "SET email = ?" +
                            "SET dateOfBirth = ?" +
                            "SET dateOfJoining = ?" +
                            "SET salary = ? " +
                            "WHERE id = ?; ");
    }

    public static PreparedStatement getDeleteEmployee() throws SQLException, IOException {
        return ConnectionFactory.getConnection()
                    .prepareStatement("DELETE FROM employees WHERE id = 137583;" );
    }

}
