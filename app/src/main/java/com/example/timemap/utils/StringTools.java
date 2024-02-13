package com.example.timemap.utils;

/**
 * A utility class providing string manipulation methods.
 */
public class StringTools {
    /**
     * Capitalizes the first character of the input string.
     *
     * @param input The string to be capitalized.
     * @return The input string with the first character in uppercase, or the input if it can't be capitalized.
     */
    public static String capitalize(String input) {
        if (input == null || input.isEmpty() || !Character.isLetter(input.charAt(0))) {
            return input;
        } else {
            return Character.toUpperCase(input.charAt(0)) + input.substring(1);
        }
    }
}
