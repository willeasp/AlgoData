package main.lab3;

import edu.princeton.cs.algs4.FrequencyCounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class BSTStopwatch {

    private static double time (String[] args, SearchTree ST, int N, String fileName) throws FileNotFoundException {
        InputStream stream = new FileInputStream(fileName);
        long start = System.nanoTime();
        FrequencyCounter.frequencyCounter(args, ST, N, stream);
        long stop = System.nanoTime();
        long time = stop - start;
        double millis = time / 1000000;
        return millis;
    }

    public static void main (String[] args) throws FileNotFoundException {
        String file = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Searching\\filter.txt";
        int maxwords = 109789;
        String[] arguments = {"3"};
        double time = 0;
        int iterations = 100;
        for(int n = 100; n < maxwords; n += 100) {
             for(int i = 0; i < iterations; i++) {
                time += time(arguments, new BST(), n, file);
             }
             time /= iterations;
             System.out.println(n + " " + time);
        }
        System.out.println("Run completed.");

    }
}
