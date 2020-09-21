/*
    Assignment 1, 2, 3

    Author: William Asp
    September 15, 2020
        Updates:
        September 16, 2020
            - Added cleanSort for use in Mergesort, so that nothing
              is printed.

    **What it is**
        Insertion sort. The class contains methods that solves different problems.
        The sort method prints its contents after each iteration, and prints the number of
        swaps performed.
        The method countInversions prints the elements that are in the wrong order.
        The method cleanSort is meant to be fast, without any print statements, and
        is meant to be used with mergesort, so one can send and array with a partition
        to be sorted.


    **How it works**
        To run the sorting from main, simply execute the program and you will be prompted
        to enter the amount of values to be sorted and then enter all values. This
        can also be done with piping a file to stdin. The output will be what inversions
        there are and what swaps are performed.

        Options:

        -f  Fast mode. Run the insertionsort without any printstatements,

        -r <N>  Random. Generate a random list of size N.

        -h  Descending order. Sort the array in descending order.

    **Testing**
        To run tests, enter -ea as VM Options. -ea means enable assertions.


 */

package main.lab2;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * A class for sorting Comparable arrays using insertionsort.
 */
public class InsertionSort {
    // insertionsort

    public InsertionSort() {}

    /**
     * Sort an array using Insertionsort
     * @param a the array to sort
     */
    public void sort (Comparable[] a) {
        int count = 0;
        for(int i = 1; i < a.length; i++) {
            for(int j = i; j > 0 && less( a[j], a[j-1] ); j--) {
                swap(a, j, j-1);
                System.out.println(Arrays.toString(a));
                count++;
            }
        }
        assert isSorted(a);
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
        assert isSorted(a, lo, hi);
    }

    /**
     *
     * @param a first element
     * @param b second element
     * @return true if a is less than b
     */
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
    private boolean less (Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    /**
     * swap two elements in an array
     * @param a array containing elements to swap
     * @param j index of first element to swap
     * @param k index of second element to swap
     */
    private void swap (Comparable[] a, int j, int k) {
        Comparable tmp = a[j];
        a[j] = a[k];
        a[k] = tmp;
    }

    /**
     * Prints a string containing the inversions of array a.
     * the string is formatted as [i, a[i]], [j, a[j]]
     * @param a
     */
    public void countInversions (Comparable[] a) {
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

    /**
     * Sort the array a in descending order
     * @param a array to be sorted.
     */
    public void descendingOrder (Integer[] a) {
        for(int i = 0; i < a.length; i++) {
            a[i] *= -1;
        }
        this.sort(a);
        assert isSorted(a);
        for(int i = 0; i < a.length; i++) {
            a[i] *= -1;
        }
    }

    /**
     * Check that the array is sorted, for assertions
     * @param a array to check.
     * @return true if the array is sorted
     */
    private boolean isSorted (Comparable[] a) {
        for (int i = 0 + 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    /**
     * check that the array is sorted from index lo to index hi
     * @param a array to check
     * @param lo low index
     * @param hi hi index
     * @return true if a[lo..hi] is sorted
     */
    private boolean isSorted (int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
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
            } else if (args[0].equals("-h")) {
                // input from user
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter size of input:");
                int N = sc.nextInt();
                array = new Integer[N];
                System.out.println("Enter " + N + " numbers.");
                for(int i = 0; i < N; i++) {
                    array[i] = sc.nextInt();
                }
                insertionsort.descendingOrder(array);
                System.out.println(Arrays.toString(array));
                System.exit(0);
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
