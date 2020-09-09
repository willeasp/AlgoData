/*  Assignment 2
*
* Author: William Asp
* September 1, 2020
*
* **What it does**
*   A recursive and an iterative version of a function
*   which reads characters from stdin until a newline
*   character is read and then prints them on stdout in reverse order.
*
* **How it is used**
*   The user will be presented with a prompt telling to input the
*   string to reverse. This will happen two times, one time for each
*   algorithm.
*
* **Testing**
*   Add "-t" to the program arguments to run the test.
*
* The code is not based on any outside source.
*
*
* */

package main.lab1;

import edu.princeton.cs.algs4.Stack;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

/**
 * Reverses a string read from System.in and prints it to System.out
 * @author William Asp
 */
public class Reverse {
    private Stack<Character> stack = new Stack<>();

    /**
     * Reverses a string from System.in and prints it to System.out
     */
    public void iterativeReverse() {
        char c;
        while((c = readChar()) != '\n') {
            stack.push(c);
        }

        while(!(stack.isEmpty())) {
            System.out.print(stack.pop());
        }
    }

    /**
     * Reverses a string from System.in and prints it to System.out
     */
    public void recursiveReverse() {
        char c = readChar();
        if (c != '\n') {
            recursiveReverse();
            System.out.print(c);
        }
        return;
    }

    /**
     * Reads a single char from System.in
     * @return A character read from System.in
     */
    private static char readChar() {
        char c = 0;
        try {
            c = (char) System.in.read();
        } catch (IOException e) {
            System.out.println("Could not read from System.in");
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Run a method in this class by name. For running the algorithms
     * @param methodName name of the method to be run.
     */
    private void runMethod(String methodName) {
        try {
            this.getClass().getMethod(methodName).invoke(this);
        } catch (IllegalAccessException e) {
            System.out.println(methodName + " is not accessible");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("Something went wrong running the method " + methodName);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("There is no method named " + methodName);
            e.printStackTrace();
        }
    }

    public static void main(String args[] ) {
        Reverse rev = new Reverse();
        if (args.length > 0){
            if (args[0].equals("-t")) {
                // run tests

                // Initialize variables for running tests
                String[] testcases = {"12345\n", "hello world!\n", "-cool?\n", "subline\n", "#byxa\n"};    // array with test cases
                String[] expectedcases = {"54321", "!dlrow olleh", "?looc-", "enilbus", "axyb#"};        // array with expected output from the test cases
                String[] algorithms = {"iterativeReverse", "recursiveReverse"};                 // names of the functions performing the reverse. Used in runMethod.
                LinkedList<String> failedTest = new LinkedList<>();                             // add strings with errors in this

                // initialize streams
                ByteArrayOutputStream output = new ByteArrayOutputStream();                     // Store the output here printed to standard out
                PrintStream oldOut = System.out;                                                // Store the original standard out
                System.setOut(new PrintStream(output));                                         // redirect standard out to "output" stream
                InputStream oldIn = System.in;                                                  // store the old standard in


                // iterate over both algorithms and every test case
                for (String algorithm : algorithms) {                                            // Run test for both algoritms
                    for (int i = 0; i < testcases.length; i++) {                                // try every testcase
                        // Set System.in to the test case
                        InputStream testInput = new ByteArrayInputStream(testcases[i].getBytes());  // make in inputstream from the word in testcases
                        try {
                            System.setIn(testInput);                                            // set the word from testcases as input in standard in
                            rev.runMethod(algorithm);                                           // perform the algorithm
                            String result = output.toString();                                  // the output from the algorithm is stored in "result"
                            output.reset();
                            if (!(result.equals(expectedcases[i]))) {                           // check if output is not equal to expected
                                failedTest.add("Case failed. Expected: " + expectedcases[i] + ", Got: " + result);
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
                // Restore in and output
                System.setIn(oldIn);
                System.setOut(oldOut);

                // print eventual errors
                if (!failedTest.isEmpty()) {
                    System.out.println(failedTest.toString());
                } else {
                    System.out.println("Test successful.");
                }
            }
        } else {
            // run program
            System.out.println("Iterative reverse. Enter the string you want reversed.");
            rev.iterativeReverse();
            System.out.println("\nRecursive reverse. Enter the string you want reversed.");
            rev.recursiveReverse();
        }
    }

}