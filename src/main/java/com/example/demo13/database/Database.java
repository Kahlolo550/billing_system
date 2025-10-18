package com.example.demo13.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/school_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(" MySQL JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println(" MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }


    public static Connection connect() throws SQLException {
        loadDriver(); // Ensure driver is loaded before connecting
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }


    public static void initialize() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createCustomersTableSQL());
            System.out.println(" Database initialized successfully.");
        } catch (SQLException e) {
            System.err.println(" Database initialization failed:");
            e.printStackTrace();
        }
    }


    private static String createCustomersTableSQL() {
        return """
        CREATE TABLE IF NOT EXISTS customers (
            id VARCHAR(50) PRIMARY KEY,
            name VARCHAR(100) NOT NULL,
            address VARCHAR(255) NOT NULL,
            meterNumber VARCHAR(50) NOT NULL,
            `usage` DOUBLE DEFAULT 0,
            bill DOUBLE DEFAULT 0
        );
    """;
    }


    /**
     * Main method for testing connection and initialization.
     */
    public static void main(String[] args) {
        initialize();
    }
}
