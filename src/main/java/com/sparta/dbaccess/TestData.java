package com.sparta.dbaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestData {
    public static void main(String[] args) {

        try {
            PreparedStatement stmt = StatementFactory.getCreateTable();
            int rowAffected = stmt.executeUpdate();
            System.out.println("Row affected: " + rowAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
