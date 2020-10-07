/*
    Author: William Asp
    October 7, 2020

    **What it is**
        Main class for running the higher grade assignment.

    **How it works**
        Run the file and the program will
        load the file specified in main. The program will run automatically.

 */

package main.lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import main.util.LinkedList;
import java.util.Scanner;

/**
 * Program to run higher grade assignment
 */
public class HigherGrade {
    private Scanner sc;
    private EdgeWeightedDigraph G;
    private boolean restrict = false;

    /**
     * Load everything and start program
     * @param fileName
     */
    public HigherGrade(String fileName) {
        InputStream in = null;
        try {
            in = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Could not load file.");
            System.exit(0);
        }
        G = new EdgeWeightedDigraph(in);
        interactive();
    }

    // program loop
    private void interactive() {
        sc = new Scanner(System.in);
        help();
        int[] input;
        while(sc.hasNextLine()) {
            input = parseInput();
            if(input.length < 2) {
                System.out.println("Please use the right format");
                help();
            }
            try {
                if (input.length == 2) {
                    int from = input[0];
                    int to = input[1];
                    showPath(singlePath(from, to));
                }
                if (input.length == 3) {
                    int from = input[0];
                    int to = input[1];
                    int by = input[2];
                    if(restrict)
                        showPath(doublePathRestrict(from, by, to));
                    else
                        showPath(doublePath(from, by, to));
                }
            } catch (Exception e) {
                System.out.println("No path found.");
            }
        }
    }

    // read input from user
    private int[] parseInput() {
        int[] arr;
        while (true){
            String in = sc.nextLine();
            if(in.equals("help")) {
                help();
                continue;
            }
            else if (in.equals("restrict")) {
                restrict = !restrict;
                System.out.println("restrict = " + restrict);
                continue;
            }
            if(! ((in.matches("^\\d+ to \\d+") || (in.matches("^\\d+ to \\d+ by \\d+"))))) {
                System.out.println("Please type using correct format" );
                continue;
            }
            String[] input = in.split(" to | by ");
            arr = new int[input.length];
            try {
                for (int i = 0; i < input.length; i++) {
                    arr[i] = Integer.parseInt(input[i]);
                    verifyVertex(arr[i]);
                }
                break;
            } catch (Exception e) {
                System.out.println("Only integer values allowed");
                continue;
            }
        }
        return arr;
    }

    // get a single path
    private Iterable<DirectedEdge> singlePath(int from, int to) {
        Dijkstra d = new Dijkstra(G, from);
        return d.pathTo(to);
    }

    // get a single path
    private Iterable<DirectedEdge> doublePath(int from, int by, int to) {
        LinkedList<DirectedEdge> path = new LinkedList<>();

        Dijkstra d = new Dijkstra(G, from);
        for (DirectedEdge e : d.pathTo(by)) {
            path.add(e);
        }
        d = new Dijkstra(G, by);
        for(DirectedEdge e : d.pathTo(to)) {
            path.add(e);
        }
        return path;
    }

    // get a double path with restriction to not visit an edge twice
    private Iterable<DirectedEdge> doublePathRestrict(int from, int by, int to) {
        Dijkstra d = new Dijkstra(G, from, by);
        return d.pathTo(to);
    }

    // print the path to stdout
    private void showPath(Iterable<DirectedEdge> path) {
        for (DirectedEdge edge : path) {
            System.out.print(edge.from() + "->" + edge.to() + "  ");
        }
        System.out.println();
    }

    // print help to stdout
    private void help() {
        System.out.println("Type \"A to B\" or \"A to B by C\" to search for shortest path between them");
    }

    // check that the vertex exist
    private void verifyVertex(int v) {
        if (! (v < G.V() && v >= 0)) throw new IllegalArgumentException();
    }


    /**
     * Starts the program
     * @param args
     */
    public static void main(String[]args) {
        String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Graphs\\NYC.txt"; // file to load
        HigherGrade h = new HigherGrade(fileName);
    }
}
