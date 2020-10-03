package main.lab4;

public interface Search<Vertex> {

    boolean hasPathTo(int v);

    boolean hasPathTo(Vertex vertex);

    Iterable<Integer> pathTo(int v);

    Iterable<Vertex> pathTo(Vertex vertex);

    int source();
}
