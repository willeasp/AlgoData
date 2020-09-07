/*
* Author: William Asp
* September 4, 2020
*
* QueueInsertRemoveFirstLast
*
* **What it does**
*   A generic iterable circular linked list which allows the user to insert
*   and remove elements to/from the front and back end of the queue.
*
* **How it is used**
*   To input elements to the queue, type in a string and press enter to
*   enqueue.
*   To dequeue, type "-". To remove first item, type "!". To remove
*   last item, type "_".
*
* **Testing**
*  To run tests, add "-t" as program arguments before running.
*
* The program is not based on any outside source.
*
* */

package lab1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A generic iterable circular linked list which allows the user to insert
 * and remove elements to/from the front and back end of the queue.
 * @param <Item> What type the queue should hold.
 */
public class QueueIRFL<Item> implements Iterable<Item> {
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
    public QueueIRFL() {
        this.first = null;
        this.last = null;
        this.n = 0;
    }

    /**
     * Get the number of elements in the queue.
     * @return Number of elements in the queue.
     */
    public int size() { return this.n; }

    /**
     * increase or decrease the item counter.
     */
    private void increaseSize() { this.n++; }
    private void decreaseSize() { this.n--; }

    /**
     * Add <code>item</code> to queue.
     * @param item
     */
    public void enqueue (Item item) {
        last = insert(item);                // the newly inserted element is the new last
        increaseSize();
        printContents();
    }

    /**
     * Inserts <code>item</code> first in the queue
     * @param item The item to be added first in the queue
     */
    public void insertFirst (Item item) {
        first = insert(item);               // the newly inserted element is the new first
        increaseSize();
        printContents();
    }

    /**
     * Helper function.
     * Inserts an item between last and first.
     * @param item Item to be added
     * @return The node that is placed between first and last
     */
    private Node insert (Item item) {
        Node newNode = new Node();      // create a new node
        newNode.item = item;            // store information
        if (isEmpty()) {            // if there are no elements
            first = newNode;            // first and last point to the same node
            last = newNode;
        }
        last.next = newNode;
        newNode.next = first;
        return newNode;
    }

    /**
     * Get the first element from the queue
     * @return The first element in the queue
     */
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

    /**
     * Take away the first element in the queue
     * @return The item removed.
     */
    public Item removeFirst() {
        return dequeue();
    }

    /**
     * Take away the last element in the queue
     * @return The item removed
     */
    public Item removeLast() {
        if (size() == 0) throw new NoSuchElementException("Queue is empty");
        Item item = last.item;
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
        return item;
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

    /**
     *
     * @return True if there are 0 elements in the queue
     */
    private boolean isEmpty() { return n == 0;  }

    /**
     * Returns an Iterator for the Class
     * @return The iterator for this class
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

    /**
     * If the test is failed
     * @param expected Expected output
     * @param result Actual output
     * @param methodName Name of the method with the failed test.
     */
    private static void testFailed(String expected, String result, String methodName) {
        System.err.println("Test failed.\n" +
                "Expected: \"" + expected + "\"\n" +
                "Got     : \"" + result + "\"\n" +
                "Method: " + methodName);
        System.exit(0);
    }

    /**
     * Checks if the test is successful
     * @param expected Expected output
     * @param result Actual output
     * @param methodName Name of the method being tested
     */
    private static void testResult(String expected, String result, String methodName) {
        if (!(result.equals(expected))) {
            testFailed(expected, result, methodName);
        }
    }

    public static void main (String[] args) {
        QueueIRFL<String> q = new QueueIRFL();
        if (args.length > 0) {
            if (args[0].equals("-t")) {
                System.out.println("TESTING...");

                // set stdout to write to outputstream
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                PrintStream oldOut = System.out;
                System.setOut(new PrintStream(output));

                // set up first test
                q.enqueue("world");
                q.enqueue("i");
                q.enqueue("am");

                // enqueue test
                output.reset();
                q.enqueue("back");
                String expected = "Queue:  world  i  am  back";
                String result = output.toString().trim();
                testResult(expected, result, "enqueue");

                // insertFirst test
                output.reset();
                q.insertFirst("hello");
                expected = "Queue:  hello  world  i  am  backs";
                result = output.toString().trim();
                testResult(expected, result, "insertFirst");

                // dequeue test
                expected = "hello";
                result = q.dequeue();
                testResult(expected, result, "dequeue");

                // removeLast test
                output.reset();
                q.removeLast();
                expected = "Queue:  world  i  am";
                result = output.toString().trim();
                testResult(expected, result, "removeLast");

                // removeFirst test
                output.reset();
                q.removeFirst();
                expected = "Queue:  i  am";
                result = output.toString().trim();
                testResult(expected, result, "removeFirst");

                System.setOut(oldOut);
                System.out.println("Test successful.");
            }                   // tests
        } else {
            Scanner sc = new Scanner(System.in);
            String input = null;
            System.out.println("Type in a string and press enter to enqueue.\n" +
                    "Type \"+\" to insert element first in queue.\n" +
                    "Type \"-\" to dequeue.\n" +
                    "Type \"!\" to remove first element from queue.\n" +
                    "Type \"_\" to remove last element in queue.\n");
            while (true) {
                input = sc.next();
                if (input.equals("-"))
                    q.dequeue();
                else if (input.equals("+")) {
                    System.out.println("Add element to first in queue:");
                    input = sc.next();
                    q.insertFirst(input);
                } else if (input.equals("!"))
                    q.removeFirst();
                else if (input.equals("_"))
                    q.removeLast();
                else if (input.equals("?"))
                    System.out.println("Size is: " + q.size());
                else
                    q.enqueue(input);
            }
        }                                          // main
    }
}
