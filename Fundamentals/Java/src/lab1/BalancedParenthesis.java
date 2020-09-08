/*

Author: William Asp
September 8, 2020


 */

package lab1;

import java.util.Stack;

public class BalancedParenthesis {
    Stack<Character> stack = new Stack<>();

    public boolean balanced (String parentheses) {
        char expected;
        for(int i = 0; i < parentheses.length(); i++) {
            char c = parentheses.charAt(i);
            if(c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            else if (c == ')') {
                expected = '(';
                if(stack.pop() != expected)
                    return false;
            }
            else if (c == ']') {
                expected = '[';
                if(stack.pop() != expected)
                    return false;
            }
            else if (c == '}') {
                expected = '{';
                if(stack.pop() != expected)
                    return false;
            }
        }
        if (! stack.empty())
            return false;

        return true;
    }

    public static void main (String[] args) {
        BalancedParenthesis b = new BalancedParenthesis();
        System.out.println(b.balanced("[( )] {}{ [( )() ]()"));
    }
}
