package com.example.timemap.utils;

public class StringTools {
    /**
     * @param input The string to be capitalized
     * @return input with the first character toUpperCase, or input if cant be capitalized
     */
    public static String capitalize(String input) {
        if (input == null || input.isEmpty() || !Character.isLetter(input.charAt(0))) {
            return input;
        } else {
            return Character.toUpperCase(input.charAt(0)) + input.substring(1);
        }
    }

}
