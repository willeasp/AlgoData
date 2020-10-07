/*
    Author: William Asp
    October 7, 2020

    **What it is**
        A priority queue implemented as a binary heap
        Used in dijkstra.

    **How it works**
        Run the program to test adding strings to the queue
 */

package main.lab4;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A Priority queue implemented as a binary heap
 * @param <Key>
 */
public class IndexMinPQ<Key extends Comparable> implements Iterable<Integer>{
    private int maxN;           // max number of elements in pq
    private int n;              // number of elements in priority queue
    private int[] pq;           // pq[0] holds the most prioritized index
    private int[] qp;           // pq[n] holds index n's place in pq
    private Key[] keys;         // keys[n] holds n's associated key

    /**
     * Initializes an empty indexed priority queue with indices
     * between 0 and maxN-1
     * @param maxN the keys on the queue are index from 0 and maxN-1
     */
    public IndexMinPQ(int maxN) {
        if(maxN < 0) throw new IllegalArgumentException("PriorityQueue cannot be negative size");
        this.maxN = maxN;
        n = 0;
        this.pq = new int[maxN +1];
        this.qp = new int[maxN +1];
        this.keys = (Key[]) new Comparable[maxN +1];
        for(int i = 0; i < maxN; i++)
            qp[i] = -1;                 // element at qp[i] does not exist
    }

    /**
     *
     * @return True if there are no elements in the queue
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     *
     * @param i Index
     * @return true if index i is on the priority queue
     */
    public boolean contains(int i) {
        validateIndex(i);
        return qp[i] != -1;
    }

    /**
     *
     * @return The number of keys on the priority queue
     */
    public int size() {
        return this.n;
    }

    /**
     * Associate key with index i and places it in the priority queue
     * @param i index to insert
     * @param key key to associate with index i;
     */
    public void insert(int i, Key key) {
        validateIndex(i);
        if(contains(i)) throw new IllegalArgumentException("Index " + i + " is already on the priority queue");
        n++;            // last place of the queue
        qp[i] = n;      // the index i is at the last place of the queue
        pq[n] = i;      // at the last place of the queue is index i
        keys[i] = key;
        swim(n);        // move the last element of the pq to its right position
    }

    /**
     *
     * @return The first index in priority queue
     */
    public int minIndex() {
        if(n == 0) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    /**
     *
     * @return The first key in the priority queue
     */
    public Key minKey() {
        if(n == 0) throw new NoSuchElementException("Priority queue underflow");
        return keys[pq[1]];
    }

    /**
     * Removes the minimum key and returns its associated index
     * @return An index associated with a minimum key
     */
    public int delMin() {
        if(n == 0) throw new NoSuchElementException("Priority queue underflow");
        int min = pq[1];
        swap(1, n--);
        sink(1);
        assert min == pq[n+1];
        qp[min] = -1;
        keys[min] = null;
        pq[n+1] = -1;
        return min;
    }

    /**
     * Return key associated with index i
     * @param i index of the key to return
     * @return The key associated with index i
     */
    public Key keyOf(int i) {
        validateIndex(i);
        if(!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        else return keys[i];
    }

    /**
     * Change the key associated with index i to key
     * @param i the index of the key to change
     * @param key the key to replace the old key
     */
    public void changeKey(int i, Key key) {
        validateIndex(i);
        if(!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        keys[i] = key;
        swim(qp[i]);
        sink(qp[i]);
    }

    /**
     * Decrease the key associated with index i to the new key
     * @param i index of key to decrease
     * @param key key to change
     */
    public void decreaseKey(int i, Key key) {
        validateIndex(i);
        if(!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if(keys[i].compareTo(key) == 0)
            throw new IllegalArgumentException("Calling decreaseKey() with key equal to the already existing key");
        if(keys[i].compareTo(key) < 0)
            throw new IllegalArgumentException("Calling decreaseKey() with a key greater than the already axisting key");
        keys[i] = key;
        swim(qp[i]);
    }

    /**
     * Increase the key associated with index i to the new key
     * @param i index of key to increase
     * @param key key to change
     */
    public void increaseKey(int i, Key key) {
        validateIndex(i);
        if(!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        if(keys[i].compareTo(key) == 0)
            throw new IllegalArgumentException("Calling increaseKey() with key equal to the already existing key");
        if(keys[i].compareTo(key) > 0)
            throw new IllegalArgumentException("Calling increaseKey() with a key smaller than the already axisting key");
        keys[i] = key;
        sink(qp[i]);
    }

    /**
     * Remove the key associated with index i
     * @param i the index of the key to remove
     */
    public void delete(int i) {
        validateIndex(i);
        if(!contains(i)) throw new NoSuchElementException("index is not in the priority queue");
        int index = qp[i];
        swap(index, n--);
        swim(index);
        sink(index);
        keys[i] = null;
        qp[i] = -1;
    }

    public void validateIndex(int i) {
        if(i < 0) throw new IllegalArgumentException("index is negative: " + i);
        if(i >= maxN) throw new IllegalArgumentException("index >= capacity: " + i);
    }

    /***************************************************************
     * General helper functions
     *****************************************************************/

    // return true if key at pq[i] is greater than the key at pq[j]
    private boolean greater(int i, int j) {
        return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    // swap pq[i] with pq[j]
    private void swap(int i, int j) {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        qp[pq[i]] = i;      // the index at pq[i] is at place i in pq
        qp[pq[j]] = j;
    }

    /**************************************************************
     * Heap helper functions
     ***************************************************************/

    // move Key at pq[k] upwards in the heap
    private void swim(int k) {
        while(k > 1 && greater(k/2, k)) {
            swap(k, k/2);       // move pq[k] up to pq[k/2]
            k = k/2;
        }
    }

    // move Key at pq[k] downwards in the heap
    private void sink(int k) {
        while(2*k <= n) {
            int j = 2*k;    // j is at the left place under pq[k]
            if(j < n && greater(j, j+1)) j++;
            if(!greater(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    /***********************************************************
     * Iterator
     ***********************************************************/

    /**
     * Returns an iterator that iterates over the keys in ascending order
     * @return an iterator that iterates over the keys in ascending order
     */
    public Iterator<Integer> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Integer> {
        // a copy of the heap
        private IndexMinPQ<Key> copy;

        // add all elements in this heap to copy heap
        public HeapIterator() {
            copy = new IndexMinPQ<Key>(pq.length -1);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i], keys[pq[i]]);
        }

        @Override
        public boolean hasNext() { return !copy.isEmpty(); }

        @Override
        public Integer next() {
            if(!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }

    /**
     * Unit tests the heap
     * @param args
     */
    public static void main(String[] args) {
        String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };

        IndexMinPQ<String> pq = new IndexMinPQ<>(strings.length);
        for(int i = 0; i < strings.length; i++)
            pq.insert(i, strings[i]);

        while(!pq.isEmpty()) {
            int i = pq.delMin();
            System.out.println(i + " " + strings[i]);
        }
        System.out.println();

        // reinsert same strings
        for(int i = 0; i < strings.length; i++)
            pq.insert(i, strings[i]);

        for (int i : pq)
            System.out.println(i + " " + strings[i]);

        while(!pq.isEmpty()) {
            pq.delMin();
        }
    }
}

