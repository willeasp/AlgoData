/*

    Author: William Asp
    September 17, 2020

    **What it is**
        Mergesort with ability to use cutoff to insertionsort.

    **How it works**
        Run program and first enter the amount of numbers to sort.
        Then enter that same number of integers to sort.

        Options:
            -c  <Cutoff>    Set the cutoff to insertionsort. Defaults to 0.

    **Testing**
        Enter -ea as VM Option. -ea means enable assertions.

 */

package main.lab2;

import java.util.Arrays;
import java.util.Scanner;

public class MergeSort<T extends Comparable> {
    private int cutoff = 0;
    private InsertionSort insertionSort = new InsertionSort();

    public void setCutoff (int cutoff) {
        this.cutoff = cutoff;
    }

    /**
     * Merges the array partitions a[lo ... mid] with a[mid+1 ... hi]
     * @param a the array with the input and output
     * @param aux help array
     * @param lo start index of first partition
     * @param mid end index of first partition
     * @param hi end index of first partition
     */
    private void merge (int[] a, int[] aux, int lo, int mid, int hi) {
        // initiate indexes for both partitions to merge
        int i = lo;
        int j = mid +1;

        // merge both partitions, by taking the smallest element from each until finished
        for(int k = lo; k <= hi; k++) {
            if (i > mid)                aux[k] = a[j++];
            else if (j > hi)            aux[k] = a[i++];
            else if (a[i] < a[j])   aux[k] = a[i++];
            else                        aux[k] = a[j++];
        }

        // copy aux to a
        for(int k = lo; k <= hi; k++) {
            a[k] = aux[k];
        }
        assert isSorted(a, lo, hi);
    }

    /**
     * returns the middle index between hi and lo
     */
    private int mid (int lo, int hi) {
        return lo + (hi - lo) / 2;
    }

    /**
     * Recursive sorting function.
     * @param a destination array
     * @param aux help array
     * @param lo lower index to sort from
     * @param hi higher index to sort to
     */
    private void sort (int[] a, int[] aux, int lo, int hi) {
        if (hi - lo <= cutoff) {
            insertionSort.cleanSort(a, lo, hi);
            return;
        }
        int mid = mid(lo, hi);
        sort(a, aux, lo, mid);
        sort(a, aux, mid +1, hi);
        merge(a, aux, lo, mid, hi);
        assert isSorted(a, lo, hi);
    }

    /**
     * MergeSort array a
     * @param a array to sort
     */
    public void sort (int[] a) {
        int[] aux = new int[a.length];
        int hi = a.length - 1;
        int lo = 0;
        sort(a, aux, lo, hi);
        assert isSorted(a);
    }

    /**
     * check that the full array a is sorted
     * @param a array
     * @return true if a is sorted.
     */
    private boolean isSorted (int[] a) {
        return isSorted(a, 0, a.length-1);
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

    /**
     * help function to isSorted
     * @param a first element
     * @param b second element
     * @return true if a is less than b
     */
    private boolean less(int a, int b) {
        return a < b;
    }

    public static void main (String[] args) {
        MergeSort merge = new MergeSort();
        if(args.length > 0) {
            if(args[0].equals("-c")) {
                int cutoff = Integer.parseInt(args[1]);
                merge.setCutoff(cutoff);
            }
        }
        // enter numbers
        // input from user
        Scanner sc = new Scanner(System.in);
        //System.out.println("Enter size of input:");
        int N = sc.nextInt();
        int[] array = new int[N];
        //System.out.println("Enter " + N + " numbers.");
        for(int i = 0; i < N; i++) {
            array[i] = sc.nextInt();
        }

        //System.out.println(Arrays.toString(array));
        merge.sort(array);
        //System.out.println(Arrays.toString(array));
    }
}
