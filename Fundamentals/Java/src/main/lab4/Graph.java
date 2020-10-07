/*
    Author: William Asp
    October 2, 2020

    **What it is**
        An undirected graph.

    **How it works**
        Run the file to test a few vertices connection.
 */

package main.lab4;

import main.util.LinkedList;

/**
 * An undirected graph
 */
public class Graph implements Graphable {
    private LinkedList<Integer>[] adj;      // bags of adjecent vertices
    private final int V;                    // number of vertices
    private int E;                          // number of edges

    /**
     * Initiate an empty graph of V vertices
     * @param V
     */
    public Graph(int V) {
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
        return V;
    }

    /**
     *
     * @return The number of edges in the graph
     */
    @Override
    public int E() {
        return E;
    }

    /**
     * Add an undirected edge between v and w.
     * Does not check if the edge already exists.
     * @param v first vertex
     * @param w second vertex
     */
    @Override
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

    /**
     *
     * @param v the edge to find adjecent vertices to
     * @return An iterable of adjecent vertices to v
     */
    @Override
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
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

    // throws IllegalArgumentException if v is outside graph range
    private void validateVertex(int v) {
        if(v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
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

    /**
     * Unit test the graph
     * @param args
     */
    public static void main(String[] args) {
        Graph G = new Graph(4);
        G.addEdge(0,1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        System.out.println(G.toString());
    }
}
