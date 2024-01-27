package com.example.timemap.utils;

public class StringTools {
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        } else {
            return Character.toUpperCase(input.charAt(0)) + input.substring(1);
        }
    }
}
