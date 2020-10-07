/*
    Author: William Asp
    October 2, 2020

    **What it is**
        A depth first search algorithm, with an
        enhanced API that let's the user get results using
        symbols directly.

    **How it works**
        run the file to test for a simple graph.

 */

package main.lab4;

import edu.princeton.cs.algs4.Stack;
import main.util.LinkedList;

import java.io.File;
import java.io.FileWriter;

/**
 * A depth first search class, with API support for SymbolGraph.
 * @param <Vertex>
 */
public class DFS<Vertex> implements Search<Vertex>{
    private boolean[] marked;
    private int[] from;
    private final int source;
    private final int V;
    private SymbolGraph<Vertex> SG;

    /**
     * Create a new search from vertex source
     * @param SG Symbolgraph to search
     * @param source vertex to search from
     */
    public DFS(SymbolGraph<Vertex> SG, Vertex source) {
        this(SG.G(), SG.indexOf(source));
        this.SG = SG;
    }

    /**
     * Create a new search from vertex source
     * @param G Graph to search in
     * @param source vertex to search from
     */
    public DFS (Graphable G, int source) {
        this.V = G.V();
        validateVertex(source);
        this.source = source;
        from = new int[V];
        marked = new boolean[V];
        dfs(G, source);
    }

    // breadth first search algorithm
    private void dfs(Graphable G, int v) {
        marked[v] = true;
        for(int w : G.adj(v)) {
            if(!marked[w]){
                from[w] = v;
                dfs(G, w);
            }
        }
    }

    /**
     *
     * @param v Vertex
     * @return True if source has a path to v
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     *
     * @param vertex Vertex
     * @return true if source has a path to vertex
     */
    public boolean hasPathTo(Vertex vertex) {
        validateVertex(vertex);
        return hasPathTo(SG.indexOf(vertex));
    }

    /**
     *
     * @param v destination
     * @return an iterable of all vertices to traverse to get to v.
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if(!hasPathTo(v)) return null;
        Stack<Integer> path= new Stack();
        for(int x = v; x != source; x = from[x]) {
            path.push(x);
        }
        path.push(source);
        return path;
    }

    /**
     *
     * @param vertex destination
     * @return an iterable of all vertices to traverse to get to v.
     */
    public Iterable<Vertex> pathTo(Vertex vertex) {
        validateVertex(vertex);
        LinkedList<Vertex> list = new LinkedList();
        if(!hasPathTo(SG.indexOf(vertex))) return null;
        for(Integer v : pathTo(SG.indexOf(vertex))) {
            list.add(SG.nameOf(v));
        }
        return list;
    }

    /**
     *
     * @return the source vertex for this search
     */
    public int source() { return this.source; };

    // check so that the vertex is valid
    private void validateVertex(int v) {
        if (v < 0 || v >= this.V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    // check so that the vertex is valid
    private void validateVertex(Vertex v) {
        if (! SG.contains(v))
            throw new IllegalArgumentException("vertex " + v + " does not exist." );
    }

    /**
     * Unit test the search
     * @param args
     */
    public static void main(String[] args) {
        String fileName = "test.txt";
        File file = new File(fileName);
        FileWriter fw = null;
        StringGraph sg = null;
        try {
            fw = new FileWriter(file);
            String states = "AL FL\n" +
                    "AL GA\n" +
                    "AL MS\n" +
                    "AL TN\n" +
                    "AR LA\n" +
                    "AR MO\n" +
                    "AR MS\n" +
                    "AR OK\n" +
                    "AR TN\n" +
                    "AR TX\n" +
                    "AZ CA\n" +
                    "AZ NM\n" +
                    "AZ NV\n" +
                    "AZ UT\n" +
                    "CA NV\n" +
                    "CA OR\n" +
                    "CO KS\n" +
                    "CO NE\n" +
                    "CO NM\n" +
                    "CO OK";
            fw.write(states);
            fw.close();
            sg = new StringGraph(fileName, " ");
        } catch (Exception e) {
            System.out.println("Could not do the thing");
        }
        System.out.println(sg.toString());
        System.out.println(sg.G().toString());
        DFS<String> search1 = new DFS(sg, "AZ");
        for(String s : search1.pathTo("UT"))
            System.out.print(s + " -> ");
        file.delete();
    }

}
