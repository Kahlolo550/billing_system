package com.example.demo13.lec_billing;

import com.example.demo13.model.Customer;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SlipPrinter {

    public static void printCustomerSlip(Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(SlipPrinter.class.getResource("Slip.fxml"));
            Parent root = loader.load();

            // Set values
            javafx.scene.control.Label idLabel = (javafx.scene.control.Label) root.lookup("#customerIdLabel");
            javafx.scene.control.Label nameLabel = (javafx.scene.control.Label) root.lookup("#nameLabel");
            javafx.scene.control.Label meterLabel = (javafx.scene.control.Label) root.lookup("#meterLabel");
            javafx.scene.control.Label usageLabel = (javafx.scene.control.Label) root.lookup("#usageLabel");
            javafx.scene.control.Label billLabel = (javafx.scene.control.Label) root.lookup("#billLabel");

            idLabel.setText("Customer ID: " + customer.getId());
            nameLabel.setText("Name: " + customer.getName());
            meterLabel.setText("Meter No: " + customer.getMeterNumber());
            usageLabel.setText("Usage (kWh): " + customer.getUsage());
            billLabel.setText(String.format("Bill Amount: M%.2f", customer.getBill()));

            // Print
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(null)) {
                boolean success = job.printPage(root);
                if (success) {
                    job.endJob();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
