package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */

    private Map<String, TimeSeries> wordsData;
    private TimeSeries countData;

    public NGramMap(String wordsFilename, String countsFilename) {
        this.wordsData = new HashMap<>();
        this.countData = new TimeSeries();

        In wordsIn = new In(wordsFilename);
        while (wordsIn.hasNextLine()) {
            String line = wordsIn.readLine();
            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\t");
            String word = parts[0];
            int year = Integer.parseInt(parts[1]);
            double count = Double.parseDouble(parts[2]);

            if (!this.wordsData.containsKey(word)) {
                TimeSeries ts = new TimeSeries();
                ts.put(year, count);
                this.wordsData.put(word, ts);
            } else {
                TimeSeries ts = this.wordsData.get(word);
                ts.put(year, count);
            }
        }

        In countsIn = new In(countsFilename);
        while (countsIn.hasNextLine()) {
            String line = countsIn.readLine();
            String[] parts = line.split(",");

            int year = Integer.parseInt(parts[0]);
            double count = Double.parseDouble(parts[1]);

            this.countData.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (wordsData.containsKey(word)) {
            return new TimeSeries(wordsData.get(word), startYear, endYear);
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (wordsData.containsKey(word)) {
            return new TimeSeries(wordsData.get(word), MIN_YEAR, MAX_YEAR);
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(countData, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (wordsData.containsKey(word)) {
            TimeSeries count = countHistory(word, startYear, endYear);
            return count.dividedBy(totalCountHistory());
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (wordsData.containsKey(word)) {
            TimeSeries count = countHistory(word);
            return count.dividedBy(totalCountHistory());
        } else {
            return new TimeSeries();
        }
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            TimeSeries item = weightHistory(word, startYear, endYear);
            sum = sum.plus(item);
        }
        return sum;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries sum = new TimeSeries();
        for (String word : words) {
            TimeSeries item = weightHistory(word);
            sum = sum.plus(item);
        }
        return sum;
    }
}
