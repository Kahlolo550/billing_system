package com.example.demo13.model;

public class BillCalculator {
    public static double calculate(double usage) {
        if (usage <= 100) return usage * 1.20;
        else if (usage <= 300) return usage * 1.50;
        else return usage * 2.00;
    }
}
