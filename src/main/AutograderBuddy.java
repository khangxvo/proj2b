package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {

//        throw new RuntimeException("Please fill out AutograderBuddy.java!");
//        return new HyponymsHandler(synsetFile, hyponymFile, wordFile, countFile);
        return new HyponymsHandler(wordFile, countFile, synsetFile, hyponymFile);
    }
}
