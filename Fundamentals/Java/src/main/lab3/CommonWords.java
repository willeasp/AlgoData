/*
    Author: William Asp
    September 28, 2020

    **What it is**
        a program which takes as input a text file and allows the user
        to (repeatedly without re-reading the input file) ask questions:
            - i) Which is the k:th most common word
            - ii) Which are the k:th to the k+n:th most common words

    **How it works**
        Run the program. The program will read the file "leipzig1mFilter.txt"
        Then the program will prompt for an integer k.
        The program will then show the kth most common word in O(1) time.

        Type "range" to input 2 integers, k and n.
        The program will then show the kth to (k + n)th most
        common words.

        (Extra function)
        Type a word in quotationmarks to view the number of occurrences
        of the word. The result will be presented in O(N log(N)) time.

    **Testing**
        Run the program with -t as program arguments.
 */

package main.lab3;

import main.util.HashMap;
import main.util.LinkedList;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Reads a file and can answer questions about the commonness of words in the file in constant time.
 */
public class CommonWords {
    private HashMap<Integer, String> hashMap;
    private BST<String, Integer> wordCountBST;
    private Scanner sc;             // user input
    private int leastCommon;

    /**
     * Constructor.
     * Loads the input
     * @param input
     */
    public CommonWords(InputStream input) {
        System.out.println("Reading file ...");
        long start = System.nanoTime();         // Start loading time
        wordCountBST = load(input);             // Load file and count occurrences

        BST<Integer, LinkedList<String>> sortedWordCount = sortWordCountDescending();       // sort wordCountBST after number of occurrences

        int numberOfWords = wordCountBST.size();    // Number of unique words in file
        System.out.println("The number of words in the list is " + numberOfWords + " words.");
        int mapSize = numberOfWords / 4;            // hashMap size
        System.out.println("The capacity of the hashmap is " + mapSize + " buckets.");
        hashMap = new HashMap(mapSize);

        // put every word with its rank in the hashMap
        int rank = 0;
        for(Integer count : sortedWordCount) {
            for(String word : sortedWordCount.get(count))
                hashMap.put(++rank, word);
        }

        leastCommon = rank;                             // "index" of the least common word

        long stop = System.nanoTime();              // stop the loading time
        double seconds = (double)(stop - start) / 1000000 / 1000;
        System.out.println("Loading time: " + seconds + " seconds");

    }

    // load the file in the BST with the number of occurrences of each word as value
    private BST load (InputStream input) {
        BST<String, Integer> ST = new BST();
        Scanner sc = new Scanner(input);
        String key;
        while(sc.hasNext()) {
            key = sc.next();
            if (ST.contains(key)) {
                ST.put(key, ST.get(key) + 1);
            }
            else {
                ST.put(key, 1);
            }
        }
        return ST;
    }

    // sort wordCountBST by Value and put in sortedWordCount
    private BST<Integer, LinkedList<String>> sortWordCountDescending() {
        BST<Integer, LinkedList<String>> sortedWordCount = new BST();
        // sort all words according to number of occurrences reversed
        for(String word : wordCountBST) {
            int key = wordCountBST.get(word) * -1;       // get number of occurrences
            LinkedList list;
            if (sortedWordCount.contains(key)) {
                list = sortedWordCount.get(key);
            }
            else {
                list = new LinkedList();
            }
            list.add(word);// add word to the occurrence
            sortedWordCount.put((key), list);
        }
        return sortedWordCount;
    }

    /**
     * Run the program
     */
    public void commonWords () {
        interactive();
    }

    // Read commands from user
    private void interactive() {
        sc = new Scanner(System.in);
        String input;
        String word;
        int occurrences;
        System.out.println("Enter a number k to show the texts kth most common word.");
        System.out.println("Type \"range\" and enter two numbers k and n to show the most common words from kth to (k + n)th");
        while(true) {
            input = sc.next();
            if(input.equals("range"))
                range();
            else if (Pattern.matches("\"(.*?)\"", input))
                occurences(input.replaceAll("\"", ""));
            else {
                try {
                    single(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter an integer or type \"range\".");
                }
            }
        }
    }

    // return number of occurences of key
    private void occurences (String key) {
        Integer occurrences;
        if((occurrences = wordCountBST.get(key)) == null)
            occurrences = 0;
        System.out.println("The word \"" + key + "\" occurs " + occurrences + " times.");
    }

    // print word that is kth most common
    private void single ( int k ) {
        String word;
        if((word = getCommon(k)) == null) {
            System.out.println("There is no such word.");
            System.out.println("The least most common word is \"" + getCommon(getLeastCommon()) + "\" which sits at number " + getLeastCommon());
            return;
        }
        int occurrences = wordCountBST.get(word);
        System.out.println("The " + k + " most common word is \"" + word + "\"");
    }

    public String getCommon (int kth) {
        return hashMap.get(kth);
    }

    // print words that is k to k+n most common
    private void range() {
        System.out.println("Enter two integers k and n:");
        int k = readInteger('k');
        int n = readInteger('n');

        System.out.println("The " + k + " to " + (k+n) + " most common words are");
        for(int i = k; i < k+n; i++) {
            single(i);
        }
    }

    // read integers for the range function
    private int readInteger( char character ) {
        int i = 0;
        String input;
        while (true){
            System.out.println("Please enter integer " + character);
            try {
                input = sc.next();
                i = Integer.parseInt(input);
                break;
            } catch (Exception e) {

            }
        }
        return i;
    }

    /**
     *
     * @return the k of the least common word
     */
    public int getLeastCommon() {
        return leastCommon;
    }

    // run the program
    public static void main(String[] args) throws IOException {
        if(args.length > 0 && args[0].equals("-t")) {
            String fileName = "test.txt";
            File f = new File(fileName);
            FileWriter fw = new FileWriter(f);
            fw.write("a a a a b b b c c d");
            fw.close();

            // test
            InputStream input = new FileInputStream(f);
            CommonWords c = new CommonWords(input);
            if(     ! c.getCommon(1).equals("a") ||
                    ! c.getCommon(2).equals("b") ||
                    ! c.getCommon(3).equals("c") ||
                    ! c.getCommon(4).equals("d"))
                System.out.println("Test failed.");
            else
                System.out.println("Test successful.");
            f.delete();
        } else {
            String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Searching\\leipzig1mFilter.txt";
            InputStream input = new FileInputStream(fileName);
            CommonWords commonWords = new CommonWords(input);
            commonWords.commonWords();
        }
    }
}
