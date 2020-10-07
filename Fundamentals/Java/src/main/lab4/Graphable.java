/*
    Author: William Asp
    October 2, 2020

    **What it is**
       An interface for the different graph types
       in lab 4.
 */

package main.lab4;

/**
 * An interface for graphs without weights
 */
public interface Graphable {
    // get number of vertices
    int V();

    // get number of edges
    int E();

    // add an edge to the graph
    void addEdge(int v, int w);

    // get adjecent to v
    Iterable<Integer> adj(int v);

    // get number of vertices adjecent to v
    int degree(int v);

    // string representation of graph
    String toString();
}