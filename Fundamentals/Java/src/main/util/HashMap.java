/*
    Author: William Asp
    October 2, 2020

    A regular hashmap implementation that supports
    resizeing.
 */

package main.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A HashMap implementation for lab 3
 * @param <Key>
 * @param <Value>
 */
public class HashMap<Key, Value> implements Iterable<Key> {
    private int n;          // number of elements
    private int capacity;          // number of buckets in hashmap
    private LinkedList<Node>[] buckets;
    double MAX_LOAD = 4.0;

    // a node
    private class Node {
        public Key key;
        public Value val;

        public Node (Key key, Value val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * Creates a HashMap with size 16.
     */
    public HashMap () {
        this(16);
    }

    /**
     * Initiate hashmap with capacity
     * @param capacity
     */
    public HashMap (int capacity) {
        this.buckets = new LinkedList[capacity];
        this.n = 0;
        this.capacity = capacity;
    }

    private int hash(Key key, int capacity) {
        return 0x7fffffff & (key.hashCode() % capacity);
    }

    /**
     * Put key-value pair in hashmap
     * @param key
     * @param val
     */
    public void put( Key key, Value val ) {
        int index = hash(key, this.capacity);
        if(buckets[index] == null) buckets[index] = new LinkedList<>();
        for(Node node : buckets[index]) {
            if(node.key.equals(key)) {
                node.val = val;
                return;
            }
        }
        buckets[index].add(new Node(key, val));
        n++;
        if (loadFactor() > MAX_LOAD) {
            resize(this.capacity * 2);
        }
    }

    private void resize (int newCapacity) {
        //System.out.println("Resizeing HashMap ... Old capacity is: " + this.capacity);
        LinkedList<Node>[] newBuckets = new LinkedList[newCapacity];
        int index;
        for( LinkedList<Node> list : buckets ) {
            for (Node node : list) {
                index = hash(node.key, newCapacity);
                if(newBuckets[index] == null) newBuckets[index] = new LinkedList<>();
                newBuckets[index].add(node);
            }
        }
        buckets = newBuckets;
        capacity = newCapacity;
        //System.out.println("Resizeing done ... New size is: " + newCapacity);
    }

    private double loadFactor() {
        return (double) n / this.capacity;
    }

    /**
     * Get value from key in hashmap
     * @param key
     * @return Value
     */
    public Value get (Key key) {
        int index = hash(key, this.capacity);
        if( buckets[index] == null) return null;
        for(Node node : buckets[index]) {
            if(node.key.equals(key)) return node.val;
        }
        return null;
    }

    /**
     * @param key
     * @return true if the hashmap contains the given key
     */
    public boolean contains (Key key) {
        return this.get(key) != null;
    }

    /**
     *
     * @return the number of elements in hashmap
     */
    public int size() { return this.n; };

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(LinkedList<Node> list : buckets) {
            try {
                for(Node node : list) {
                    sb.append(node.key + " " + node.val + " ");
                }
            } catch (Exception e) {

            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public Iterator iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator<Key> implements Iterator<Key> {
        private int index;
        private Iterator<Node> listIterator;

        public HashMapIterator () {
            this.index = 0;
            listIterator = buckets[index].iterator();
        }

        @Override
        public boolean hasNext() {
            if(!listIterator.hasNext()) {
                index++;
                listIterator = null;
            }
            return index < buckets.length;
        }

        @Override
        public Key next() {
            if(listIterator == null) listIterator = buckets[index].iterator();
            if(!hasNext()) throw new NoSuchElementException();
            Node node = listIterator.next();
            Key key = (Key) node.key;
            return key;
        }
    }

    // tests
    public static void main (String[] args) {
        HashMap<Integer, String> hashMap = new HashMap(2);
        hashMap.put(5, "cool");
        System.out.println(hashMap.toString());
        hashMap.put(2, "bröd");
        System.out.println(hashMap.toString());
        hashMap.put(4, "nice");
        System.out.println(hashMap.toString());
        hashMap.put(3, "lalla");
        System.out.println(hashMap.toString());
        hashMap.put(0, "dubbel");
        System.out.println(hashMap.toString());
        hashMap.put(6, "blomma");
        System.out.println(hashMap.toString());
        hashMap.put(7, "ris");
        System.out.println(hashMap.toString());
        hashMap.put(9, "lampa");
        System.out.println(hashMap.toString());
        hashMap.put(11, "grej");
        System.out.println(hashMap.toString());
        System.out.println(hashMap.get(9));
        System.out.println(hashMap.get(10));
        System.out.println(hashMap.get(6));
        hashMap.get(200000);
        for(Integer i : hashMap) {
            System.out.println(i + " " + hashMap.get(i));
        }
    }
}
