import edu.princeton.cs.algs4.Stack;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Ass1 {
    private Stack<Character> stack = new Stack<>();

    public void iterativeReverse() {
        char c;
        while((c = readChar()) != '\n') {
            stack.push(c);
        }

        while(!(stack.isEmpty())) {
            System.out.print(stack.pop());
        }
    }

    public void recursiveReverse() {
        char c = readChar();
        if (c != '\n') {
            recursiveReverse();
            System.out.print(c);
        }
        return;
    }

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

    private void runMethod(String methodName) {
        try {
            this.getClass().getMethod(methodName).invoke(this);
        } catch (IllegalAccessException e) {
            System.out.println(methodName + " is private");
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
        Ass1 ass1 = new Ass1();
        if (args[0].equals("-t")) {
            String[] testcases = {"12345\n", "hello world!\n", "-cool?\n", "subline\n"};
            String[] expectedcases = {"54321", "!dlrow olloh", "?looc-", "enilbus"};
            String[] algorithms = {"iterativeReverse", "recursiveReverse"};

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PrintStream oldOut = System.out;
            System.setOut(new PrintStream(output));

            for(String algorithm : algorithms){
                for (String testcase : testcases) {
                    InputStream testInput = new ByteArrayInputStream(testcase.getBytes());
                    InputStream oldIn = System.in;
                    try {
                        System.setIn(testInput);
                        ass1.runMethod(algorithm);
                        String result = output.toString();
                        System.out.println(result);

                    } finally {
                        System.setIn(oldIn);
                    }
                }
            }
            System.setOut(oldOut);

        } else {
            System.out.println("Iterative reverse. Enter the string you want reversed.");
            ass1.iterativeReverse();
            System.out.println("\nRecursive reverse. Enter the string you want reversed.");
            ass1.recursiveReverse();
        }

    }

}