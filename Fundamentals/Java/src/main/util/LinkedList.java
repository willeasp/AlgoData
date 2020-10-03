package main.util;

import java.util.Iterator;

/**
 * A Linked list for use in lab 3.
 * @param <Item>
 */
public class LinkedList<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    // node
    private class Node {
        public Item item;
        public Node next;

        public Node (Item item) {
            this.item = item;
            this.next = null;
        }
    }

    // constructor
    public LinkedList() {
        this.first = null;
        this.last = first;
    }

    // add element to the last place in list
    public void add (Item item) {
        Node newNode = new Node(item);
        if (this.last == null); // just make last newNode
        else
            this.last.next = newNode;
        this.last = newNode;
        if(first == null) first = last;
    }

    /**
     *
     * @return number of elements in list
     */
    public int size() {
        return this.size;
    }

    /**
     *
     * @return Iterator for this class
     */
    @Override
    public Iterator<Item> iterator() {
        return new LinkedListIterator(this.first);
    }

    private class LinkedListIterator implements Iterator<Item> {
        private Node current;

        public LinkedListIterator(Node first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}
