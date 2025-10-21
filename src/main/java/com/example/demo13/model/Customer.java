package com.example.demo13.model;

import javafx.beans.property.*;

public class Customer {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty meterNumber = new SimpleStringProperty();
    private final DoubleProperty usage = new SimpleDoubleProperty();
    private final DoubleProperty bill = new SimpleDoubleProperty();

    public Customer(String id, String name, String address, String meterNumber, double usage) {
        this.id.set(id);
        this.name.set(name);
        this.address.set(address);
        this.meterNumber.set(meterNumber);
        this.usage.set(usage);
        this.bill.set(calculateBill(usage));
    }

    private double calculateBill(double usage) {
        if (usage <= 100) return usage * 1.20;
        else if (usage <= 300) return (100 * 1.20) + ((usage - 100) * 1.50);
        else return (100 * 1.20) + (200 * 1.50) + ((usage - 300) * 2.00);
    }

    // Getters
    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getAddress() { return address.get(); }
    public String getMeterNumber() { return meterNumber.get(); }
    public double getUsage() { return usage.get(); }
    public double getBill() { return bill.get(); }

    // Setters
    public void setBill(double value) { bill.set(value); }

    // Properties
    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty addressProperty() { return address; }
    public StringProperty meterNumberProperty() { return meterNumber; }
    public DoubleProperty usageProperty() { return usage; }
    public DoubleProperty billProperty() { return bill; }
}
