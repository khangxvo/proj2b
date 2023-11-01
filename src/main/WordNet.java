package main;

import java.util.HashMap;
import java.util.HashSet;

public class WordNet {
    public HashMap<Integer, String> words;
    public HashMap<Integer, Synset> diGraph;
    public HashMap<String, HashSet<Integer>> appearance;

    public WordNet() {
        words = new HashMap<>();
        diGraph = new HashMap<>();
        appearance = new HashMap<>();
    }

    /**
     * Add a word and its ID to words map
     * @param wordID: a unique number
     * @param word: a random word
     */
    public void addWord(int wordID, String word) {
        words.put(wordID, word);
    }

    public void addSynset(int synsetID, String wordCollection) {
        Synset newSS = new Synset(synsetID, wordCollection);
        diGraph.put(synsetID, newSS);
        newSS.addWordAppearance(appearance);
    }
}
