package main.lab2;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Assignment1<T extends Comparable> {
    // insertionsort

    public Assignment1() {}

    /**
     * Sort an array using Insertionsort
     * @param a the array to sort
     */
    public void sort (T[] a) {
        int i, j;
        for(i = 1; i < a.length; i++) {
            for(j = i; j > 0 && less( a[j], a[j-1] ); j--) {
                swap(a, j, j-1);
                System.out.println(Arrays.toString(a));
            }
        }
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

    public static void main (String[] args) {
        Assignment1 insertionsort = new Assignment1();
        Integer[] array = new Integer[0];

        if (args.length > 0) {
            if (args[0].equals("-r")) {
                int N = Integer.parseInt(args[1]);

                // generate random array
                array = new Integer[N];
                Random rd = new Random();
                for (int i = 0; i < N; i++) {
                    array[i] = Math.abs(rd.nextInt()) % 20;
                }
                System.out.println(Arrays.toString(array));
            }
        } else {
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
        // do sorting
        System.out.println("Before:");
        System.out.println(Arrays.toString(array));
        System.out.println("Sorting:");
        insertionsort.sort(array);
        System.out.println("After:");
        System.out.println(Arrays.toString(array));
    }
}
