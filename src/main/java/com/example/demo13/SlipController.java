package com.example.demo13;

import com.example.demo13.model.Customer;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SlipController {

    @FXML private Label customerIdLabel;
    @FXML private Label nameLabel;
    @FXML private Label meterLabel;
    @FXML private Label usageLabel;
    @FXML private Label billLabel;
    @FXML private VBox slipRoot;

    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
        updateLabels();
    }

    private void updateLabels() {
        if (customer == null) {
            showError("No customer data provided.");
            return;
        }

        customerIdLabel.setText("Customer ID: " + customer.getId());
        nameLabel.setText("Name: " + customer.getName());
        meterLabel.setText("Meter No: " + customer.getMeter());
        usageLabel.setText("Usage (kWh): " + String.format("%.2f", customer.getUsage()));
        billLabel.setText("Bill Amount: M" + String.format("%.2f", customer.getBill()));
    }

    @FXML
    private void handlePrint() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            showError("No printer available.");
            return;
        }

        if (job.showPrintDialog(slipRoot.getScene().getWindow())) {
            boolean success = job.printPage(slipRoot);
            if (success) {
                job.endJob();
                closeWindow();
            } else {
                showError("Printing failed.");
            }
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) slipRoot.getScene().getWindow();
        stage.close();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Print Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
