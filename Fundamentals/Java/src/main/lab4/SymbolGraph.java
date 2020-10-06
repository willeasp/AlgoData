/*
    Author: William Asp
    October 2, 2020

    **What it is**
        A generic SymbolGraph that can be both directed and not.

    **How it works**
        The graph reads input from an Iterable over Vertex arrays.
        Each vertex in each array in the Iterable the graph considers to be edged together.
        The class may be good to extend to make a type graph that handles the making of
        the input Iterable.

        The SymbolGraph can be both directed and directed.
        The SymbolGraph defaults to being undirected.
        The third argument of the constructor (boolean directed) determines
        if the SymbolGraph is to be directed or not.
        directed = true => directed SymbolGraph
        directed = false => undirected SymbolGraph

    **Testing**
        Run program to test for a small graph.

 */

package main.lab4;

import main.util.LinkedList;
import main.util.HashMap;

/**
 * A generic SymbolGraph which takes input from an iterable of arrays. Each array contains
 * any number of Vertices. Every vertice in each array is considered to be connected each other
 * vertice.
 * @param <Vertex> The "symbol" to store.
 */
public class SymbolGraph<Vertex> {
    private HashMap<Vertex, Integer> st;
    private Vertex[] keys;
    private final Graphable G;

    /**
     * Creates a new undirected Symbolgraph from vertices.
     * @param V
     * @param vertices
     */
    public SymbolGraph (int V, Iterable<Vertex[]> vertices) {
        this(V, vertices, false);
    }

    /**
     * Creates a new SymbolGraph which is either undirected of directed based on boolean directed.
     * @param V The number of vertices
     * @param vertices An iterable over arrays of vertices in which each vertice in an array
     *                 is considered to be connected to all other vertices in that array.
     * @param directed graph is directed if true, undirected if false. Defaults to false
     */
    public SymbolGraph (int V, Iterable<Vertex[]> vertices, boolean directed) {
        st = new HashMap<Vertex, Integer>(V / 4);
        keys = (Vertex[]) new Object[V];
        if(directed)
            G = new Digraph(V);
        else
            G = new Graph(V);

        int v = 0;
        for(Vertex[] vertex : vertices) {
            for(int i = 0; i < vertex.length; i++){
                if (!st.contains(vertex[i])) {
                    st.put(vertex[i], v);
                    keys[v++] = vertex[i];
                    if(v > V) throw new IllegalArgumentException("Iterable have more elements than V.");
                }
            }
            if (vertex.length > 1){
                for (int i = 0; i < vertex.length - 1; i++) {
                    for (int j = i + 1; j < vertex.length; j++) {
                        G.addEdge(st.get(vertex[i]), st.get(vertex[j]));
                    }
                }
            }
        }
    }

    /**
     *
     * @param key
     * @return true if the symbolgraph contains the key
     */
    public boolean contains(Vertex key) {
        return this.st.get(key) != null;
    }

    /**
     *
     * @param key
     * @return the index of the key in the symbolgraph
     */
    public int indexOf (Vertex key) {
        return this.st.get(key);
    }

    /**
     *
     * @param v
     * @return the name in index v
     */
    public Vertex nameOf (int v) {
        return this.keys[v];
    }

    /**
     *
     * @return the Graph associated
     */
    public Graphable G() {
        return this.G;
    }

    /**
     *
     * @return string representation of the symbolgraph
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < G.V(); i++) {
            sb.append(this.nameOf(i) + " : ");
            for(int v : G.adj(i))
                sb.append(this.nameOf(v) + " ");
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        LinkedList<String[]> list = new LinkedList();
        list.add(new String[]{"korv", "bröd"});
        list.add(new String[]{"korv", "mos"});
        list.add(new String[]{"korv", "senap"});
        list.add(new String[]{"mos", "senap"});
        SymbolGraph sg = new SymbolGraph(4, list);
        System.out.println(sg.toString());
        System.out.println(sg.G.toString());
    }
}
