package main;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Synset {
    private int synsetID;
    private HashSet<String> node;
    private HashSet<Integer> children;

    /**
     *
     * @param wordCollection: a string that is passed from a file that contain a list of string.
     */
    public Synset(int synsetID, String wordCollection) {
        node = new HashSet<>();
        children = new HashSet<>();
        String[] splitWords = wordCollection.split(" ");
        Collections.addAll(node, splitWords);
        this.synsetID = synsetID;
    }

    /**
     *
     * @return a copy of the node
     */
    public HashSet<String> getNode() {
        return new HashSet<>(node);
    }

    /**
     *
     * @param childID: a unique integer that associate with the child.
     */
    public void addChild(int childID) {
        children.add(childID);
    }

    /**
     *
     */
    public HashSet<Integer> getChildren() {
        return new HashSet<>(children);
    }

    /**
     * If a word is in this synset, add the synset id to the word appearance map
     * @param wordsAppearance: a map that contain words and what synset in which they appear
     */
    public void addWordAppearance(HashMap<String, HashSet<Integer>> wordsAppearance) {
        for (String word : node) {
            if (!wordsAppearance.containsKey(word)) {
                HashSet<Integer> intArr = new HashSet<>();
                intArr.add(synsetID);
                wordsAppearance.put(word, intArr);
            } else {
                wordsAppearance.get(word).add(synsetID);
            }
        }
    }

}
