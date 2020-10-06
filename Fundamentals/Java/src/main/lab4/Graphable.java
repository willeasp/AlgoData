/*
    Author: William Asp
    October 2, 2020

    **What it is**
       An interface for the different graph types
       in lab 4.
 */

package main.lab4;

public interface Graphable {
    int V();
    int E();
    void addEdge(int v, int w);
    Iterable<Integer> adj(int v);
    int degree(int v);
    String toString();
}