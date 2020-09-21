package main.lab2;

import main.lab2.InsertionSort;
import main.lab2.MergeSort;

import java.util.Random;

public class AlgorithmTimeTest {
    MergeSort m = new MergeSort();
    InsertionSort i = new InsertionSort();

    public long msort ( int[] array ) {
        long start = System.nanoTime();
        m.sort(array);
        long stop = System.nanoTime();
        return stop - start;
    }

    public long isort ( int[] array ) {
        long start = System.nanoTime();
        i.cleanSort(array, 0, array.length -1);
        long stop = System.nanoTime();
        return stop - start;
    }

    public int[] randomArray (int N) {
        int[] a = new int[N];
        Random rd = new Random();
        for (int i = 0; i < N; i++) {
            a[i] = rd.nextInt(1000000);
        }
        return a;
    }

    public static void main (String[] args) {
        singleTest();
    }

    private static void singleTest (){
        AlgorithmTimeTest t = new AlgorithmTimeTest();
        int[] array = t.randomArray(70);
        long insertionSortTime = t.isort(array);
        long mergeSortTime = t.msort(array);
        double ratio = (double) insertionSortTime / mergeSortTime;
        System.out.println(insertionSortTime);
        System.out.println(mergeSortTime);
        System.out.println(ratio);
    }
    private static void try1() {
        AlgorithmTimeTest t = new AlgorithmTimeTest();
        int[] array = t.randomArray(200);
        long insertionSortTime = 0;
        long mergeSortTime = 0;
        int sortingTimes = 20;
        for (int i = 0; i < sortingTimes; i++) {
            insertionSortTime += t.isort(array);
            mergeSortTime += t.msort(array);
        }
        insertionSortTime /= sortingTimes;
        mergeSortTime /= sortingTimes;
        System.out.println(insertionSortTime);
        System.out.println(mergeSortTime);
        double avgRatio = (double) insertionSortTime / mergeSortTime;
        System.out.println(avgRatio);
    }

    private static void avgRatio() {
        AlgorithmTimeTest t = new AlgorithmTimeTest();
        int[] array;
        long insertionSortTime = 0;
        long mergeSortTime = 0;
        double avgRatio = 0;
        int avgAdditions = 10;
        int sortingTimes = 20;
        for(int j = 0; j < avgAdditions; j++){
            for (int i = 0; i < sortingTimes; i++) {
                array = t.randomArray(50);
                insertionSortTime += t.isort(array);
                mergeSortTime += t.msort(array);
            }
            insertionSortTime /= sortingTimes;
            mergeSortTime /= sortingTimes;
            System.out.println(insertionSortTime);
            System.out.println(mergeSortTime);
            avgRatio += (double) insertionSortTime / mergeSortTime;
            System.out.println((double) insertionSortTime / mergeSortTime);
        }
        avgRatio /= avgAdditions;
        System.out.println(avgRatio);
    }


}
