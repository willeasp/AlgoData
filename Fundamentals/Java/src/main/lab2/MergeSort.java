package main.lab2;

import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        // initiate indexes for both "arrays" to merge
        int i = lo;
        int j = mid +1;

        // merge both "arrays", by taking the smallest element from each until finished
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
    }

    /**
     * returns the middle index between hi and lo
     */
    private int mid (int lo, int hi) {
        return lo + (hi - lo) / 2;
    }

    /**
     * Recursive sorting function.
     * @param a
     * @param aux
     * @param lo
     * @param hi
     */
    private void sort (int[] a, int[] aux, int lo, int hi) {
        if (hi - lo <= cutoff) {
            insertionSort.cleanSort(a, lo, hi);
            return;
        }
        int mid = mid(lo, hi);
        sort(a, aux, lo, mid);
        sort(a, aux, mid +1, hi);
        // before the merging, clone the aux so that the correct merging happens.
        merge(a, aux, lo, mid, hi);
    }

    public void sort (int[] a) {
        int[] aux = new int[a.length];
        int hi = a.length - 1;
        int lo = 0;
        sort(a, aux, lo, hi);
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
        System.out.println("Enter size of input:");
        int N = sc.nextInt();
        int[] array = new int[N];
        System.out.println("Enter " + N + " numbers.");
        for(int i = 0; i < N; i++) {
            array[i] = sc.nextInt();
        }

        //System.out.println(Arrays.toString(array));
        merge.sort(array);
        //System.out.println(Arrays.toString(array));
    }
}
