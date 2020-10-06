package main.lab4;

import edu.princeton.cs.algs4.Stack;
import main.util.LinkedList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Dijkstra {
    private DirectedEdge[] from;
    private double[] distTo;
    private DirectedEdge[] byPath;
    private IndexMinPQ<Double> pq;
    LinkedList<DirectedEdge> firstPath = null;

    /**
     * Create a new search
     * @param G graph
     * @param s source
     */
    public Dijkstra (EdgeWeightedDigraph G, int s) {
        for(DirectedEdge e : G.edges()) {
            if(e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " hash negative weight");
        }

        distTo = new double[G.V()];
        from = new DirectedEdge[G.V()];

        validateVertex(s);

        for(int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;

        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while(!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge edge : G.adj(v))
                relax(edge);
        }
    }

    public Dijkstra(EdgeWeightedDigraph G, int s, int by) {
        for(DirectedEdge e : G.edges()) {
            if(e.weight() < 0)
                throw new IllegalArgumentException("edge " + e + " hash negative weight");
        }

        distTo = new double[G.V()];
        from = new DirectedEdge[G.V()];

        validateVertex(s);

        byPath = new DirectedEdge[G.V()];

        for(int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;

        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while(!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge edge : G.adj(v)) {
                relax(edge);
            }
        }

        firstPath = new LinkedList();
        for(DirectedEdge edge : pathTo(by)) {
            byPath[edge.from()] = edge;
            firstPath.addFirst(edge);
        }

        for(int i = 0; i < G.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
            from[i] = null;
        }
        distTo[by] = 0;

        pq.insert(by, distTo[by]);
        while(!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge edge : G.adj(v)) {
                byRelax(edge);
            }
        }
        from[s] = null;
    }

    private void relax (DirectedEdge edge) {
        int source = edge.from(), dest = edge.to();
        if(distTo[dest] > distTo[source] + edge.weight()) {
            distTo[dest] = distTo[source] + edge.weight();
            this.from[dest] = edge;

            if(pq.contains(dest)) pq.decreaseKey(dest, distTo[dest]);
            else pq.insert(dest, distTo[dest]);
        }
    }

    private void byRelax (DirectedEdge edge) {
        if (byPath[edge.from()] != edge){
            int source = edge.from(), dest = edge.to();
            if (distTo[dest] > distTo[source] + edge.weight()) {
                distTo[dest] = distTo[source] + edge.weight();
                this.from[dest] = edge;

                if (pq.contains(dest)) pq.decreaseKey(dest, distTo[dest]);
                else pq.insert(dest, distTo[dest]);
            }
        }
    }

    /**
     * Get distance to v
     * @param v vertex
     * @return distance to v from s
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Does s has a path to v
     * @param v vertex
     * @return true if s has a path to v
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Path from s to v, null if none
     * @param v destination
     * @return Path from s to v, null if none.
     */
    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if(!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge edge = from[v]; edge != null; edge = from[edge.from()]) {
            path.push(edge);
        }
        if (firstPath != null){
            for (DirectedEdge edge : firstPath) {
                path.push(edge);
            }
        }
        return path;
    }

    // throw an IllegalArgumentException unless 0 <= v < V
    private void validateVertex(int v) {
        if(v < 0 || v > from.length)
            throw new IllegalArgumentException("Vertex " + v + " is outside range");
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Graphs\\tinyEWD.txt";
        InputStream input = new FileInputStream(fileName);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(input);
        /*Dijkstra d = new Dijkstra(G, 4);
        for (int i = 0; i < 8; i++) {
            System.out.println(4 + " to " + i);
            for(DirectedEdge edge : d.pathTo(i)) {
                System.out.print(edge.from() + "->" + edge.to() + "  ");
            }
            System.out.println();
            System.out.println(d.distTo(i));
        }*/

        Dijkstra dos = new Dijkstra(G, 3, 4);
        for(DirectedEdge edge : dos.pathTo(6)) {
            System.out.print(edge.from() + "->" + edge.to() + "  ");
        }
    }
}
