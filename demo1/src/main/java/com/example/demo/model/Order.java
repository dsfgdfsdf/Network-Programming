package com.example.demo.model;

import java.time.LocalDate;

public class Order {
    private int id;
    private LocalDate date;
    private double amount;
    private String paymentMethod;

    public boolean isFromLastMonth() {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);
        return date.isAfter(oneMonthAgo);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
