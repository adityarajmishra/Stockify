package com.stockify.platform.service;

import com.stockify.platform.model.Product;
import java.util.*;

public class InventoryService {
    private static InventoryService instance;
    private final Map<String, Product> inventory;
    private final List<String> productOrder;

    private InventoryService() {
        this.inventory = new HashMap<>();
        this.productOrder = new ArrayList<>();
    }

    public static synchronized InventoryService getInstance() {
        if (instance == null) {
            instance = new InventoryService();
        }
        return instance;
    }

    public void reset() {
        inventory.clear();
        productOrder.clear();
    }

    public boolean addProduct(String productId, String productName, int quantity, int price) {
        if (inventory.containsKey(productId)) {
            return false;
        }

        Product product = new Product(productId, productName, quantity, price);
        inventory.put(productId, product);
        productOrder.add(productId);
        return true;
    }

    public boolean updateQuantity(String productId, int quantity) {
        Product product = inventory.get(productId);
        if (product == null) {
            return false;
        }
        product.setQuantity(quantity);
        return true;
    }

    public boolean updatePrice(String productId, int price) {
        Product product = inventory.get(productId);
        if (product == null) {
            return false;
        }
        product.setPrice(price);
        return true;
    }

    public Product getProduct(String productId) {
        return inventory.get(productId);
    }

    public boolean removeProduct(String productId) {
        if (!inventory.containsKey(productId)) {
            return false;
        }
        inventory.remove(productId);
        productOrder.remove(productId);
        return true;
    }

    public List<Product> getAllProducts() {
        return productOrder.stream()
                .map(inventory::get)
                .toList();
    }

    public List<Product> getSortedProducts(String sortField) {
        Comparator<Product> comparator = switch (sortField.toLowerCase()) {
            case "id" -> Comparator.comparing(Product::getProductId);
            case "name" -> Comparator.comparing(Product::getProductName);
            case "quantity" -> Comparator.comparingInt(Product::getQuantity);
            case "price" -> Comparator.comparingInt(Product::getPrice);
            default -> null;
        };

        if (comparator == null) {
            return Collections.emptyList();
        }

        return inventory.values().stream()
                .sorted(comparator)
                .toList();
    }

    public int getProductCount() {
        return inventory.size();
    }

    public int getTotalInventoryValue() {
        return inventory.values().stream()
                .mapToInt(Product::getTotalValue)
                .sum();
    }
}