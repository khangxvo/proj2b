import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.AutograderBuddy;
import ngrams.TimeSeries;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Tests the case where the list of words is length greater than 1, but k is still zero. */
public class TestMultiWordK0Hyponyms {
    // this case doesn't use the NGrams dataset at all, so the choice of files is irrelevant
    public static final String WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String LARGE_SYNSET_FILE = "data/wordnet/synsets.txt";
    public static final String LARGE_HYPONYM_FILE = "data/wordnet/hyponyms.txt";

    /** This is an example from the spec.*/
    @Test
    public void testOccurrenceAndChangeK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("occurrence", "change");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[alteration, change, increase, jump, leap, modification, saltation, transition]";
        assertThat(actual).isEqualTo(expected);
    }

    // TODO: Add more unit tests (including edge case tests) here.
    @Test
    public void testNatural_EventAndActionK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, SMALL_SYNSET_FILE, SMALL_HYPONYM_FILE);
        List<String> words = List.of("natural_event", "action");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[change]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testVideoAndRecordingK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("video", "recording");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[video, video_recording, videocassette, videotape]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPastryAndTartK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("pastry", "tart");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[apple_tart, lobster_tart, quiche, quiche_Lorraine, tart, tartlet]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDemotionAndVariationK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("demotion", "variation");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }

    // TODO: Create similar unit test files for the k != 0 cases.

    @Test
    public void testNGMK0() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("demotion", "variation");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
//        String actual = studentHandler.handle(nq);
//        String expected = "[]";
//        assertThat(actual).isEqualTo(expected);
    }

    public void testTS() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                WORDS_FILE, TOTAL_COUNTS_FILE, LARGE_SYNSET_FILE, LARGE_HYPONYM_FILE);
        List<String> words = List.of("airport", "request");

        NgordnetQuery nq = new NgordnetQuery(words, 0, 0, 0);
//        String actual = studentHandler.handle(nq);
//        String expected = "[]";
//        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void testPopularity() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/frequency_tester.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets20.txt",
                "data/wordnet/hyponyms20.txt"
                );

        List<String> words = List.of("AAAA");

        NgordnetQuery nq = new NgordnetQuery(words, 1450, 1500, 3);
        String actual = studentHandler.handle(nq);
        String expected = "[DDDD, EEEE, FFFF]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularityBasic1Word() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("stream");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 3);
        String actual = studentHandler.handle(nq);
        String expected = "[current, flow, run]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularityBasicVine() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("vine");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 9);
        String actual = studentHandler.handle(nq);
        String expected = "[bean, grape, hop, marrow, peanut, pepper, potato, soybean, vine]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularity2FieldEcology() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("field", "ecology");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 5);
        String actual = studentHandler.handle(nq);
        String expected = "[ecology]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularityMultWords() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("structure", "proportion");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 8);
        String actual = studentHandler.handle(nq);
        String expected = "[balance, proportion, symmetry]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularityBasicSubject() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("subject");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 4);
        String actual = studentHandler.handle(nq);
        String expected = "[business, case, law, part]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularityOutScope() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("science", "field");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 3);
        String actual = studentHandler.handle(nq);
        String expected = "[analysis, government, science]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularityBasicTree() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("tree");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 8);
        String actual = studentHandler.handle(nq);
        String expected = "[ash, bay, coffee, fig, lime, orange, pine, tree]";
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void testPopularity4_2() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("location");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 3);
        String actual = studentHandler.handle(nq);
        String expected = "[see, there, work]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularity4_3() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("whole");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 9);
        String actual = studentHandler.handle(nq);
        String expected = "[being, can, first, have, may, one, part, well, work]";
        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void testPopularity4_4() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("message", "proviso");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 5);
        String actual = studentHandler.handle(nq);
        String expected = "[provision]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularity4_5() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("quality", "gumption");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 6);
        String actual = studentHandler.handle(nq);
//        System.out.println(actual);
        String expected = "[backbone, guts, sand]";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testPopularity4_6() {
        NgordnetQueryHandler studentHandler = AutograderBuddy.getHyponymHandler(
                "data/ngrams/top_14377_words.csv", TOTAL_COUNTS_FILE,
                "data/wordnet/synsets.txt",
                "data/wordnet/hyponyms.txt"
        );

        List<String> words = List.of("good", "knee-hi");
        NgordnetQuery nq = new NgordnetQuery(words, 1470, 2019, 7);
        String actual = studentHandler.handle(nq);
        String expected = "[]";
        assertThat(actual).isEqualTo(expected);
    }




}
