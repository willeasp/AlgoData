/*

Author: William Asp
September 8, 2020

Queue Ordered

**What it does**
    An ordered general queue which allows the user to remove the kth element from the queue.
    The elements stored should be integer values. The elements are ordered at insertion so
    that all elements are stored in ascending order starting from when you insert the first
    element and in all following insertions.

**How it works**
    To input elements to the queue, type in a string and press enter to
    enqueue.
    To dequeue, type "-". To remove an item at index k, type "#", press
    enter, then input the index of the element to remove, starting with
    index 1 at the end of the queue.
    The queue is ordered automatically.

**Testing**
    To run tests, add "-t" as program arguments before running.

The program has taken inspiration from the Queue.java found at https://algs4.cs.princeton.edu/code/

 */

package lab1;

import Util.Testing;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class QueueOrdered implements Iterable<Integer> {
    private Node first;         // first element in queue
    private Node last;          // last element in queue
    private int n;              // # of items in queue

    /**
     * Holds each element and points to the next node
     */
    private class Node {
        private int item;
        private Node next;
    }

    /**
     * Class Constructor
     */
    public QueueOrdered() {
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

    /**
     * Put the added element in the correct place in the queue.
     * @param item The item to place
     */
    private void sortIn (int item) {
        // sortIn places item in the correct place by checking every element before it if it is bigger.
        // When the element after is bigger, item is placed before it.
        Node newNode = new Node();                  // create the new Node
        newNode.item = item;                        // store information
        Node current = first;                       // current = iterator node
        if (first.item > newNode.item) {            // if new element is to be placed first
            newNode.next = first;
            first = newNode;
        } else if (first.next == null){             // if new element is to be placed second
            first.next = newNode;
            newNode.next = null;
        }
        else {
            boolean lastPlace = false;                  // boolean if the new element is placed last
            while (current.next.item < newNode.item) {  // move iterator to the position before the element bigger than newNode
                if (current.next.next == null) {        // if at the end of the queue
                    current.next.next = newNode;        // place the new node there
                    newNode.next = null;
                    last = newNode;                     // new last
                    lastPlace = true;                   // yes, newNode was placed last
                    break;                              // exit loop
                }
                current = current.next;                 // move to next element
            }
            if (! lastPlace){                           // if newNode was not placed last
                newNode.next = current.next;            // place newNode in the queue
                current.next = newNode;
            }
        }
    }

    /**
     * Add <code>item</code> to queue.
     *
     * @param item
     */
    public void enqueue(int item) {
        if (isEmpty()) {            // if there are no elements
            first = new Node();     // create a new node
            first.item = item;      // store information
            last = first;           // point last to same element
        } else {
            sortIn(item);           // place the element in the correct spot
        }
        increaseSize();
        printContents();
    }

    /**
     * Get the first element from the queue
     *
     * @return The first element in the queue
     */
    public int dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        int item = first.item;                      // item to be returned
        first = first.next;                         // new first
        decreaseSize();
        if (isEmpty()) last = null;                 // if nothing left, last is null
        printContents();
        return item;
    }

    /**
     * Remove the Kth index from the queue, starting at index 1 at the last entered item.
     * @param k The index to remove.
     * @return The item removed
     */
    public int removeKth (int k) {
        if (k > size() || k <= 0) {              // if index out of range
            throw new IndexOutOfBoundsException("No element at that index");
        }
        int item = 0;                            // item to remove
        Node current = first;                    // for iterating over queue
        int index = size();                      // index starting at first counting backwards

        if(k == index) {                         // if k is at the first element
            return dequeue();
        }
        while (index != (k + 1)) {               // move to the element right before k
            current = current.next;
            index--;
        }
        item = current.next.item;                // get the item to be returned
        if (current.next == last) {              // if the element to be removed is last
            last = current;                      // new last
            last.next = null;
        } else {
            current.next = current.next.next;    // hop over the element to be removed
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
        for (int item : this) {                 // use the iterator
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
    public Iterator<Integer> iterator() {
        return new QueueIterator(first);
    }

    /**
     * Iterator for this class
     */
    private class QueueIterator implements Iterator<Integer> {
        private Node current;

        public QueueIterator (Node first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            int item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        QueueOrdered q = new QueueOrdered();

        if (args.length > 0){
            if (args[0].equals("-t")) {
                System.out.println("TESTING...");

                Testing test = new Testing();

                // set stdout to write to outputstream
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                PrintStream oldOut = System.out;
                System.setOut(new PrintStream(output));

                // set up first test
                q.enqueue(10);
                q.enqueue(20);
                q.enqueue(15);
                q.enqueue(100);

                // enqueue test
                output.reset();
                q.enqueue(-1);
                String expected = "Queue:  -1  10  15  20  100";
                String result = output.toString().trim();
                test.testResult(expected, result, "enqueue", "");

                // dequeue test
                output.reset();
                int exp = -1;
                int res = q.dequeue();
                test.testResult(expected, result, "dequeue", "");

                // removeKth test
                output.reset();
                exp = 15;
                res = q.removeKth(3);
                test.testResult(expected, result, "removeKth", "The returned value did not match expected return value.");
                expected = "Queue:  10  20  100";
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
                    q.enqueue(Integer.parseInt(input));
            }
        }
    }
}
