/*
    Author: William Asp
    September 28, 2020

    **What it is**
        an "index"-program which allows the user to ask questions
        "on which positions in the text (i.e. the number of characters
        from the beginning) you find the word X".

    **How it is used**
        Select what file to be read in main()
        The program will prompt for input.

    **Testing**
        Run the file with the -t flag.
 */

package main.lab3;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Stores a text files words and its character indexes. Able do query words for its indexes.
 */
public class IndexQuery {
    private SearchTree<String, LinkedList<Integer>> ST;
    private BufferedReader br;

    /**
     * Consructor
     * @param fileName file to read
     */
    public IndexQuery (String fileName) {
        ST = new BST<String, LinkedList<Integer>>();
        try {
            load(fileName);
        } catch (IOException e) {
            System.out.println("File could not be read");
        }
    }

    /**
     * Load a file into a ST
     * @param fileName file to load
     * @return A ST
     * @throws IOException if file does not exist
     */
    private SearchTree load (String fileName) throws IOException {
        // initiate scanner
        InputStream stream = new FileInputStream(fileName);
        Scanner sc = new Scanner(stream);
        // initiate indexreader
        File file = new File(fileName);
        br = new BufferedReader(new FileReader(file));
        String key;
        int index = 1;
        while(sc.hasNext()) {
            while(! Character.isLetter(br.read())) index++;
            key = sc.next();
            putWord(key, index);
            moveCursorForward(key.length());
            index += key.length();
        }
        return ST;
    }

    // move the characterreaders index forward in file
    private void moveCursorForward (int keyLength) throws IOException {
        for(int i = 0; i < keyLength -1; i++) {
            br.read();
        }
    }

    /**
     * Put the word in the ST
     * @param key
     * @param index
     */
    private void putWord (String key, int index) {
        LinkedList list;
        if (ST.contains(key)) {
            list = ST.get(key);
        }
        else {
            list = new LinkedList();
        }
        list.add(index);
        ST.put(key, list);
    }

    /**
     * Get list of indexes for one word
     * @param key word
     * @return list of indexes
     */
    public LinkedList<Integer> query (String key) {
        return ST.get(key);
    }

    /**
     * Iteratively ask and get words from stdin
     * @throws IOException
     */
    public void indexQuery() throws IOException {
        Scanner sc = new Scanner(System.in);
        String input;
        System.out.println("Enter word to check index(es) it appears on.");
        while (sc.hasNext()) {
            input = sc.next();
            if (query(input) == null)
                System.out.println("the input did not match any word in the text");
            else {
                System.out.print("The word: " + input + " appears at index: ");
                for (Integer index : query(input)) {
                    System.out.print(index + ", ");
                }
                System.out.println();
            }
        }
    }

    /**
     * Run test or run iteratively.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if(args.length > 0 && args[0].equals("-t")) {
            String fileName = "test.txt";
            File f = new File(fileName);
            FileWriter fw = new FileWriter(f);
            fw.write("a b c d d");
            fw.close();
            IndexQuery q = new IndexQuery(fileName);
            if(!(q.query("a").getFirst() == 1) ||
                    !(q.query("b").getFirst() == 3) ||
                    !(q.query("c").getFirst() == 5)) {
                System.out.println("Test failed.");
                System.exit(0);
            }
            int n = 7;
            for (Integer i : q.query("d")) {
                if(! (i == n)) {
                    System.out.println("Test failed.");
                    System.exit(0);
                }
                n += 2;
            }
            System.out.println("Test successful.");
            f.delete();
        } else {
            String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Searching\\theTextFilter.txt";
            IndexQuery q = new IndexQuery(fileName);
            q.indexQuery();
        }
    }
}