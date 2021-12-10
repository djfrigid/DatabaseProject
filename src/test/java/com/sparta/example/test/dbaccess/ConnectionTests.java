package com.sparta.example.test.dbaccess;

import com.sparta.dbaccess.ConnectionFactory;
import com.sparta.dbaccess.StatementFactory;
import com.sparta.util.DateFormatter;
import com.sparta.validate.EmployeeValidate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionTests {
    Connection connection;

    @BeforeEach
    public void setUp() {
        connection = ConnectionFactory.getConnection();
    }

    @Test
    public void getConnectionTest() throws SQLException {
        StatementFactory.getAllEmployees();
        assertTrue(StatementFactory.getAllEmployees().isPoolable());
    }

    @Test
    public void connectionCloseTest() throws SQLException {
        StatementFactory.getAllEmployees();
        ConnectionFactory.closeConnection();
        assertTrue(StatementFactory.getAllEmployees().isClosed());
    }

//    @Test
//    public void statementCloseTest() throws SQLException, IOException {
//        StatementFactory.getAllEmployees();
//        StatementFactory.getCreateTable();
//        StatementFactory.getDeleteEmployee();
//        StatementFactory.getOneEmployee();
//        StatementFactory.getDropTable();
//        StatementFactory.getInsertEmployee();
//        StatementFactory.closeStatement();
//    }

}
