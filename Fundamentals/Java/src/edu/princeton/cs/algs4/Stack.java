/*  Assignment 2

 */

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 *  The {@code Stack} class represents a last-in-first-out (LIFO) stack of generic items.
 *  It supports the usual <em>push</em> and <em>pop</em> operations, along with methods
 *  for peeking at the top item, testing if the stack is empty, and iterating through
 *  the items in LIFO order.
 *  <p>
 *  This implementation uses a singly linked list with a static nested class for
 *  linked-list nodes.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *
 *  @param <Item> the generic type of an item in this stack
 */
public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first;     // top of stack
    private int n;                // size of the stack


    // helper linked list class
    private static class Node<Item> {
        private Item item;              // item stored in stack
        private Node<Item> next;        // points to next element in stack
    }

    /**
     * Initializes an empty stack.
     */
    public Stack() {
        first = null;
        n = 0;
    }

    /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the number of items in this stack.
     *
     * @return the number of items in this stack
     */
    public int size() {
        return n;
    }

    /**
     * Adds the item to this stack.
     *
     * @param  item the item to add
     */
    public void push(Item item) {
        Node<Item> oldfirst = first;                // store old first
        first = new Node<Item>();                   // create the new node and point first to it
        first.item = item;                          // store information
        first.next = oldfirst;                      // point to old first
        n++;                                        // increase size
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");     // if stack is empty
        Item item = first.item;                     // save item to return
        first = first.next;                         // delete first node / move to next node in stack
        n--;                                        // decrease size
        return item;                                // return the saved item
    }


    /**
     * Returns (but does not remove) the item most recently added to this stack.
     *
     * @return the item most recently added to this stack
     * @throws NoSuchElementException if this stack is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in this stack in LIFO order, separated by spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }


    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     *
     * @return an iterator to this stack that iterates through the items in LIFO order
     */
    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) {
            current = first;
        }           // point current to first

        public boolean hasNext() {
            return current != null;
        }                   // if current is null, no more elements left

        public void remove() {
            throw new UnsupportedOperationException();
        }   // not used

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();               // if there are no elements
            Item item = current.item;                                         // item to return
            current = current.next;                                           // move to next item
            return item;                                                      // return item
        }
    }


    /**
     * Unit tests the {@code Stack} data type.
     * Modified by William Asp
     *
     * @param args the command-line arguments
     *
     *
     */
    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String item = sc.next();
            if (!item.equals("-"))
                stack.push(item);
            else if (!stack.isEmpty())
                System.out.print(stack.pop() + " ");
        }
        System.out.println("(" + stack.size() + " left on stack)");
    }
}


    /******************************************************************************
     *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
     *
     *  This file is part of algs4.jar, which accompanies the textbook
     *
     *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
     *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
     *      http://algs4.cs.princeton.edu
     *
     *
     *  algs4.jar is free software: you can redistribute it and/or modify
     *  it under the terms of the GNU General Public License as published by
     *  the Free Software Foundation, either version 3 of the License, or
     *  (at your option) any later version.
     *
     *  algs4.jar is distributed in the hope that it will be useful,
     *  but WITHOUT ANY WARRANTY; without even the implied warranty of
     *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     *  GNU General Public License for more details.
     *
     *  You should have received a copy of the GNU General Public License
     *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
     ******************************************************************************/

