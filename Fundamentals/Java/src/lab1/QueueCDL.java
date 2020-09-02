package lab1;

import java.util.Iterator;
import java.util.NoSuchElementException;

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

    private void printContents() {
        for(Item item : this) {
            System.out.println(item);
        }
    }

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
            return current.next != first;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main (String[] args) {
        QueueCDL<String> q = new QueueCDL();
        q.enqueue("Hello");
        q.enqueue("Bitch");
        q.enqueue("How");
        q.enqueue("Are");
        q.enqueue("You");
        System.out.println(q.dequeue() + " " + q.size());
        System.out.println(q.dequeue() + " " + q.size());
        System.out.println(q.dequeue() + " " + q.size());
        System.out.println(q.dequeue() + " " + q.size());
        System.out.println(q.dequeue() + " " + q.size());
        System.out.println(q.dequeue() + " " + q.size());
    }
}
