package edu.princeton.cs.algs4;

import main.lab3.BinarySearchST;
import main.lab3.SearchTree;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 *  The {@code FrequencyCounter} class provides a client for
 *  reading in a sequence of words and printing a word (exceeding
 *  a given length) that occurs most frequently. It is useful as
 *  a test client for various symbol table implementations.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/31elementary">Section 3.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class FrequencyCounter {

    // Do not instantiate.
    private FrequencyCounter() { }

    public static void frequencyCounter (String[] args, SearchTree<String, Integer> ST, int N, InputStream stream) {
        int distinct = 0, words = 0;
        int minlen = Integer.parseInt(args[0]);
        Scanner sc = new Scanner(stream);

        int i = 0;
        // compute frequency counts
        while (sc.hasNext() && i++ < N) {
            String key = sc.next();
            if (key.length() < minlen) continue;
            words++;
            if (ST.contains(key)) {
                ST.put(key, ST.get(key) + 1);
            }
            else {
                ST.put(key, 1);
                distinct++;
            }
        }

        // find a key with the highest frequency count
        String max = "";
        ST.put(max, 0);
        for (String word : ST) {
            if (ST.get(word) > ST.get(max))
                max = word;
        }

        /*System.out.println(max + " " + ST.get(max));
        System.out.println("distinct = " + distinct);
        System.out.println("words    = " + words);*/
    }

    /**
     * Reads in a command-line integer and sequence of words from
     * standard input and prints out a word (whose length exceeds
     * the threshold) that occurs most frequently to standard output.
     * It also prints out the number of words whose length exceeds
     * the threshold and the number of distinct such words.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        frequencyCounter(args, new BinarySearchST(), Integer.MAX_VALUE, System.in);
    }
}