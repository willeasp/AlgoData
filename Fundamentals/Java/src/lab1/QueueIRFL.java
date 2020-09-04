/*
* Author: William Asp
*
* QueueInsertRemoveFirstLast
*
* */

package lab1;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class QueueIRFL<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int n;

    private class Node {
        private Item item;
        private Node next;
    }

    public QueueIRFL() {
        this.first = null;
        this.last = null;
        this.n = 0;
    }

    public int size() { return this.n; }

    private void increaseSize() { this.n++; }
    private void decreaseSize() { this.n--; }

    public void enqueue (Item item) {
        last = insert(item);
        increaseSize();
        printContents();
    }

    public void insertFirst (Item item) {
        first = insert(item);
        increaseSize();
        printContents();
    }

    private Node insert (Item item) {
        Node newNode = new Node();      // create a new node
        newNode.item = item;            // store information
        if (isEmpty()) {            // if there are no elements
            first = newNode;
            last = newNode;
        }
        last.next = newNode;
        newNode.next = first;
        return newNode;
    }

    public Item dequeue() {
        if (size() == 0) throw new NoSuchElementException("Queue is empty");
        Item item = first.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
        }
        decreaseSize();
        printContents();
        return item;
    }

    public void removeFirst() {
        dequeue();
    }

    public void removeLast() {
        if (size() == 0) throw new NoSuchElementException("Queue is empty");
        if (first == last) {
            first = null;
            last = null;
        } else {
            Node n = first;
            while (n.next != last) {
                n = n.next;
            }
            n.next = first;
            last = n;
        }
        decreaseSize();
        printContents();
    }

    /**
     * Print the contents of the queue
     */
    private void printContents() {
        StringBuilder sb = new StringBuilder(); // use a stringbuilder
        sb.append("Queue:");                   // make clear that the printout is for the queue
        for(Item item : this) {                 // use the iterator
            sb.append("  " + item);             // append each item
        }
        System.out.println(sb.toString());      // print to stdout
    }

    private boolean isEmpty() { return n == 0;  }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator(first);
    }

    private class QueueIterator implements Iterator<Item> {
        private Node current;

        public QueueIterator (Node first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            if (current == null) {              // if current is set to null, there are no more elements
                return false;
            }
            return true;
        }

        @Override
        public Item next() {
            Item item = current.item;
            if (current == last)
                current = null;
            else
                current = current.next;
            return item;
        }
    }

    public static void main (String[] args) {
        QueueIRFL<String> q = new QueueIRFL();
        Scanner sc = new Scanner(System.in);
        String input = null;
        while (true) {
            input = sc.next();
            if (input.equals("-"))
                q.dequeue();
            else if (input.equals("!"))
                q.removeFirst();
            else if (input.equals("_"))
                q.removeLast();
            else if (input.equals("?"))
                System.out.println("Size is: " + q.size());
            else
                q.enqueue(input);
        }
    }
}
