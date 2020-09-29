/*
    Author: William Asp
    September 28, 2020

    **What it is**
        A program to compare the running times for BinarySearchST.java
        and BST.java when running the program FrequencyCounter.java.

    **How it is used**
        The program tests the chosen SearchTree sent to the "time" function.
        To run different BSTs, simply change what SearchTree should be used.

 */

package main.lab3;

import edu.princeton.cs.algs4.FrequencyCounter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BSTStopwatch {

    /**
     * Return the time to run FrequencyCounter with SearchTree ST
     * @param args arguments to FC
     * @param ST SearchTree
     * @param N Number of words to read
     * @param fileName File to read from
     * @return The time to run FC in ms
     * @throws FileNotFoundException if there is no such file.
     */
    private static double time (String[] args, SearchTree ST, int N, String fileName) throws FileNotFoundException {
        InputStream stream = new FileInputStream(fileName);
        long start = System.nanoTime();
        FrequencyCounter.frequencyCounter(args, ST, N, stream);
        long stop = System.nanoTime();
        long time = stop - start;
        double millis = time / 1000000;
        return millis;
    }

    /**
     * Choose what ST to test here
     * @param args
     * @throws FileNotFoundException
     */
    public static void main (String[] args) throws FileNotFoundException {
        String file = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Searching\\theTextFilter.txt";
        int maxwords = 109789;
        String[] arguments = {"3"};
        double time = 0;
        int iterations = 100;
        for(int n = 100; n < maxwords; n += 100) {
             for(int i = 0; i < iterations; i++) {
                 // change ST here
                time += time(arguments, new BST(), n, file);
             }
             time /= iterations;
             System.out.println(n + " " + time);
        }
        System.out.println("Run completed.");
    }
}
