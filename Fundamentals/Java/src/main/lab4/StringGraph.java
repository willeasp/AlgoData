/*
    Author: William Asp
    October 2, 2020

    **What it is**
        An extension of symbolgraph using strings.
        The symbolgraph can be both undirected and directed

    **How it works**
        Run this file to view the output from a test run.

        The symbolgraph defaults to being undirected.
        To make it directed, send it the argument true
        to the constructor.

 */

package main.lab4;

import main.lab3.BST;
import main.util.LinkedList;

import java.io.*;
import java.util.Scanner;

/**
 * A symbolgraph extension for reading strings
 */
public class StringGraph extends SymbolGraph {

    /**
     * Create a new SymbolGraph that is undirected
     * @param fileName Name of file to read from
     * @param delimiter What separates the vertices
     * @throws FileNotFoundException if no file is found.
     */
    public StringGraph(String fileName, String delimiter) throws FileNotFoundException {
        this(fileName, delimiter, false);
    }

    /**
     * Create a new Symbolgraph that it either directed or not.
     * @param fileName the name of the file with graph information
     * @param delimiter ehst separates the vertices
     * @param directed wether the graph should be directed or not
     * @throws FileNotFoundException if no file is found
     */
    public StringGraph(String fileName, String delimiter, boolean directed) throws FileNotFoundException {
        super(vertices(fileName, delimiter), loadInput(fileName, delimiter), directed);
    }

    /**
     * Load the graph from the file.
     * @param fileName graph information file
     * @param delimiter what separates edges
     * @return An iterable of all vertices connected
     * @throws FileNotFoundException
     */
    private static Iterable loadInput(String fileName, String delimiter) throws FileNotFoundException {
        InputStream input = new FileInputStream(fileName);
        LinkedList<String[]> vertices = new LinkedList();
        Scanner sc = new Scanner(input);

        while (sc.hasNextLine()) {
            String[] a = sc.nextLine().split(delimiter);
            vertices.add(a);
        }
        return vertices;
    }

    /**
     * Count how many vertices there are in the file.
     */
    private static int vertices(String fileName, String delimiter) throws FileNotFoundException {
        InputStream input = new FileInputStream(fileName);
        BST<String, Integer> verticeCounter = new BST();
        Scanner sc = new Scanner(input);

        while (sc.hasNextLine()) {
            String[] a = sc.nextLine().split(delimiter);
            for (int i = 0; i < a.length; i++) {
                if (!verticeCounter.contains(a[i]))
                    verticeCounter.put(a[i], verticeCounter.size());
            }
        }
        return verticeCounter.size();
    }

    /**
     * Unit tests
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
            System.out.println("Could not read file");
        }
        System.out.println(sg.toString());
        System.out.println(sg.G().toString());
        file.delete();
    }
}
