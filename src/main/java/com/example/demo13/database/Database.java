package com.example.demo13.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:billing_db.sqlite"; // SQLite file

    private static void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("SQLite JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    public static Connection connect() throws SQLException {
        loadDriver(); // Ensure driver is loaded before connecting
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialize() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createCustomersTableSQL());
            System.out.println("Database initialized successfully.");
        } catch (SQLException e) {
            System.err.println("Database initialization failed:");
            e.printStackTrace();
        }
    }

    private static String createCustomersTableSQL() {
        return """
        CREATE TABLE IF NOT EXISTS customers (
            id TEXT PRIMARY KEY,
            name TEXT NOT NULL,
            address TEXT NOT NULL,
            meterNumber TEXT NOT NULL,
            usage REAL DEFAULT 0,
            bill REAL DEFAULT 0
        );
        """;
    }

    public static void main(String[] args) {
        initialize();
    }
}
