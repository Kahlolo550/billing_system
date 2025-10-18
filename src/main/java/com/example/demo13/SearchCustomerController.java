package com.example.demo13;

import com.example.demo13.controller.CustomerManager;
import com.example.demo13.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class SearchCustomerController {
    @FXML private TextField searchField;
    @FXML private Label resultLabel;

    private CustomerManager manager;
    private TableView<Customer> customerTable;

    public void setManager(CustomerManager manager) {
        this.manager = manager;
    }

    public void setCustomerTable(TableView<Customer> table) {
        this.customerTable = table;
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().trim();

        if (query.isEmpty()) {
            resultLabel.setText("Please enter a search term.");
            return;
        }

        if (manager == null || customerTable == null) {
            resultLabel.setText("Search system not initialized.");
            System.err.println("❌ Manager or TableView not injected.");
            return;
        }

        // Try search by ID
        Customer matchById = manager.searchById(query);
        if (matchById != null) {
            selectAndScrollTo(matchById);
            return;
        }

        // Try search by name
        List<Customer> matchesByName = manager.searchByName(query);
        if (!matchesByName.isEmpty()) {
            selectAndScrollTo(matchesByName.get(0));
            return;
        }

        resultLabel.setText("No customer found.");
    }

    private void selectAndScrollTo(Customer customer) {
        customerTable.getSelectionModel().select(customer);
        customerTable.scrollTo(customer);
        closeWindow();
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) searchField.getScene().getWindow();
        stage.close();
    }
}
