package main.lab4;

import main.Util.LinkedList;
import main.lab3.HashMap;

import java.io.InputStream;
import java.util.Scanner;

public class UniGraph {
    private HashMap<String, LinkedList<String>> graph;
    private int edges = 0;

    /**
     * Create a new Undirected Graph of capacity V
     * @param V
     */
    public UniGraph (int V) {
        graph = new HashMap(V / 4);
    }

    /**
     * Create a new Undirected Graph from input
     * @param input
     */
    public UniGraph (InputStream input) {
        Scanner sc = new Scanner(input);

        graph = new HashMap();
        while (sc.hasNextLine()) {
            String v = sc.next();
            String w = sc.next();
            graph.put(v, new LinkedList<>());
            graph.put(w, new LinkedList<>());
            addEdge(v, w);
        }
    }

    /**
     *
     * @return Number of vertices
     */
    public int V() {
        return graph.size();
    }

    /**
     *
     * @return Number of edges
     */
    public int E() {
        return edges;
    }

    /**
     * Add a Undirected edge between v and w
     * @param v
     * @param w
     */
    public void addEdge(String v, String w) {
        LinkedList list = graph.get(v);
        list.add(w);
        graph.put(v, list);

        list = graph.get(w);
        list.add(v);
        graph.put(w, list);

        edges++;
    }

    /**
     *
     * @param v
     * @return An Iterable of all adjecent vertices of v
     */
    public Iterable adj(String v) {
        return graph.get(v);
    }

    /**
     *
     * @param v
     * @return The number of vertices adjecent to v.
     */
    public int degree(String v) {
        return graph.get(v).size();
    }

    public String toString () {
        return "cool";
    }
}
