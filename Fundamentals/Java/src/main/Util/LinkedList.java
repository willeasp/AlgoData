package main.Util;

import java.util.Iterator;

public class LinkedList<Item> implements Iterable{
    private Node first;
    private Node last;
    private int size;

    private class Node {
        public Item item;
        public Node next;

        public Node (Item item) {
            this.item = item;
            this.next = null;
        }
    }

    public LinkedList() {
        this.first = null;
        this.last = first;
    }

    public void add (Item item) {
        Node newNode = new Node(item);
        this.last.next = newNode;
        this.last = newNode;
        if(first == null) first = last;
    }

    public int size() {
        return this.size;
    }

    @Override
    public Iterator iterator() {
        return new LinkedListIterator(this.first);
    }

    private class LinkedListIterator implements Iterator {
        private Node current;

        public LinkedListIterator(Node first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Object next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}
