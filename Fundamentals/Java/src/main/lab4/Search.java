/*
    Author: William Asp
    October 2, 2020

    **What it is**
        An interface for searching methods in the
        graphs in lab4.
 */

package main.lab4;

public interface Search<Vertex> {

    boolean hasPathTo(int v);

    boolean hasPathTo(Vertex vertex);

    Iterable<Integer> pathTo(int v);

    Iterable<Vertex> pathTo(Vertex vertex);

    int source();
}
