/*
    Author: William Asp
    September 28, 2020

    **What it is**
        Program that will test different properties of the
        hashcode() function.

    **How it is used**
        Choose what test to run in the main function then compile and
        run.
 */

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

    /**
     * Hashes the key according to built in hashCode()
     * @param key
     * @return The built in hashCode() for the key
     */
    static int hashHash(Object key) {
        return key.hashCode();
    }

    /**
     * Hashmap.java built in indexing function
     * @param hash hashCode to be indexed
     * @param n number of indexes to choose from
     * @return index
     */
    static int hashMapIndex(int hash, int n) {
        return (n-1 & hash);
    }

    /**
     * Simple indexing function using modulo
     * @param hash To be indexed
     * @param n number of indexes to choose from
     * @return index
     */
    static int moduloIndex(int hash, int n) {
        return Math.abs(hash % n);
    }

    /**
     * Check the balance a hashmap produces over a determined size array
     * @param n size of array
     * @param input different elements to hash
     * @return an array of number of elements per index
     */
    private static int[] hashMapIndexBalance(int n, SearchTree<String, Integer> input) {
        int[] buckets = new int[n];
        int index = 0;
        for(String s : input) {
            index = hashMapIndex(hashMapHash(s), n);
            buckets[index] += 1;
        }
        return buckets;
    }

    /**
     * Check the balance a simple hashcode and modulo indexing produces over a determined size array
     * @param n size of array
     * @param input different elements to hash
     * @return an array of number of elements per index
     */
    private static int[] hashModuloIndexBalance(int n, SearchTree<String, Integer> input) {
        int[] buckets = new int[n];
        int index = 0;
        for(String s : input) {
            index = moduloIndex(hashHash(s), n);
            buckets[index] += 1;
        }
        return buckets;
    }

    /**
     * read a textfiles words into a BST
     * @param input textfile
     * @return the BST loaded with all words
     */
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

    /**
     * Loads a files words and makes their keys the words hash.
     * @param input a textfile
     * @return The BST with words as keys and hashes as values
     */
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

    /**
     * Tests the "index balance" for HashMaps indexing and a regular
     * hashCode() and modulo indexing.
     * @param input textfile
     * @param n size of hashmap
     */
    private static void hashIndexBalanceTest(InputStream input, int n) {
        BST ST = load(input);
        int[] hashMapBalance = hashMapIndexBalance(n, ST);
        int[] hashModuloBalance = hashModuloIndexBalance(n, ST);
        for(int i = 0; i < n; i++) {
            System.out.println(hashMapBalance[i] + " " + hashModuloBalance[i]);
        }
    }

    /**
     * Check what hashCode() duplicates there are in text input
     * @param input textfile
     */
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

    /**
     * Get all hashCodes from input printed to the screen, sorted
     * @param input textfile
     */
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

    /**
     * Choose what test to run, then compile and run.
     * @throws FileNotFoundException if there is no such file
     */
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Searching\\theTextFilter.txt";
        InputStream input = new FileInputStream(fileName);
        hashIndexBalanceTest(input, 64);
        //hashCodeDuplicates(input);
        //hashCodeDistribution(input);
    }
}
