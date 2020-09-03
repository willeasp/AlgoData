package lab1;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class QueueCDL<Item> implements Iterable<Item>{
    private Node first; // first element in queue
    private int n;      // number of elements in queue

    /**
     * Stores information and keeps track of next and previous element
     */
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    /**
     * Creates an instance
     */
    public QueueCDL() {
        this.first = null;
        this.n = 0;
    }

    /**
     *
     * @return The number of elements in queue
     */
    public int size() {
        return this.n;
    }

    /**
     * Add an element to the last place in the queue
     * @param item The element to be added.
     */
    public void enqueue (Item item) {
        if (this.first == null) {
            // if there are no elements in the queue
            Node newNode = new Node();              // create a new Node
            newNode.item = item;                    // store the information
            newNode.next = newNode.prev = newNode;  // point to itself
            first = newNode;                        // make first in queue
        } else {
            Node oldLast = first.prev;              // store the old last element
            Node newLast = new Node();              // create new Node
            newLast.item = item;                    // store information
            newLast.next = first;                   // connect to first
            first.prev = newLast;
            newLast.prev = oldLast;                 // connect to oldLast
            oldLast.next = newLast;
        }
        this.n++;
        printContents();
    }

    /**
     *
     * @return true if the queue has 0 elements
     */
    private boolean isEmpty() {
        return this.n == 0;
    }

    /**
     * Take the first element from the queue
     * @return  The first element in the queue
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
        Item item = first.item;                 // the item to be returned
        Node last = first.prev;                 // the last item in the queue
        first = first.next;                     // change the first element in queue
        first.prev = last;                      // point new first to last
        last.next = first;                      // point last to first
        n--;                                    // count down number of items in queue
        if (isEmpty()) first = null;            // if number of items is 0, first points to nothing
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
     * Creates an Iterator object
     * @return The iterator for the queue
     */
    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator(first);
    }

    /**
     * The iterator for this class.
     */
    private class QueueIterator implements Iterator<Item> {
        private Node current;                   // holds the place in the queue

        public QueueIterator (Node first) {     // constructor
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
        public Item next() {                    // move to next item in queue
            Item item = current.item;
            if (current.next == first)          // if the element after this is first, set current to null
                current = null;
            else
                current = current.next;         // move to next element
            return item;                        // return current iterations element.
        }
    }

    /**
     * Main function. Write the name if the item you want to add to the queue, or write "-" to dequeue an item. Write exit to exit.
     * To run test add -t to program arguments.
     * @param args
     */
    public static void main (String[] args) {
        QueueCDL<String> q = new QueueCDL();
        if (args.length > 0){
            if (args[0].equals("-t")) {
                // test
                System.out.println("TESTING...");

                // set up expected
                String expected = "Queue:  ONE  TWO  THREE  FOUR  FIVE\r\n";

                // set stdout to write to outputstream
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                PrintStream oldOut = System.out;
                System.setOut(new PrintStream(output));

                // add to queue
                q.enqueue("ONE");
                q.enqueue("TWO");
                q.enqueue("THREE");
                q.enqueue("FOUR");

                // reset output for test
                output.reset();

                // perform last test, write FIVE to queue
                q.enqueue("FIVE");

                // check result
                String result = output.toString();
                System.setOut(oldOut);
                if (result.equals(expected)) {
                    System.out.println("Test successful.");
                } else {
                    System.out.println("Test failed.\n" +
                            "Expected: \"" + expected + "\"\n" +
                            "Got:      \"" + result + "\"");
                    System.out.println("Difference: " + result.compareTo(expected));
                }
            }
        } else {
            System.out.println("Enter an item you want to add by simply typing it. \n" +
                    "Write \"-\" to dequeue an item.\n" +
                    "Write \"exit\" to exit.");
            Scanner sc = new Scanner(System.in);
            String input = null;
            while (true) {
                input = sc.next();
                if (input.equals("-"))
                    q.dequeue();
                else if (input.equals("exit"))
                    break;
                else
                    q.enqueue(input);
            }
        }
    }
}
