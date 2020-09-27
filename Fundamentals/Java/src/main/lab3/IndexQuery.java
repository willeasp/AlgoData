package main.lab3;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class IndexQuery {
    private SearchTree<String, LinkedList<Integer>> ST;
    private BufferedReader br;


    public IndexQuery (String fileName) {
        ST = new BST<String, LinkedList<Integer>>();
        try {
            load(fileName);
        } catch (IOException e) {
            System.out.println("File could not be read");
        }
    }

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

    private void moveCursorForward (int keyLength) throws IOException {
        for(int i = 0; i < keyLength -1; i++) {
            br.read();
        }
    }

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

    public LinkedList<Integer> query (String key) {
        LinkedList<Integer> list;
        return ST.get(key);
    }

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

    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Searching\\filter.txt";
        IndexQuery q = new IndexQuery(fileName);
        q.indexQuery();
    }
}