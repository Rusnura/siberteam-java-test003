package org.tumasov.siberteam.test.services;

public class StringUtil {
    public static String clearPunctuation(String inputString) {
        // Regular expression is very expensive.
        // so I using standard tools to clear the line unnecessary characters.
        StringBuilder result = new StringBuilder(inputString.length());
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (Character.isAlphabetic(c) || Character.isSpaceChar(c)) {
                result.append(c);
            }
        }
        return result.toString().toLowerCase();
    }
}
