package org.tumasov.siberteam.test.services;

import org.tumasov.siberteam.test.entities.AnagramHolder;
import org.tumasov.siberteam.test.entities.CountedLetters;

import java.util.*;

public class AnagramService {
    private Map<CountedLetters, AnagramHolder> anagramsMap = new HashMap<>();

    public Map<CountedLetters, AnagramHolder> build(List<String> words) {
        for (String word: words) {
            final CountedLetters counter = CountedLetters.getLettersCounter(word);
            final AnagramHolder anagram = new AnagramHolder(word);
            if (anagramsMap.putIfAbsent(counter, anagram) != null) {
                anagramsMap.get(counter).add(word);
            }
        }
        return anagramsMap;
    }

    public List<AnagramHolder> sortAnagrams() {
        List<AnagramHolder> anagramsList = new ArrayList<AnagramHolder>(anagramsMap.values());

        Collections.sort(anagramsList, new Comparator<AnagramHolder>() {
            @Override
            public int compare(AnagramHolder o1, AnagramHolder o2) {
                return Integer.compare(o1.getLengthOfWord(), o2.getLengthOfWord());
            }
        });

        return anagramsList;
    }
}