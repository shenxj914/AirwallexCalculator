package com.airwallex.calculator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ApplicationTest {

    @Test
    public void parseNoOp() {
        Application app = new Application();
        app.parse("5 3");
        assertEquals(2, app.calculator.operands.size());
        assertEquals(new BigDecimal(3), app.calculator.operands.pop());
        assertEquals(new BigDecimal(5), app.calculator.operands.pop());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseWrongOp() {
        Application app = new Application();
        app.parse("5 abc");
    }

    @Test
    public void parseAdd() {
        Calculator cal = spy(Calculator.class);
        doNothing().when(cal).add();
        Application app = new Application(cal);
        app.parse(" 5 3   + 8 ");
        assertEquals(8, cal.getCurrentPos());
        verify(cal).add();
    }

    @Test
    public void parseUndo() {
        Calculator cal = spy(Calculator.class);
        doNothing().when(cal).undo();
        Application app = new Application(cal);
        app.parse("56 3 903 undo");
        verify(cal).undo();
    }
}