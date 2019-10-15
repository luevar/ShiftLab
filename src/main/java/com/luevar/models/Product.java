package com.luevar.models;

public class Product {
    private String name;
    private int quantity;
    private double price;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    void setPrice(double price) {
        this.price = price;
    }

    void increaseQuantityBy(int quantity) {
        this.quantity += quantity;
    }

    public String printProduct() {
        return name + "                " + price + "                " + quantity + "                     " + Math.round(quantity * price * 100.0) / 100.0;
    }
}
