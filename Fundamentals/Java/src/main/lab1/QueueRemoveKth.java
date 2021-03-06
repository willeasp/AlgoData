/*  Assignment 5

Author : William Asp
September 7, 2020

Queue Remove Kth Element

**What it does**
    A generalized queue which allows the user to remove the kth
    element from the queue. The most recently added element has index 1.

** How it is used**
    To input elements to the queue, type in a string and press enter to
    enqueue.
    To dequeue, type "-". To remove an item at index k, type "#", press
    enter, then input the index of the element to remove, starting with
    index 1 at the end of the queue.

**Testing**
    To run tests, add "-t" as program arguments before running.

The program has taken inspiration from the Queue.java found at https://algs4.cs.princeton.edu/code/

 */

package main.lab1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import main.util.Testing;

/**
 * A generic iterable circular linked list which allows the user to insert
 * and remove elements to/from the front and back end of the queue.
 * @param <Item> What type the queue should hold.
 */
public class QueueRemoveKth<Item> implements Iterable<Item> {
    private Node first;         // first element in queue
    private Node last;          // last element in queue
    private int n;              // # of items in queue

    /**
     * Holds each element and points to the next node
     */
    private class Node {
        private Item item;
        private Node next;
    }

    /**
     * Class Constructor
     */
    public QueueRemoveKth() {
        this.first = null;
        this.last = null;
        this.n = 0;
    }

    /**
     * Get the number of elements in the queue.
     *
     * @return Number of elements in the queue.
     */
    public int size() {
        return this.n;
    }

    /**
     * increase or decrease the item counter.
     */
    private void increaseSize() { this.n++; }
    private void decreaseSize() { this.n--; }

    private void sortIn (Item item) {
        Node current = first;
    }

    /**
     * Add <code>item</code> to queue.
     *
     * @param item
     */
    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();      // create a new node
        last.item = item;            // store information
        last.next = null;
        if (isEmpty()) {            // if there are no elements
            first = last;            // first and last point to the same node
        } else {
            oldLast.next = last;
        }
        increaseSize();
        printContents();
    }

    /**
     * Get the first element from the queue
     *
     * @return The first element in the queue
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Item item = first.item;
        first = first.next;
        decreaseSize();
        if (isEmpty()) last = null;
        printContents();
        return item;
    }

    /**
     * Remove the Kth index from the queue, starting at index 1 at the last entered item.
     * @param k The index to remove.
     * @return The item removed
     */
    public Item removeKth (int k) {
        if (k > size() || k <= 0) {              // if index out of range
            throw new IndexOutOfBoundsException("No element at that index");
        }
        Item item = null;                       // item to remove
        Node current = first;                   // for iterating over queue
        int index = size();                     // index starting at first counting backwards

        if(k == index) {                        // if k is at the first element
            return dequeue();
        }
        while (index != (k + 1)) {              // move to the element right before k
            current = current.next;
            index--;
        }
        item = current.next.item;               // get the item to be returned
        if (current.next == last) {             // if the element to be removed is last
            last = current;                     // new last
            last.next = null;
        } else {
            current.next = current.next.next;   // hop over the element to be removed
        }
        decreaseSize();
        printContents();
        return item;
    }

    /**
     * Print the contents of the queue
     */
    private void printContents() {
        StringBuilder sb = new StringBuilder(); // use a stringbuilder
        sb.append("Queue:");                   // make clear that the printout is for the queue
        for (Item item : this) {                 // use the iterator
            sb.append("  " + item);             // append each item
        }
        System.out.println(sb.toString());      // print to stdout
    }

    /**
     * @return True if there are 0 elements in the queue
     */
    private boolean isEmpty() {
        return n == 0;
    }

    /**
     * Creates an Iterator object
     * @return The iterator for the queue
     */
    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator(first);
    }

    /**
     * Iterator for this class
     */
    private class QueueIterator implements Iterator<Item> {
        private Node current;

        public QueueIterator (Node first) {
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

    public static void main(String[] args) {
        QueueRemoveKth<String> q = new QueueRemoveKth<>();

        if (args.length > 0){
            if (args[0].equals("-t")) {
                System.out.println("TESTING...");

                Testing test = new Testing();

                // set stdout to write to outputstream
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                PrintStream oldOut = System.out;
                System.setOut(new PrintStream(output));

                // set up first test
                q.enqueue("hello");
                q.enqueue("world");
                q.enqueue("i");
                q.enqueue("am");

                // enqueue test
                output.reset();
                q.enqueue("back");
                String expected = "Queue:  hello  world  i  am  back";
                String result = output.toString().trim();
                test.testResult(expected, result, "enqueue", "");

                // dequeue test
                output.reset();
                expected = "hello";
                result = q.dequeue();
                test.testResult(expected, result, "dequeue", "");

                // removeKth test
                output.reset();
                expected = "i";
                result = q.removeKth(3);
                test.testResult(expected, result, "removeKth", "The returned value did not match expected return value.");
                expected = "Queue:  world  am  back";
                result = output.toString().trim();
                test.testResult(expected, result, "removeKth", "The queue does not match expected output.");

                Boolean catched = null;
                try {
                    q.removeKth(0);
                    catched = false;
                } catch (IndexOutOfBoundsException e) {
                    catched = true;
                }
                test.testResult(true, catched, "removeKth", "Did not throw IndexOutOfBoundsException at index " + 0);

                try {
                    q.removeKth(4);
                    catched = false;
                } catch (IndexOutOfBoundsException e) {
                    catched = true;
                }
                test.testResult(true, catched, "removeKth", "Did not throw IndexOutOfBoundsException at index " + 4);

                System.setOut(oldOut);
                System.out.println("Test successful.");
            }
        }
        else {
            System.out.println("Enter an item you want to add by simply typing it. \n" +
                    "Write \"-\" to dequeue an item.\n" +
                    "Write \"exit\" to exit.");
            Scanner sc = new Scanner(System.in);
            String input = null;
            while (true) {
                input = sc.next();
                if (input.equals("-"))
                    q.dequeue();
                else if (input.equals("#")) {
                    System.out.println("Enter the index you want to remove.");
                    int k = Integer.parseInt(sc.next());
                    q.removeKth(k);
                } else if (input.equals("exit"))
                    break;
                else
                    q.enqueue(input);
            }
        }
    }

}