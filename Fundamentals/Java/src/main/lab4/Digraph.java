/*
    Author: William Asp
    October 2, 2020

    **What it is**
        A directed graph.

    **How it works**
        Run the file to test a few vertices connection.
 */

package main.lab4;

import main.util.LinkedList;

/**
 * A directional graph
 */
public class Digraph implements Graphable {
    private LinkedList<Integer>[] adj;      // bags of adjecent vertices
    private final int V;                    // number of vertices
    private int E;                          // number of edges

    /**
     * Initiate an empty Digraph of V vertices
     * @param V
     */
    public Digraph(int V) {
        if ( V < 0 ) throw new IllegalArgumentException("Number of vertices cannot be negative");
        this.V = V;
        this.E = 0;
        adj = new LinkedList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedList<Integer>();
        }
    }

    /**
     *
     * @return the number of vertices in the graph
     */
    @Override
    public int V() {
        return this.V;
    }

    /**
     *
     * @return The number of edges in the graph
     */
    @Override
    public int E() {
        return this.E;
    }

    /**
     * Add an directed edge between v and w.
     * Does not check if the edge already exists.
     * @param v source vertex
     * @param w destination vertex
     */
    @Override
    public void addEdge(int v, int w) {
        E++;
        adj[v].add(w);
    }

    /**
     *
     * @param v the edge to find adjecent vertices to
     * @return An iterable of adjecent vertices to v
     */
    @Override
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    /**
     *
     * @param v A vertice
     * @return the number of adjecent vertices
     */
    @Override
    public int degree(int v) {
        return adj[v].size();
    }

    /**
     *
     * @return String representation of the graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V + " vertices " + E + " edges \n");
        for(int v = 0; v < V; v++) {
            sb.append(v + ": ");
            for(int w : adj[v]) {
                sb.append(w + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Digraph G = new Digraph(4);
        G.addEdge(0,1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        System.out.println(G.toString());
    }
}
