package com.example.demo13.controller;

import com.example.demo13.database.Database;
import com.example.demo13.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerManager {
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    public void addCustomer(Customer c) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO customers (id, name, address, meterNumber, usage, bill) VALUES (?, ?, ?, ?, ?, ?)"
             )) {
            stmt.setString(1, c.getId());
            stmt.setString(2, c.getName());
            stmt.setString(3, c.getAddress());
            stmt.setString(4, c.getMeterNumber());
            stmt.setDouble(5, c.getUsage());
            stmt.setDouble(6, c.getBill());
            stmt.executeUpdate();
            System.out.println("Customer inserted: " + c.getId());
        } catch (SQLException e) {
            System.err.println("Failed to insert customer:");
            e.printStackTrace();
        }
        loadCustomersFromDB();
    }

    public void deleteCustomer(Customer c) {
        customers.remove(c);
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM customers WHERE id = ?")) {
            stmt.setString(1, c.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer searchById(String id) {
        for (Customer c : customers) {
            if (c.getId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    public List<Customer> searchByName(String name) {
        return customers.stream()
                .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public void loadCustomersFromDB() {
        customers.clear();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("meterNumber"), // updated for SQLite
                        rs.getString("address"),
                        rs.getDouble("usage")
                );
                c.setBill(rs.getDouble("bill"));
                customers.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----------------- New Method: Update Customer -----------------
    public void updateCustomer(Customer c) {
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE customers SET name = ?, address = ?, meterNumber = ?, usage = ?, bill = ? WHERE id = ?"
             )) {
            stmt.setString(1, c.getName());
            stmt.setString(2, c.getAddress());
            stmt.setString(3, c.getMeterNumber());
            stmt.setDouble(4, c.getUsage());
            stmt.setDouble(5, c.getBill());
            stmt.setString(6, c.getId());
            stmt.executeUpdate();
            System.out.println("Customer updated: " + c.getId());
        } catch (SQLException e) {
            System.err.println("Failed to update customer:");
            e.printStackTrace();
        }
        loadCustomersFromDB();
    }
}
