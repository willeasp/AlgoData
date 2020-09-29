/*
    Author: William Asp
    September 28, 2020

    **What it is**
        A binary searchtree implemented as a linked tree of nodes with
        each node referencing two children. Each node is bigger than
        its left relative node and smaller than its right relative node.

    **How it is used**
        Run the program and enter words that will be used as keys.
        The value for each word will be the number of words in the ST
        when the key was added.

    **Testing**
        Run the file with the -ea flag.
 */

package main.lab3;

import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.Scanner;

public class BST<Key extends Comparable<Key>, Value> implements Iterable<Key>, SearchTree<Key, Value>{
    private Node root;

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BST() {
    }

    /**
     * Returns true if this symbol table is empty.
     * @return true if the symbol table is empty
     */
    public boolean isEmpty() { return size() == 0; }

    /**
     * @return the number of key-value pairs in this symbol table
     */
    public int size() { return size(root); }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table otherwise null
     * @throws IllegalArgumentException if the key is null
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {          // if the key is to be deleted
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Removes the specified key and its associated value from this symbol table
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    private void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = delete(x.left,  key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left  == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * @param x start Node
     * @return The smallest reachable node from x
     */
    private Node min(Node x) {
        if (x.left == null) return x;
        else                return min(x.left);
    }

    /**
     * Removes the smalles key and associated value from the subtree under x
     * @param x the root of the subtree that the min key should be removed from
     * @return the subtree when Node x is removed
     */
    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Key s : this) {
            sb.append(s + " : " + this.get(s) + "\n");
        }
        return sb.toString();
    }

    /**
     * @return An iterator for this class
     */
    @Override
    public Iterator<Key> iterator() {
        return new BSTIterator(root);
    }

    /**
     * Iterate from smallest to biggest
     */
    private class BSTIterator implements Iterator<Key>{
        private Node node;
        private Stack<Node> stack;

        public BSTIterator( Node root ) {
            stack = new Stack<>();
            node = root;
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Key next() {
            node = stack.pop();
            Key result = node.key;
            if(node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return result;
        }
    }

    /****************************************************************
        Test functions
    **************************************************************/
    private boolean check() {
        if (!isBST())            System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
        return isBST() && isSizeConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    /**
     * Unit tests the BST data type.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BST<String, Integer> st = new BST<String, Integer>();
        int i = 0;
        while (sc.hasNext()) {
            String key = sc.next();
            st.put(key, i++);
        }
        System.out.println(st.toString());
    }
}
