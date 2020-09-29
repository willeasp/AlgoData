package main.lab3;

import main.Util.LinkedList;

/**
 * A HashMap implementation for lab 3
 * @param <Key>
 * @param <Value>
 */
public class HashMap<Key, Value> {
    private int n;          // number of elements
    private int capacity;          // number of buckets in hashmap
    private LinkedList<Node>[] buckets;

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
     * Initiate hashmap with capacity
     * @param capacity
     */
    public HashMap (int capacity) {
        this.buckets = new LinkedList[capacity];
        this.n = 0;
        this.capacity = capacity;
    }

    private int hash(Key key) {
        return Math.abs(key.hashCode() % capacity);
    }

    /**
     * Put key-value pair in hashmap
     * @param key
     * @param val
     */
    public void put( Key key, Value val ) {
        int index = hash(key);
        if(buckets[index] == null) buckets[index] = new LinkedList<>();
        for(Node node : buckets[index]) {
            if(node.key.equals(key)) {
                node.val = val;
                return;
            }
        }
        buckets[index].add(new Node(key, val));
    }

    /**
     * Get value from key in hashmap
     * @param key
     * @return Value
     */
    public Value get (Key key) {
        int index = hash(key);
        if( buckets[index] == null) return null;
        for(Node node : buckets[index]) {
            if(node.key.equals(key)) return node.val;
        }
        return null;
    }

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

    // tests
    public static void main (String[] args) {
        HashMap<Integer, String> hashMap = new HashMap(4);
        hashMap.put(5, "cool");
        hashMap.put(2, "bröd");
        hashMap.put(4, "nice");
        hashMap.put(3, "lalla");
        hashMap.put(0, "dubbel");
        hashMap.put(6, "blomma");
        hashMap.put(7, "ris");
        hashMap.put(9, "lampa");
        System.out.println(hashMap.toString());
        System.out.println(hashMap.get(9));
        System.out.println(hashMap.get(10));
        System.out.println(hashMap.get(6));
        hashMap.get(200000);
    }


}
