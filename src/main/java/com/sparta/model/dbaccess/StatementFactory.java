package com.sparta.model.dbaccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatementFactory {

    private static PreparedStatement dropTable = null;
    private static PreparedStatement createTable = null;
    private static PreparedStatement truncateTable = null;
    private static PreparedStatement getAllEmployees = null;
    private static PreparedStatement getOneEmployee = null;
    private static PreparedStatement insertEmployee = null;
    private static PreparedStatement updateEmployee = null;
    private static PreparedStatement deleteEmployee = null;

    public static PreparedStatement getDropTable() throws SQLException {
        if(dropTable == null){
            dropTable = ConnectionFactory.getConnection()
                    .prepareStatement("DROP TABLE IF EXISTS employees;");
        }
        return dropTable;
    }


    public static PreparedStatement getCreateTable() throws SQLException {
        if(createTable == null){
            createTable = ConnectionFactory.getConnection()
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
        return createTable;
    }

    public static PreparedStatement getTruncateStatement() throws SQLException {
        if(truncateTable == null){
            truncateTable = ConnectionFactory.getConnection()
                    .prepareStatement("TRUNCATE TABLE employees");
        }
        return truncateTable;
    }

    public static PreparedStatement getAllEmployees() throws SQLException {
        if(getAllEmployees == null){
            getAllEmployees = ConnectionFactory.getConnection()
                    .prepareStatement("SELECT * FROM employees");
        }
        return getAllEmployees;
    }

    public static PreparedStatement getOneEmployee() throws SQLException, IOException {
        if(getOneEmployee == null){
            getOneEmployee = ConnectionFactory.getConnection()
                    .prepareStatement("SELECT * FROM employees WHERE id = ?");
        }
        return getOneEmployee;
    }

    public static PreparedStatement getInsertEmployee() throws SQLException {
        if(insertEmployee == null){
            insertEmployee = ConnectionFactory.getConnection()
                    .prepareStatement("INSERT INTO employees(id, namePrefix, firstName, initial, " +
                            "lastName, gender, email, dateOfBirth, dateOfJoining, salary)" +
                            " VALUES(?,?,?,?,?,?,?,?,?,?);");
        }
        return insertEmployee;
    }

    public static PreparedStatement getInsertEmployee(Connection connection) throws SQLException, IOException {
        return connection
                .prepareStatement("INSERT INTO employees(id, namePrefix, firstName, initial, " +
                        "lastName, gender, email, dateOfBirth, dateOfJoining, salary)" +
                        " VALUES(?,?,?,?,?,?,?,?,?,?);");
    }

    public static PreparedStatement getUpdateAnEmployee() throws SQLException, IOException {
        if(updateEmployee ==null){
            updateEmployee = ConnectionFactory.getConnection()
                    .prepareStatement("UPDATE employees " +
                            "SET namePrefix = ?, " +
                            "firstName =  ? ,"+
                            "initial = ? ,"+
                            "lastName = ?," +
                            "gender = ? ," +
                            "email = ?," +
                            "dateOfBirth = ?," +
                            "dateOfJoining = ?," +
                            "salary = ? " +
                            "WHERE id = ?; ");
        }
        return updateEmployee;
    }

    public static PreparedStatement getDeleteEmployee() throws SQLException, IOException {
        if(deleteEmployee == null){
            deleteEmployee = ConnectionFactory.getConnection()
                    .prepareStatement("DELETE FROM employees WHERE id = ?;" );
        }
        return deleteEmployee;
    }

    public static void closeStatement() throws SQLException {
        if (dropTable != null) dropTable.close();
        if (createTable != null) createTable.close();
        if (getAllEmployees != null) getAllEmployees.close();
        if (getOneEmployee != null) getOneEmployee.close();
        if (insertEmployee != null) insertEmployee.close();
        if (updateEmployee != null) updateEmployee.close();
        if (deleteEmployee != null) deleteEmployee.close();
    }
}
