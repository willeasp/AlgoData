package main.lab4;

public interface Paths<Vertex> {
    boolean hasPathTo (Vertex v);
    Iterable<Vertex> pathTo(Vertex v);
}
