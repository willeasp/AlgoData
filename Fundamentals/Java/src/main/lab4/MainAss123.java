/*
    Author: William Asp
    October 2, 2020

    **What it is**
        A program that can answer the question:
        "Find the path from A to B":
            1. Based on DFS
            2. Based on BFS
            3. With or without directed graph

        The program can dynamically switch between DFS and BFS
        search when the user wants.

        The program can change graph type during runtime as well.

    **How it works**
        Directed or undirected graph?
            - Program argument:
                (none) : undirected
                -d : directed

            - During runtime:
              Type "graph" to enter what graph type to use.

        Use DFS or BFS?
            - When starting the program the user will be
              asked what searching method to use.

            - Change during runtime:
              Type "search" to enter what searching
              method to use.
 */

package main.lab4;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The program for Assignment 1, 2 and 3.
 * The program can answer the question:
 *      Find the path from X to Y
 *      - using both DFS and BFS
 *          - It is easy to switch between DFS and BFS during runtime.
 *            simply type "search" and you will be able to enter the
 *            new searching mechanism.
 *      - using either a directed or undirected graph
 *          - Whether the graph should be directed or not
 *            is chosen with the flag -d as program argument.
 */
public class MainAss123 {
    private StringGraph SG;                 // The symbolgraph in use
    private Scanner sc;                     // Scanner to use in program
    private SearchFactory searchFactory;    // for getting search methods
    private GraphFactory graphFactory;      // for getting directed or undirected graphs

    /**
     * Initiate program
     * @param fileName file with graph information
     * @param delimiter what separates the vertices
     * @param directed if the graph should be directed or not
     */
    public MainAss123(String fileName, String delimiter, boolean directed) {
        this.graphFactory = new GraphFactory(fileName, delimiter);
        setGraphType(directed);

        this.searchFactory = new SearchFactory();
        System.out.println(SG.toString());
        interactive();
    }

    // Program loop
    private void interactive() {
        sc = new Scanner(System.in);
        setSearchType();
        String[] input;
        String XX = null;
        String YY = null;
        String path;
        System.out.println("Enter 'XX to YY' to search for paths.");
        while(sc.hasNext()) {
            input = sc.nextLine().split(" to ");
            if (input.length < 2) {                         // if the input is a command
                if(input[0].equals("")) continue;
                if(input[0].equals("search")) {             // change search type
                    setSearchType();
                    continue;
                } else if (input[0].equals("graph")) {      // change the graph type
                    setGraphType();
                    System.out.println(SG.toString());
                    continue;
                } else {
                    System.out.println("Please type using correct format.");
                    continue;
                }
            }
            try {
                verifyVertex((XX = input[0]));
                verifyVertex((YY = input[1]));
            } catch (IllegalArgumentException e) {
                System.out.println("No vertex " + e.getMessage());
                continue;
            }
            if ((path = getPath(newSearch(SG, XX), YY)) == null)
                System.out.println("No path from " + XX + " to " + YY);    // get search from searchesST
            else System.out.println("There exists a path:\n" + path);
        }
    }

    // Change what searching method is used
    private void setSearchType() {
        System.out.println("What search type would you like to use?\n" +
                "DFS or BFS?");
        String type;
        while(true) {
            type = sc.nextLine();
            if(type.equals("DFS") || type.equals("BFS")) {
                setSearchType(type);
                break;
            }
            else
                System.out.println("Enter a correct search method.");
            }
    }

    // change what searching method is used
    private void setSearchType(String searchName) {
        searchFactory.setDefaultSearch(searchName);
    }

    // make a new search
    private Search newSearch(SymbolGraph SG, String vertex) {
        verifyVertex(vertex);
        return searchFactory.getNewSearch(SG, vertex);
    }

    // get a string representation of the path
    private String getPath (Search<String> search, String vertex) {
        verifyVertex(vertex);
        Iterable<String> result;
        if((result = search.pathTo(vertex)) == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for(String s : result ) {
            sb.append(s);
            if (s.equals(vertex))
                break;
            sb.append(" -> ");
        }
        sb.append("\n");
        return sb.toString();
    }

    // ask for a new graph type
    private void setGraphType() {
        System.out.println("What graph type would you like to use?\n" +
                "graph or digraph?");
        String type;
        boolean directed = false;
        while(true) {
            type = sc.nextLine();
            if(type.equals("graph") || type.equals("digraph")) {
                if(type.equals("digraph"))
                    directed = true;
                setGraphType(directed);
                break;
            }
            else
                System.out.println("Enter a correct graph type.");
        }
        System.out.println("Now using a " + type);
    }

    // load the new graph that is either directed or not
    private void setGraphType(boolean directed) {
        try {
            SG = graphFactory.getNewStringGraph(directed);
        } catch (FileNotFoundException e) {
            System.out.println("could not read from file");
        }
    }

    // check that the vertex exists
    private void verifyVertex (String vertex) throws IllegalArgumentException {
        if (!SG.contains(vertex)) throw new IllegalArgumentException(vertex);
    }

    // A class to load the chosen search algorithm.
    private class SearchFactory {
        private String defaultSearch;

        public SearchFactory() { }

        public void setDefaultSearch (String defaultSearch) {
            this.defaultSearch = defaultSearch;
        }

        public Search getNewSearch (SymbolGraph SG, String vertex) {
            switch (defaultSearch) {
                case "DFS":
                    return new DFS(SG, vertex);
                case "BFS":
                    return new BFS(SG, vertex);
                default:
                    throw new NoSuchElementException("no such search.");
            }
        }
    }

    // class that remembers the filename and delimiter and loads a new stringgraph that is directed or not
    private class GraphFactory {
        private String fileName;
        private String delimiter;

        public GraphFactory(String fileName, String delimiter){
            this.fileName = fileName;
            this.delimiter = delimiter;
        }

        public StringGraph getNewStringGraph(boolean directed) throws FileNotFoundException {
            return new StringGraph(fileName, delimiter, directed);
        }
    }

    // start program here
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Graphs\\contiguous-usa.dat.txt";
        String delimiter = " ";
        boolean directed = false;
        if(args.length > 0 && args[0].equals("-d"))
            directed = true;
        MainAss123 m = new MainAss123(fileName, delimiter, directed);
    }
}
