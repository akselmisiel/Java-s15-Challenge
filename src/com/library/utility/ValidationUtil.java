package com.library.utility;

public class ValidationUtil {
    public static boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isValidPrice(double price) {
        return price > 0;
    }

    public static boolean isValidId(int id) {
        return id > 0;
    }

    public static boolean isValidCategory(String category) {
        return category.equalsIgnoreCase("fiction") || category.equalsIgnoreCase("non_fiction") || category.equalsIgnoreCase("history") || category.equalsIgnoreCase("fantasy");
    }
}
