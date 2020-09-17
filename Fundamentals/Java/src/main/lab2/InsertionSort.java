/*
    Assignment 1, 2, 3

    Author: William Asp
    September 15, 2020
        Updates:
        September 16, 2020
            - Added cleanSort for use in Mergesort, so that nothing
              is printed.

    **What it is**

    **How it works**

    **Testing**


 */

package main.lab2;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * A class for sorting Comparable arrays using insertionsort.
 * @param <T> A comparable item.
 */
public class InsertionSort<T extends Comparable> {
    // insertionsort

    public InsertionSort() {}

    /**
     * Sort an array using Insertionsort
     * @param a the array to sort
     */
    public void sort (T[] a) {
        int count = 0;
        for(int i = 1; i < a.length; i++) {
            for(int j = i; j > 0 && less( a[j], a[j-1] ); j--) {
                swap(a, j, j-1);
                System.out.println(Arrays.toString(a));
                count++;
            }
        }
        System.out.println("Swaps: " + count);
    }

    /**
     * Sort an array using Insertionsort without any print statements
     * @param a the array to sort
     */
    public void cleanSort (int[] a, int lo, int hi) {
        for(int i = lo + 1; i <= hi; i++) {
            for(int j = i; j > lo && less( a[j], a[j-1] ); j--) {
                swap(a, j, j-1);
            }
        }
    }

    private boolean less(int a, int b) {
        return a < b;
    }

    /**
     * swaps a[j] and a[k]
     * @param a
     * @param j
     * @param k
     */
    private void swap (int[] a, int j, int k) {
        int tmp = a[j];
        a[j] = a[k];
        a[k] = tmp;
    }

    /**
     * returns true if a < b
     * @param a first comparable
     * @param b second comparable
     * @return true if a is less than b
     */
    private boolean less (T a, T b) {
        return a.compareTo(b) < 0;
    }

    /**
     * swap two elements in an array
     * @param a array containing elements to swap
     * @param j index of first element to swap
     * @param k index of second element to swap
     */
    private void swap (T[] a, int j, int k) {
        T tmp = a[j];
        a[j] = a[k];
        a[k] = tmp;
    }

    /**
     * Prints a string containing the inversions of array a.
     * the string is formatted as [i, a[i]], [j, a[j]]
     * @param a
     */
    public void countInversions (T[] a) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < a.length; i++) {
            for(int j = i+1; j < a.length; j++) {
                if(a[i].compareTo(a[j]) > 0)
                    sb.append("[" + i + ", " + a[i] + "], " +
                              "[" + j + ", " + a[j] + "]\n");
            }
        }
        System.out.println(sb.toString());
    }

    public static void main (String[] args) {
        InsertionSort insertionsort = new InsertionSort();
        Integer[] array = new Integer[0];

        if (args.length > 0) {
            if (args[0].equals("-f")) {      // fast
                // input from user
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter size of input:");
                int N = sc.nextInt();
                int[] a = new int[N];
                System.out.println("Enter " + N + " numbers.");
                for(int i = 0; i < N; i++) {
                    a[i] = sc.nextInt();
                }
                insertionsort.cleanSort(a, 0, a.length-1);
                //System.out.println(Arrays.toString(a));
                System.exit(0);

            } else if (args[0].equals("-r")) {      // random
                int N = Integer.parseInt(args[1]);  // number of random numbers

                // generate random array
                array = new Integer[N];
                Random rd = new Random();
                for (int i = 0; i < N; i++) {
                    array[i] = rd.nextInt(50);
                }
                System.out.println(Arrays.toString(array));
            }
        }  else {
            // input from user
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter size of input:");
            int N = sc.nextInt();
            array = new Integer[N];
            System.out.println("Enter " + N + " numbers.");
            for(int i = 0; i < N; i++) {
                array[i] = sc.nextInt();
            }
        }

        // count inversion
        insertionsort.countInversions(array);

        // do sorting
        System.out.println("Before:");
        System.out.println(Arrays.toString(array));
        System.out.println("Sorting:");
        insertionsort.sort(array);
        System.out.println("After:");
        System.out.println(Arrays.toString(array));
    }
}
