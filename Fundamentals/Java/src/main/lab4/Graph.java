package main.lab4;


public interface Graph<Vertex> {
    int V();        // number of vertices
    int E();        // number of edges
    void addEdge(Vertex v, Vertex w);
    Iterable<Vertex> adj(Vertex v);
    int degree(Vertex v);
    String toString();
}
