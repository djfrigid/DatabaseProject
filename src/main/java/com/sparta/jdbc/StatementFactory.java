package com.sparta.jdbc;

import java.sql.PreparedStatement;

public class StatementFactory {
    private static PreparedStatement selectAllStatement = null;
    private static PreparedStatement selectStatement = null;
    private static PreparedStatement insertStatement = null;
    private static PreparedStatement deleteStatement = null;

    public static PreparedStatement getAllEmployees() {
        return selectAllStatement;
    }

    public static PreparedStatement getEmployee() {
        return selectStatement;
    }

    public static PreparedStatement getInsertStatement() {
        return insertStatement;
    }

    public static PreparedStatement getDeleteStatement() {
        return deleteStatement;
    }
}
