package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import edu.princeton.cs.algs4.In;
import ngrams.NGramMap;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wn;
    private HashSet<String> longestSet;
    private NGramMap ngm;
    private SortedMap<Double, HashSet<String>> popularity;

    @Override
    public String handle(NgordnetQuery q) {
        popularity.clear();
        ArrayList<String> returnList = getCommonHyponyms(q.words());
        int k = q.k();
        if (k == 0) {
            Collections.sort(returnList);
            return returnList.toString();
        }
        buildPopularity(returnList, q.startYear(), q.endYear(), q.k());
        Iterator<HashSet<String>> iterator = popularity.values().iterator();
        returnList.clear();
        while (k > 0 && iterator.hasNext()) {
            HashSet<String> wordSet = iterator.next();
            for (String word : wordSet) {
                if (k > 0) {
                    returnList.add(word);
                    k--;
                } else {
                    break;
                }
            }
        }
        Collections.sort(returnList);
        return returnList.toString();
    }

    public HyponymsHandler(String wordFile, String countFile,
                           String synsetsFile, String hyponymsFile) {
        wn = new WordNet();
        ngm = new NGramMap(wordFile, countFile);
        popularity = new TreeMap<>(Comparator.reverseOrder());
        In inputFile = new In(synsetsFile);
        int synsetID = 0;
        while (inputFile.hasNextLine()) {
            String inputLine = inputFile.readLine();
            String[] splitLine = inputLine.split(",");
            int wordID = Integer.parseInt(splitLine[0]);
            wn.addWord(wordID, splitLine[1]);
            wn.addSynset(synsetID, splitLine[1]);
            synsetID++;
        }

        inputFile = new In(hyponymsFile);
        while (inputFile.hasNextLine()) {
            String inputLine = inputFile.readLine();
            String[] splitLine = inputLine.split(",");
            int parentID = Integer.parseInt(splitLine[0]);
            Synset parentSS = wn.diGraph.get(parentID);
            for (int i = 1; i < splitLine.length; i++) {
                int childID = Integer.parseInt(splitLine[i]);
                parentSS.addChild(childID);
            }
        }
    }

    public HashSet<String> getHyponyms(String word) {
        HashSet<Integer> wordApperance = wn.appearance.get(word);
        HashSet<String> result = new HashSet<>();
        if (wordApperance != null) {
            for (Integer i : wordApperance) {
                result.addAll(getHyponymsByID(i));
            }
        }
        return result;
    }

    private HashSet<String> getHyponymsByID(Integer id) {
        Synset wordSS = wn.diGraph.get(id);
        HashSet<String> result = new HashSet<>(wordSS.getNode());
        if (!wordSS.getChildren().isEmpty()) {
            for (Integer i : wordSS.getChildren()) {
                result.addAll(getHyponymsByID(i));
            }
        }
        return result;
    }

    private ArrayList<String> getCommonHyponyms(List<String> words) {
        ArrayList<String> returnList = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            if (i == 0) {
                HashSet<String> wordSet = getHyponyms(words.get(i));
                returnList = new ArrayList<>(wordSet);
            }
            HashSet<String> wordSet = getHyponyms(words.get(i));
            returnList.retainAll(wordSet);
        }
        return returnList;
    }

    private void buildPopularity(ArrayList<String> wordList,
                                 int startYear, int endYear, int k) {
        for (String word : wordList) {
            ArrayList<Double> count = new ArrayList<>(
                    ngm.countHistory(word, startYear, endYear).values());
            double sum = 0;
            for (double d : count) {
                sum += d;
            }
            if (sum > 0) {
                if (!popularity.containsKey(sum)) {
                    HashSet<String> wordSet = new HashSet<>();
                    wordSet.add(word);
                    popularity.put(sum, wordSet);
                } else {
                    popularity.get(sum).add(word);
                }
            }
        }
    }
}
