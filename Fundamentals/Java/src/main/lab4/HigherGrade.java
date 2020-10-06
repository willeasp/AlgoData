

package main.lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

public class HigherGrade {
    Scanner sc;

    private EdgeWeightedDigraph G;

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
            if(input.length == 2) {
                Dijkstra d = new Dijkstra(G, input[0]);
                showSinglePath(d.pathTo(input[1]));
            }
            if(input.length == 3) {
                Dijkstra d = new Dijkstra(G, input[0], input[2]);
                showDoublePath(d.pathTo(input[1]));
            }
        }
    }

    private void showSinglePath(Iterable path) {

    }

    private void help() {
        System.out.println("Type \"'A' to 'B'\" or \"'A' to 'B' by 'C'\" to search for shortest path between them");
    }

    private void verifyVertex(int v) {
        if (v < G.V() && v >= 0) throw new IllegalArgumentException();
    }

    private int[] parseInput() {
        String[] input = sc.nextLine().split(" to | by ");
        int[] arr = new int[input.length];
        try {
            for (int i = 0; i < input.length; i++) {
                arr[i] = Integer.parseInt(input[i]);
                verifyVertex(arr[i]);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Only integer values allowed");
        }
        return arr;
    }

    public static void main(String[]args) {
        String fileName = "C:\\Users\\Dell\\Documents\\Code\\KTH\\Algoritmer och Datastrukturer\\Graphs\\tinyEWD.txt";
        HigherGrade h = new HigherGrade(fileName);
    }
}
