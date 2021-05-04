package com.airwallex.calculator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class CalculatorTest {
    private static final int MOCK_POS = 0;
    @Test
    public void add() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.store(new BigDecimal(2));
        cal.store(new BigDecimal(8));
        cal.add();
        assertEquals(2, cal.operands.size());
        assertEquals(new BigDecimal(10), cal.operands.pop());
        assertEquals(new BigDecimal(5), cal.operands.pop());
    }

    @Test
    public void sub() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.store(new BigDecimal(2));
        cal.store(new BigDecimal(8));
        cal.sub();
        assertEquals(2, cal.operands.size());
        assertEquals(new BigDecimal(-6), cal.operands.pop());
        assertEquals(new BigDecimal(5), cal.operands.pop());
    }

    @Test
    public void mul() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.store(new BigDecimal(2));
        cal.store(new BigDecimal(8));
        cal.mul();
        assertEquals(2, cal.operands.size());
        assertEquals(new BigDecimal(16), cal.operands.pop());
        assertEquals(new BigDecimal(5), cal.operands.pop());
    }

    @Test
    public void div() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.store(new BigDecimal(42));
        cal.store(new BigDecimal(4));
        cal.div();
        assertEquals(2, cal.operands.size());
        assertEquals(new BigDecimal(10.5), cal.operands.pop());
        assertEquals(new BigDecimal(5), cal.operands.pop());
    }

    @Test
    public void sqrt() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.store(new BigDecimal(2));
        cal.sqrt();
        assertEquals(2, cal.operands.size());
        assertEquals(new BigDecimal(1.41421356237309, new MathContext(15, RoundingMode.DOWN)), cal.operands.pop());
        assertEquals(new BigDecimal(5), cal.operands.pop());
    }

    @Test(expected = EmptyStackException.class)
    public void emptyStackException() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.div();
    }

    @Test(expected = ArithmeticException.class)
    public void arithmeticException() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.store(new BigDecimal(0));
        cal.div();
    }

    @Test
    public void clear() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.store(new BigDecimal(42));
        cal.store(new BigDecimal(4));
        cal.clear();
        assertEquals(0, cal.operands.size());
    }

    @Test
    public void undo() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.store(new BigDecimal(4));
        cal.store(new BigDecimal(3));
        cal.store(new BigDecimal(2));
        cal.undo();
        cal.undo();
        cal.mul();
        assertEquals(1, cal.operands.size());
        assertEquals(new BigDecimal(20), cal.operands.peek());

        cal.undo();
        assertEquals(2, cal.operands.size());
        assertEquals(new BigDecimal(4), cal.operands.pop());
        assertEquals(new BigDecimal(5), cal.operands.pop());

    }

    @Test
    public void undoWithClear() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(5));
        cal.store(new BigDecimal(4));
        cal.mul();
        cal.clear();
        cal.undo();

        assertEquals(1, cal.operands.size());
        assertEquals(new BigDecimal(20), cal.operands.peek());

        cal.undo();
        assertEquals(2, cal.operands.size());
        assertEquals(new BigDecimal(4), cal.operands.pop());
        assertEquals(new BigDecimal(5), cal.operands.pop());

    }

    @Test
    public void undoWithSameResultOperand() {
        Calculator cal = new Calculator();
        cal.store(new BigDecimal(1));
        cal.store(new BigDecimal(10));
        cal.mul();
        cal.undo();

        assertEquals(2, cal.operands.size());
        assertEquals(new BigDecimal(10), cal.operands.pop());
        assertEquals(new BigDecimal(1), cal.operands.pop());

    }
}