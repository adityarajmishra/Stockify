package com.stockify.platform.model;

public class Product {
    private final String productId;
    private final String productName;
    private int quantity;
    private int price;

    public Product(String productId, String productName, int quantity, int price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public int getPrice() { return price; }

    // Setters for mutable properties
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(int price) { this.price = price; }

    public int getTotalValue() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%d:%d", productId, productName, quantity, price);
    }
}