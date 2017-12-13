package org.tumasov.siberteam.test.entities;

import java.util.ArrayList;
import java.util.List;

public class AnagramHolder {
    private final List<String> anagramList = new ArrayList<>();
    private int lengthOfWord = 0;

    public AnagramHolder(String word) {
        this.add(word);
        this.lengthOfWord = word.length();
    }

    public void add(String word) {
        this.anagramList.add(word);
    }

    public int getLengthOfWord() {
        return lengthOfWord;
    }

    public List<String> getAnagrams() {
        return this.anagramList;
    }
}
