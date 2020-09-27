package main.lab3;


import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BinarySearchST <Key extends Comparable<Key>, Value> implements SearchTree<Key, Value>, Iterable<Key>{
    private static final int INIT_CAPACITY = 2;
    private Key[] keys;
    private Value[] vals;
    private int n = 0;

    /**
     * Initializes an empty symbol table.
     */
    public BinarySearchST() {
        this(INIT_CAPACITY);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     * @param capacity the maximum capacity
     */
    public BinarySearchST ( int capacity ) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    // resize the underlying arrays
    private void resize(int capacity) {
        assert capacity >= n;
        Key[]   tempk = (Key[])   new Comparable[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            tempk[i] = keys[i];
            tempv[i] = vals[i];
        }
        vals = tempv;
        keys = tempk;
    }

    /**
     * @return the number of key-value pairs in this symbol table
     */
    public int size() { return n; }

    /**
     * @return true if this symbol table is empty
     */
    public boolean isEmpty() { return size() == 0; }

    /**
     * Returns the value associated with the given key in this symbol table.
     * @param  key the key
     * @return the keys value, or if the search table is empty return null
     * @throws IllegalArgumentException if the key is null
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < n && keys[i].compareTo(key) == 0) return vals[i];
        return null;
    }

    /**
     * Returns the "index" of the key in the symbol table
     * @param  key the key
     * @return the number of keys in the symbol table strictly less than the key
     * @throws IllegalArgumentException if the key is null
     */
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");

        int lo = 0, hi = n-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if      (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if the key is null
     */
    public void put(Key key, Value val)  {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        int i = rank(key);

        // key is already in table
        if (i < n && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }

        // insert new key-value pair
        if (n == keys.length) resize(2*keys.length);

        for (int j = n; j > i; j--)  {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key;
        vals[i] = val;
        n++;

        assert check();
    }

    /**
     * Does this symbol table contain the given key?
     * @param  key the key
     * @return true if this symbol table contains key and
     *         false otherwise
     * @throws IllegalArgumentException if key is null
     */
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    /**
     * Return the kth smallest key in this symbol table.
     * @param  k the "index" of the Key to return
     * @return the kth smallest key in this symbol table
     * @throws IllegalArgumentException unless k is between 0 and n-1
     */
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }
        return keys[k];
    }

    @Override
    public Iterator<Key> iterator() {
        return new BinarySearchSTIterator();
    }

    private class BinarySearchSTIterator implements Iterator<Key> {
        private int index;

        public BinarySearchSTIterator() {
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Key next() {
            return keys[index++];
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size(); i++) {
            sb.append(keys[i] + " : " + vals[i] + "\n");
        }
        return sb.toString();
    }

    /****************************************************************************
        Testing methods
     ****************************************************************************/

    private boolean check() {
        return isSorted() && rankCheck();
    }

    // are the items in the array in ascending order?
    private boolean isSorted() {
        for (int i = 1; i < size(); i++)
            if (keys[i].compareTo(keys[i-1]) < 0) return false;
        return true;
    }

    // check that rank(select(i)) = i
    private boolean rankCheck() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (int i = 0; i < size(); i++)
            if (keys[i].compareTo(select(rank(keys[i]))) != 0) return false;
        return true;
    }

    /**
     * Unit tests the BinarySearchST data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        int i = 0;
        while (sc.hasNext()) {
            String key = sc.next();
            st.put(key, i++);
        }
        System.out.println(st.toString());
    }


}
