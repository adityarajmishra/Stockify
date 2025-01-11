package com.stockify.platform.util;

public class ValidationUtil {
    private ValidationUtil() {} // Prevent instantiation

    public static boolean isValidProductName(String name) {
        return name != null && !name.contains(" ") && !name.contains(";");
    }

    public static boolean isValidQuantityAndPrice(int quantity, int price) {
        return quantity > 0 && price > 0;
    }
}