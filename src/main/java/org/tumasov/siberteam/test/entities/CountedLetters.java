package org.tumasov.siberteam.test.entities;

import java.util.HashMap;
import java.util.Map;

public class CountedLetters {
    private Map<Character, Integer> lettersCountMap;

    private CountedLetters(Map<Character, Integer> lettersCountMap) {
        this.lettersCountMap = lettersCountMap;
    }

    public static CountedLetters getLettersCounter(String word) {
        final Map<Character, Integer> symbolsCount = new HashMap<>();
        final int lengthOfWord = word.length();

        for (int i = 0; i < lengthOfWord; i++) {
            final Character symbol = word.charAt(i);
            final Integer count = symbolsCount.get(symbol);
            if (count == null) {
                symbolsCount.put(symbol, 1);
            } else {
                symbolsCount.put(symbol, count + 1);
            }
        }
        return new CountedLetters(symbolsCount);
    }

    public Map<Character, Integer> getLettersCountMap() {
        return lettersCountMap;
    }

    @Override
    public int hashCode() {
        return lettersCountMap.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CountedLetters) {
            return lettersCountMap.equals(((CountedLetters) obj).getLettersCountMap());
        }
        return false;
    }
}
