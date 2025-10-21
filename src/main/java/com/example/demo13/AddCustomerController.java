package com.example.demo13;

import com.example.demo13.controller.CustomerManager;
import com.example.demo13.model.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCustomerController {
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField meterField;
    @FXML private TextField usageField;

    private CustomerManager manager;
    private Runnable onCustomerAdded; // Callback to refresh the main table

    public void setManager(CustomerManager manager) {
        this.manager = manager;
    }

    public void setOnCustomerAdded(Runnable callback) {
        this.onCustomerAdded = callback;
    }

    @FXML
    private void handleSave() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String meter = meterField.getText().trim();
            String usageText = usageField.getText().trim();

            // Basic validation
            if (id.isEmpty() || name.isEmpty() || address.isEmpty() || meter.isEmpty()) {
                showError("Missing Fields", "Please fill in all required fields.");
                return;
            }

            double usage;
            try {
                usage = usageText.isEmpty() ? 0.0 : Double.parseDouble(usageText);
            } catch (NumberFormatException e) {
                showError("Invalid Input", "Usage must be a valid number.");
                return;
            }

            // Create customer object
            Customer customer = new Customer(id, name, address, meter, usage);
            manager.addCustomer(customer);

            // Notify dashboard to refresh table
            if (onCustomerAdded != null) {
                onCustomerAdded.run();
            }

            showInfo("Success", "Customer added successfully!");
            closeWindow();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "An unexpected error occurred:\n" + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
