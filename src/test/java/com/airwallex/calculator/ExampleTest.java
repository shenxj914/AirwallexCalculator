package com.airwallex.calculator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.EmptyStackException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class ExampleTest {

    @Test
    public void example1() {
        Application test = new Application();
        test.parse("5 2");
        assertEquals("stack: 5 2 ", test.calculator.getPrintString());

    }

    @Test
    public void example2() {
        Application test = new Application();
        test.parse("2 sqrt");
        assertEquals("stack: 1.4142135623 ", test.calculator.getPrintString());
        test.parse("clear 9 sqrt");
        assertEquals("stack: 3 ", test.calculator.getPrintString());
    }

    @Test
    public void example3() {
        Application test = new Application();
        test.parse("5 2 -");
        assertEquals("stack: 3 ", test.calculator.getPrintString());
        test.parse("3 -");
        assertEquals("stack: 0 ", test.calculator.getPrintString());
        test.parse("clear");
        assertEquals("stack: ", test.calculator.getPrintString());
    }

    @Test
    public void example4() {
        Application test = new Application();
        test.parse("5 4 3 2");
        assertEquals("stack: 5 4 3 2 ", test.calculator.getPrintString());
        test.parse("undo undo *");
        assertEquals("stack: 20 ", test.calculator.getPrintString());
        test.parse("5 *");
        assertEquals("stack: 100 ", test.calculator.getPrintString());
        test.parse("undo");
        assertEquals("stack: 20 5 ", test.calculator.getPrintString());
    }

    @Test
    public void example5() {
        Application test = new Application();
        test.parse("7 12 2 /");
        assertEquals("stack: 7 6 ", test.calculator.getPrintString());
        test.parse("*");
        assertEquals("stack: 42 ", test.calculator.getPrintString());
        test.parse("4 /");
        assertEquals("stack: 10.5 ", test.calculator.getPrintString());

    }

    @Test
    public void example6() {
        Application test = new Application();
        test.parse("1 2 3 4 5");
        assertEquals("stack: 1 2 3 4 5 ", test.calculator.getPrintString());
        test.parse("*");
        assertEquals("stack: 1 2 3 20 ", test.calculator.getPrintString());
        test.parse("clear 3 4 -");
        assertEquals("stack: -1 ", test.calculator.getPrintString());
    }

    @Test
    public void example7() {
        Application test = new Application();
        test.parse("1 2 3 4 5");
        assertEquals("stack: 1 2 3 4 5 ", test.calculator.getPrintString());
        test.parse("* * * *");
        assertEquals("stack: 120 ", test.calculator.getPrintString());
    }

    @Test
    public void example8() {
        Application test = new Application();
        test.parse("1 2 3 * 5 + * * 6 5");
        assertEquals("stack: 11 ", test.calculator.getPrintString());
    }
}