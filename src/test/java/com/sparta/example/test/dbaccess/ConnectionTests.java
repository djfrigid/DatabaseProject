package com.sparta.example.test.dbaccess;

import com.sparta.model.dbaccess.ConnectionFactory;
import com.sparta.model.dbaccess.StatementFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionTests {
    Connection connection;

    @BeforeEach
    public void setUp() {
        connection = ConnectionFactory.getConnection();
    }

    //Connections Open & Close
    @Test
    @DisplayName("returns true if connection has been created")
    public void getConnectionTest() throws SQLException {
        assertTrue(connection.isValid(5));
    }

    @Test
    @DisplayName("returns true if connection has been closed")
    public void connectionCloseTest() throws SQLException {
        ConnectionFactory.closeConnection();
        assertTrue(connection.isClosed());
    }

    //Statements
    @Test
    @DisplayName("returns true if getAllEmployees has been closed")
    public void getAllEmployeesCloseTest() throws SQLException, IOException {
        StatementFactory.getAllEmployees();
        StatementFactory.closeStatement();
        assertTrue(StatementFactory.getAllEmployees().isClosed());
    }

    @Test
    @DisplayName("returns true if getCreateTable has been closed")
    public void getCreateTableCloseTest() throws SQLException, IOException {
        StatementFactory.getCreateTable();
        StatementFactory.closeStatement();
        assertTrue(StatementFactory.getCreateTable().isClosed());
    }

    @Test
    @DisplayName("returns true if getDeleteEmployees has been closed")
    public void getDeleteEmployeeCloseTest() throws SQLException, IOException {
        StatementFactory.getDeleteEmployee();
        StatementFactory.closeStatement();
        assertTrue(StatementFactory.getDeleteEmployee().isClosed());
    }

    @Test
    @DisplayName("returns true if getOneEmployees has been closed")
    public void getOneEmployeeCloseTest() throws SQLException, IOException {
        StatementFactory.getOneEmployee();
        StatementFactory.closeStatement();
        assertTrue(StatementFactory.getOneEmployee().isClosed());
    }

    @Test
    @DisplayName("returns true if getDropTable has been closed")
    public void getDropTableCloseTest() throws SQLException, IOException {
        StatementFactory.getDropTable();
        StatementFactory.closeStatement();
        assertTrue(StatementFactory.getDropTable().isClosed());
    }

    @Test
    @DisplayName("returns true if getInsertEmployee has been closed")
    public void getInsertEmployeeCloseTest() throws SQLException, IOException {
        StatementFactory.getInsertEmployee();
        StatementFactory.closeStatement();
        assertTrue(StatementFactory.getInsertEmployee().isClosed());
    }

}
