package main.lab4;

public interface Graphable {
    int V();
    int E();
    void addEdge(int v, int w);
    Iterable<Integer> adj(int v);
    int degree(int v);
    String toString();
}