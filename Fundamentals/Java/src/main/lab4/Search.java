/*
    Author: William Asp
    October 2, 2020

    **What it is**
        An interface for searching methods in the
        graphs in lab4.
 */

package main.lab4;

/**
 * An interface for searching in a graph without weighted edges
 * @param <Vertex>
 */
public interface Search<Vertex> {

    // check if there is a path to vertex
    boolean hasPathTo(int v);
    boolean hasPathTo(Vertex vertex);

    // get the path to vertex
    Iterable<Integer> pathTo(int v);
    Iterable<Vertex> pathTo(Vertex vertex);

    // get the source vertex for the search
    int source();
}
