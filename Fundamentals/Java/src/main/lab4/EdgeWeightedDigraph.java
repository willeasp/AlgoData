package main.lab4;

import main.util.LinkedList;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

public class EdgeWeightedDigraph {
    private LinkedList<DirectedEdge>[] adj;
    private int V;
    private int E;

    /**
     * Create an empty digraph of V vertices
     * @param V number of vertices to create
     */
    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices cannot be negative");
        this.V = V;
        initAdj();
    }

    /**
     * Construct from in
     * @param in Graph information
     */
    public EdgeWeightedDigraph(InputStream in) {
        Scanner sc = new Scanner(in);
        sc.useLocale(Locale.US);
        if((this.V = sc.nextInt()) < 0) throw new IllegalArgumentException("Number of vertices cannot be negative");
        if((this.E = sc.nextInt()) < 0) throw new IllegalArgumentException("Number of edges cannot be negative");
        initAdj();
        int from = 0;
        int to = 0;
        double weight = 0;
        DirectedEdge e = null;
        while(sc.hasNextInt()) {
            from = sc.nextInt();
            to = sc.nextInt();
            weight = sc.nextDouble();
            e = new DirectedEdge(from, to, weight);
            addEdge(e);
        }
    }

    public EdgeWeightedDigraph(EdgeWeightedDigraph old) {
        this.V = old.V();
        this.E = old.E();
        initAdj();

        for(DirectedEdge edge : old.edges()) {
            this.adj[edge.from()].add(edge);
        }
    }

    // helper function to init adj array
    private void initAdj() {
        this.adj = new LinkedList[V];
        for(int i = 0; i < V; i++) {
            adj[i] = new LinkedList<DirectedEdge>();
        }
    }

    /**
     *
     * @return Number of vertices in graph
     */
    public int V() {
        return this.V;
    }

    /**
     *
     * @return Number of edges in graph
     */
    public int E() {
        return this.E;
    }

    /**
     * Add edge e to this graph
     * @param e edge to add
     */
    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
    }

    /**
     * Get edges pointing from v
     * @param v vertex
     * @return edges from v
     */
    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    /**
     * Get all edges in this digraph
     * @return all edges in this graph
     */
    public Iterable<DirectedEdge> edges() {
        LinkedList<DirectedEdge> edges = new LinkedList();
        for (LinkedList<DirectedEdge> list : adj) {
            for (DirectedEdge e : list) {
                edges.add(e);
            }
        }
        return edges;
    }

    /**
     *
     * @return String representation of this class
     */
    @Override
    public String toString() {
        return "EdgeWeightedDigraph= {\n" +
                "adj= \n" + adjToString() +
                "V=" + V +
                "\nE=" + E + " }" ;
    }

    // helper for toString function
    private String adjToString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for(LinkedList list : adj) {
            sb.append(i++ + " : " + list.toString() + "\n");
        }
        return sb.toString();
    }

    // Test, user enter edges
    public static void main(String[] args) {
        InputStream in = System.in;
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        System.out.println(G);

        EdgeWeightedDigraph copy = new EdgeWeightedDigraph(G);
        System.out.println(copy);
    }
}
