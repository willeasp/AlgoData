package main.lab3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;

public class Leipzig {
    private HashMap<Integer, String> hashMap;
    private BST<String, Integer> ST;
    private Scanner sc;             // user input
    private int leastCommon;

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

    private class STNode {
        public final int count;
        public final String word;

        private STNode(String word, int count) {
            this.count = count;
            this.word = word;
        }
    }

    public void leipZig (InputStream input) {
        System.out.println("Reading file ...");
        long start = System.nanoTime();
        ST = load(input);
        LinkedList<STNode> list = new LinkedList();

        for(String word : ST) {
            list.add( new STNode(word, ST.get(word)) );
        }

        Collections.sort(list, new Comparator<STNode>() {
            @Override
            public int compare(STNode o1, STNode o2) {
                return o2.count - o1.count;
            }
        });

        int wordCount = ST.size();
        int mapSize = wordCount / 4;
        hashMap = new HashMap(mapSize);

        int rank = 1;
        for(STNode node : list) {
            hashMap.put(rank++, node.word);
        }

        leastCommon = list.size();

        long stop = System.nanoTime();
        double seconds = (double)(stop - start) / 1000000 / 1000;
        System.out.println("Loading time: " + seconds + " seconds");

        interactive();
    }

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
            else {
                try {
                    single(Integer.parseInt(input));
                } catch (NumberFormatException e) {
                    System.out.println("Please enter an integer or type \"range\".");
                }
            }

        }
    }

    private void single ( int k ) {
        String word;
        if((word = hashMap.get(k)) == null) {
            System.out.println("There is no such word.");
            System.out.println("The least most common word is \"" + hashMap.get(getLeastCommon()) + "\" which sits at number " + getLeastCommon());
            return;
        }
        int occurrences = ST.get(word);
        System.out.println("The " + k + " most common word is \"" + word + "\" which has " + occurrences + " occurences.");
    }

    private void range() {
        System.out.println("Enter two integers k and n:");
        int k = readInteger('k');
        int n = readInteger('n');

        System.out.println("The " + k + " to " + (k+n) + " most common words are");
        for(int i = k; i < k+n; i++) {
            single(i);
        }
    }

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

    public int getLeastCommon() {
        return leastCommon;
    }

    public static void main(String[] args) throws FileNotFoundException {

        String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Searching\\filter.txt";
        InputStream input = new FileInputStream(fileName);
        Leipzig l = new Leipzig();
        l.leipZig(input);

    }
}
