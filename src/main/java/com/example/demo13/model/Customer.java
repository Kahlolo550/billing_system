package com.example.demo13.model;

import javafx.beans.property.*;

public class Customer {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty meter = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final DoubleProperty usage = new SimpleDoubleProperty();
    private final DoubleProperty bill = new SimpleDoubleProperty();

    public Customer(String id, String name, String meter, String address, double usage) {
        this.id.set(id);
        this.name.set(name);
        this.meter.set(meter);
        this.address.set(address);
        this.usage.set(usage);
        this.bill.set(calculateBill(usage));
    }

    private double calculateBill(double usage) {
        double bill = 0;
        if (usage <= 100) bill = usage * 1.20;
        else if (usage <= 300) bill = (100 * 1.20) + ((usage - 100) * 1.50);
        else bill = (100 * 1.20) + (200 * 1.50) + ((usage - 300) * 2.00);
        return bill;
    }

    // Getters & setters
    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getMeter() { return meter.get(); }
    public String getAddress() { return address.get(); }
    public double getUsage() { return usage.get(); }
    public double getBill() { return bill.get(); }

    public void setBill(double value) { bill.set(value); }

    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty meterProperty() { return meter; }
    public StringProperty addressProperty() { return address; }
    public DoubleProperty usageProperty() { return usage; }
    public DoubleProperty billProperty() { return bill; }
}
