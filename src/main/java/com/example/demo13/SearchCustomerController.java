package com.example.demo13;

import com.example.demo13.controller.CustomerManager;
import com.example.demo13.model.Customer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class SearchCustomerController {

    @FXML private TextField searchField;
    @FXML private TableView<Customer> resultsTable;
    @FXML private TableColumn<Customer, String> idColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> meterColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, Double> usageColumn;
    @FXML private TableColumn<Customer, Double> billColumn;

    private CustomerManager manager;
    private TableView<Customer> customerTable;

    public void setManager(CustomerManager manager) {
        this.manager = manager;
    }

    public void setCustomerTable(TableView<Customer> table) {
        this.customerTable = table;
    }

    @FXML
    public void initialize() {
        // Bind columns to Customer properties
        idColumn.setCellValueFactory(cell -> cell.getValue().idProperty());
        nameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        meterColumn.setCellValueFactory(cell -> cell.getValue().meterNumberProperty());
        addressColumn.setCellValueFactory(cell -> cell.getValue().addressProperty());
        usageColumn.setCellValueFactory(cell -> cell.getValue().usageProperty().asObject());
        billColumn.setCellValueFactory(cell -> cell.getValue().billProperty().asObject());
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            showAlert("Input Required", "Please enter a search term.");
            return;
        }

        if (manager == null || customerTable == null) {
            showAlert("Error", "Search system not initialized.");
            return;
        }

        // Search by ID and Name
        Customer byId = manager.searchById(query);
        List<Customer> byName = manager.searchByName(query);

        if (byId != null) byName.add(0, byId); // ensure ID match comes first

        if (byName.isEmpty()) {
            showAlert("No Results", "No customer found for: " + query);
            resultsTable.setItems(FXCollections.observableArrayList());
        } else {
            resultsTable.setItems(FXCollections.observableArrayList(byName));
        }
    }

    @FXML
    private void handleSelect() {
        Customer selected = resultsTable.getSelectionModel().getSelectedItem();
        if (selected != null && customerTable != null) {
            customerTable.getSelectionModel().select(selected);
            customerTable.scrollTo(selected);
            closeWindow();
        } else {
            showAlert("No Selection", "Please select a customer from the table.");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) searchField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
