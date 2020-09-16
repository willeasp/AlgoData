/*  Higher Grade Assignment

Author: William Asp
September 9, 2020

Test for BalancedParenthesis.java

 */

package test.lab1;

import main.lab1.BalancedParenthesis;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the BalancedParenthesis class.
 */
class BalancedParenthesisTest {
    BalancedParenthesis b;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        b = new BalancedParenthesis();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        b = null;
    }

    /**
     * Test that the function returns that the parenthesis are balanced
     */
    @org.junit.jupiter.api.Test
    void testBalanced() {
        String test = "{}()[()()]{()}[]";
        assertTrue(b.balanced(test));
    }

    /**
     * Test that the function returnes false when parenthesis are not balanced
     */
    @org.junit.jupiter.api.Test
    void testNotBalanced() {
        String test = "{}()({)[[]])";
        assertFalse(b.balanced(test));
    }

    /**
     * No parenthesis = balanced
     */
    @Test
    void testNoParenthesis() {
        assertTrue(b.balanced("abcdefg"));
    }

    /**
     * Test that the stack does not keep any values.
     */
    @Test
    void testStackIsClear () {
        b.balanced("{");
        assertFalse(b.balanced("}"));
    }


}