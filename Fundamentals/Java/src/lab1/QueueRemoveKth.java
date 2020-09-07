/*

Author : William Asp
September 7, 2020



 */

package lab1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import Util.Testing;
import Util.Testing.*;

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

    /**
     * Add <code>item</code> to queue.
     *
     * @param item
     */
    public void enqueue(Item item) {
        Node newNode = new Node();      // create a new node
        newNode.item = item;            // store information
        if (isEmpty()) {            // if there are no elements
            first = newNode;            // first and last point to the same node
            last = newNode;
        }
        last.next = newNode;
        newNode.next = first;
        last = newNode;                 // the newly inserted element is the new last
        increaseSize();
        printContents();
    }

    /**
     * Get the first element from the queue
     *
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
     * Remove the Kth index from the queue, starting at index 1 at the last entered item.
     * @param k The index to remove.
     * @return The item removed
     */
    public Item removeKth (int k) {
        Item item = null;
        Node current = first;
        int index = size();
        if (k > index || k <= 0) {
            throw new IndexOutOfBoundsException("No element at that index");
        }
        if(k == index) {
            return dequeue();
        }
        while (index != (k + 1)) {
            current = current.next;
            index--;
        }
        if (last == current.next)
            last = current;
        item = current.next.item;
        current.next = current.next.next;
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