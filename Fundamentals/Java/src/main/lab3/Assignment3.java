package main.lab3;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Assignment3 {

    /**
     * The hash function in HashMap.java
     * @param key the Object to be hashed
     * @return
     */
    static int hashMapHash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    static int hashHash(Object key) {
        return key.hashCode();
    }

    static int hashMapIndex(int hash, int n) {
        return (n-1 & hash);
    }

    static int moduloIndex(int hash, int n) {
        return Math.abs(hash % n);
    }

    private static int[] hashMapIndexBalance(int n, SearchTree<String, Integer> input) {
        int[] buckets = new int[n];
        int index = 0;
        for(String s : input) {
            index = hashMapIndex(hashMapHash(s), n);
            buckets[index] += 1;
        }
        return buckets;
    }

    private static int[] hashModuloIndexBalance(int n, SearchTree<String, Integer> input) {
        int[] buckets = new int[n];
        int index = 0;
        for(String s : input) {
            index = moduloIndex(hashHash(s), n);
            buckets[index] += 1;
        }
        return buckets;
    }

    private static BST load (InputStream input) {
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

    private static BST loadHashes (InputStream input) {
        BST<String, Integer> ST = new BST();
        Scanner sc = new Scanner(input);
        String key;
        while(sc.hasNext()) {
            key = sc.next();
            ST.put(key, key.hashCode());
        }
        return ST;
    }

    private static void hashIndexBalanceTest(InputStream input, int n) {
        BST ST = load(input);
        int[] hashMapBalance = hashMapIndexBalance(n, ST);
        int[] hashModuloBalance = hashModuloIndexBalance(n, ST);
        for(int i = 0; i < n; i++) {
            System.out.println(hashMapBalance[i] + " " + hashModuloBalance[i]);
        }
    }

    private static void hashCodeDuplicates (InputStream input) {
        BST<String, Integer> words = loadHashes(input);
        BST<Integer, Integer> hashes = new BST();
        for(String word : words) {
            int hash = words.get(word);
            if (hashes.contains(hash)) {
                hashes.put(hash, hashes.get(hash) + 1);
            }
            else {
                hashes.put(hash, 1);
            }
        }
        int count = 0;
        int hash;
        for(String word : words) {
            if( hashes.get(hash = words.get(word)) > 1) {
                System.out.println(word + " " + hash + " " + hashes.get(hash));
                count++;
            }
        }
        System.out.println(count);
    }

    private static void hashCodeDistribution (InputStream input) {
        BST<String, Integer> ST = loadHashes(input);
        LinkedList<Integer> hashes = new LinkedList();
        for(String s : ST) {
            hashes.add(ST.get(s));
        }
        Collections.sort(hashes);
        int count = 0;
        for(Integer hash : hashes) {
            System.out.println(hash);
            count++;
        }
        System.out.println(count);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Searching\\filter.txt";
        InputStream input = new FileInputStream(fileName);
        //hashIndexBalanceTest(input, 32);
        //hashCodeDuplicates(input);
        hashCodeDistribution(input);
    }
}
