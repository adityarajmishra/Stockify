package com.stockify.platform.controller;

import com.stockify.platform.model.Product;
import com.stockify.platform.service.InventoryService;
import com.stockify.platform.util.ValidationUtil;
import java.util.List;

public class CommandController {
    private final InventoryService inventoryService;

    public CommandController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void processCommand(String command) {
        try {
            String[] parts = command.trim().split("\\s+", -1);

            switch (parts[0]) {
                case "ADD_PRODUCT" -> handleAddProduct(parts);
                case "UPDATE_QUANTITY" -> handleUpdateQuantity(parts);
                case "UPDATE_PRICE" -> handleUpdatePrice(parts);
                case "VIEW_PRODUCT" -> handleViewProduct(parts);
                case "REMOVE_PRODUCT" -> handleRemoveProduct(parts);
                case "LIST_PRODUCTS" -> handleListProducts();
                case "SORT_PRODUCTS" -> handleSortProducts(parts);
                default -> System.out.println("REQUEST_PATTERN_INVALID");
            }
        } catch (Exception e) {
            System.out.println("REQUEST_PATTERN_INVALID");
        }
    }

    private void handleAddProduct(String[] parts) {
        if (parts.length != 5) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        String productId = parts[1];
        String productName = parts[2];

        // Remove quotes if present
        if (productName.startsWith("\"") && productName.endsWith("\"")) {
            productName = productName.substring(1, productName.length() - 1);
        }

        if (!ValidationUtil.isValidProductName(productName)) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        try {
            int quantity = Integer.parseInt(parts[3]);
            int price = Integer.parseInt(parts[4]);

            if (!ValidationUtil.isValidQuantityAndPrice(quantity, price)) {
                System.out.println("REQUEST_PATTERN_INVALID");
                return;
            }

            boolean success = inventoryService.addProduct(productId, productName, quantity, price);
            System.out.println(success ? "SUCCESS" : "PRODUCT_ALREADY_EXISTS");
        } catch (NumberFormatException e) {
            System.out.println("REQUEST_PATTERN_INVALID");
        }
    }

    private void handleUpdateQuantity(String[] parts) {
        if (parts.length != 3) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        try {
            int quantity = Integer.parseInt(parts[2]);
            if (quantity <= 0) {
                System.out.println("REQUEST_PATTERN_INVALID");
                return;
            }

            boolean success = inventoryService.updateQuantity(parts[1], quantity);
            System.out.println(success ? "SUCCESS" : "PRODUCT_NOT_FOUND");
        } catch (NumberFormatException e) {
            System.out.println("REQUEST_PATTERN_INVALID");
        }
    }

    private void handleUpdatePrice(String[] parts) {
        if (parts.length != 3) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        try {
            int price = Integer.parseInt(parts[2]);
            if (price <= 0) {
                System.out.println("REQUEST_PATTERN_INVALID");
                return;
            }

            boolean success = inventoryService.updatePrice(parts[1], price);
            System.out.println(success ? "SUCCESS" : "PRODUCT_NOT_FOUND");
        } catch (NumberFormatException e) {
            System.out.println("REQUEST_PATTERN_INVALID");
        }
    }

    private void handleViewProduct(String[] parts) {
        if (parts.length != 2) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        Product product = inventoryService.getProduct(parts[1]);
        if (product == null) {
            System.out.println("PRODUCT_NOT_FOUND");
            return;
        }

        System.out.println("Product ID: " + product.getProductId());
        System.out.println("Name: " + product.getProductName());
        System.out.println("Quantity: " + product.getQuantity());
        System.out.println("Price: " + product.getPrice());
    }

    private void handleRemoveProduct(String[] parts) {
        if (parts.length != 2) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        boolean success = inventoryService.removeProduct(parts[1]);
        System.out.println(success ? "SUCCESS" : "PRODUCT_NOT_FOUND");
    }

    private void handleListProducts() {
        List<Product> products = inventoryService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("NO_PRODUCTS_AVAILABLE");
            return;
        }

        products.forEach(System.out::println);
    }

    private void handleSortProducts(String[] parts) {
        if (parts.length != 2) {
            System.out.println("REQUEST_PATTERN_INVALID");
            return;
        }

        List<Product> sortedProducts = inventoryService.getSortedProducts(parts[1]);
        if (sortedProducts.isEmpty()) {
            System.out.println("INVALID_SORT_FIELD");
            return;
        }

        sortedProducts.forEach(System.out::println);
    }

    public void handleExit() {
        System.out.println("Product Count: " + inventoryService.getProductCount());
        System.out.println("Total Inventory Value: " + inventoryService.getTotalInventoryValue());
        System.out.println("Goodbye!");
    }
}