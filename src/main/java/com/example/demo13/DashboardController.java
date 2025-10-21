package com.example.demo13;

import com.example.demo13.controller.CustomerManager;
import com.example.demo13.model.Customer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class DashboardController {
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> idColumn;
    @FXML private TableColumn<Customer, String> nameColumn;
    @FXML private TableColumn<Customer, String> addressColumn;
    @FXML private TableColumn<Customer, String> meterColumn;
    @FXML private TableColumn<Customer, Double> usageColumn;
    @FXML private TableColumn<Customer, Double> billColumn;

    private final CustomerManager manager = new CustomerManager();

    @FXML
    public void initialize() {
        // Bind columns to properties
        idColumn.setCellValueFactory(cell -> cell.getValue().idProperty());
        nameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        addressColumn.setCellValueFactory(cell -> cell.getValue().addressProperty());
        meterColumn.setCellValueFactory(cell -> cell.getValue().meterNumberProperty()); // UPDATED
        usageColumn.setCellValueFactory(cell -> cell.getValue().usageProperty().asObject());
        billColumn.setCellValueFactory(cell -> cell.getValue().billProperty().asObject());

        // Load data
        refreshCustomerTable();
    }

    private void refreshCustomerTable() {
        manager.loadCustomersFromDB();
        customerTable.setItems(FXCollections.observableArrayList(manager.getCustomers()));
    }

    @FXML
    private void handleAdd() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddCustomerForm.fxml"));
            Stage formStage = new Stage();
            formStage.setScene(new Scene(loader.load()));
            formStage.setTitle("Add Customer");

            AddCustomerController controller = loader.getController();
            controller.setManager(manager);
            controller.setOnCustomerAdded(this::refreshCustomerTable);

            formStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to open Add Customer form.");
        }
    }

    @FXML
    private void handleDelete() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a customer to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete customer: " + selected.getName() + "?");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                manager.deleteCustomer(selected);
                refreshCustomerTable();
                showAlert("Deleted", "Customer deleted successfully.");
            }
        });
    }

    @FXML
    private void handleLoadCustomers() {
        refreshCustomerTable();
    }

    @FXML
    private void handleSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SearchCustomerForm.fxml"));
            Stage searchStage = new Stage();
            searchStage.setScene(new Scene(loader.load()));
            searchStage.setTitle("Search Customer");

            SearchCustomerController controller = loader.getController();
            controller.setManager(manager);
            controller.setCustomerTable(customerTable);

            searchStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to open Search form.");
        }
    }

    @FXML
    private void handlePrintSlip() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("No Selection", "Please select a customer to print the slip.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Slip.fxml"));
            Stage slipStage = new Stage();
            slipStage.setScene(new Scene(loader.load()));
            slipStage.setTitle("Customer Slip");

            SlipController controller = loader.getController();
            controller.setCustomer(selected);

            slipStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to load Slip view.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
