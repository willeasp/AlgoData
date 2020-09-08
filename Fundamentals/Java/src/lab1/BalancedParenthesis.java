/*

Author: William Asp
September 8, 2020

Balanced Parenthesis

**What it does**
     a program which takes as input a series of parentheses , that is a series of the characters:
     '(', ')', '[', ']', '{', '}'. The program should check if the parentheses are "balanced" or not.

**How it works**
    The program will prompt for a string with the parenthesis to check.
    After entering the parenthesis, the program will tell if the parenthesis
    were balanced or not.

**Testing**
    To run tests, add "-t" as program arguments before running.

The program was not based on any outside source.

 */

package lab1;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class BalancedParenthesis {
    Stack<Character> stack = new Stack<>();

    private boolean popEqualsExpected (char c, char exp) {
        try {
            if (stack.pop() != exp)
                return false;
        } catch (EmptyStackException e) {
            return false;
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            System.exit(0);
        }
        return true;
    }

    public boolean balanced (String parentheses) {
        char expected;
        for(int i = 0; i < parentheses.length(); i++) {
            char c = parentheses.charAt(i);
            if(c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            else if (c == ')') {
                if(! popEqualsExpected(c, '('))
                    return false;
            }
            else if (c == ']') {
                if (! popEqualsExpected(c, '['))
                    return false;
            }
            else if (c == '}') {
                if (! popEqualsExpected(c, '{'))
                    return false;
            }
        }
        if (! stack.empty())
            return false;

        return true;
    }

    public static void main (String[] args) {
        BalancedParenthesis b = new BalancedParenthesis();
        Scanner sc = new Scanner(System.in);
        String input;
        boolean result;
        while (true) {
            System.out.println("Enter a series of parenthesis to check if they are balanced.");
            input = sc.nextLine();
            result = b.balanced(input);
            if (result)
                System.out.println("The parenthesies were balanced.");
            else
                System.out.println("The parenthesies were not balanced.");
        }
    }
}
