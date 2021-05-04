package com.airwallex.calculator;

import org.assertj.core.util.VisibleForTesting;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Consumer;

/**
 * calculator to run the supported operators on stored operands
 */
public class Calculator {
    //map the function to the operator
    public final Map<String, Runnable> operators;

    @VisibleForTesting
    Stack<BigDecimal> operands;

    //store the used operands in case for undo operator
    private Stack<String> archived_operands;

    //precision to store in the stack
    private static final MathContext stackPrecision = new MathContext(15, RoundingMode.DOWN);

    //precision to output
    private static final MathContext outputPrecision = new MathContext(11, RoundingMode.DOWN);

    //the position of the operator in the input line.
    private int curOperatorPos;

    public Calculator() {
        operators = new HashMap<>();
        operators.put(ValidOperators.ADD, this::add);
        operators.put(ValidOperators.SUB, this::sub);
        operators.put(ValidOperators.MUL, this::mul);
        operators.put(ValidOperators.DIV, this::div);
        operators.put(ValidOperators.SQRT, this::sqrt);
        operators.put(ValidOperators.UNDO, this::undo);
        operators.put(ValidOperators.CLEAR, this::clear);
        operands = new Stack<>();
        archived_operands = new Stack<>();
        curOperatorPos = 0;
    }

    /**
     * store the input operands
     * @param val
     */
    public void store(BigDecimal val){
        operands.push(val);
        //for case: 5 1 4 + undo undo undo undo
        //store the value also in archived_operands to know what to pop
        archived_operands.push(val.toString());
    }

    /**
     *
     * @return content in the stack with the format: stack: <>
     */
    @VisibleForTesting
    String getPrintString() {
        StringBuffer output = new StringBuffer("stack: ");
        for (BigDecimal val: operands) {
            output.append(val.round(outputPrecision) + " ");
        }
        return output.toString();
    }


    /**
     * add function
     * @throws ArithmeticException
     */
    public void add() throws ArithmeticException {
        BigDecimal operand1 = null;
        BigDecimal operand2 = null;
        try {
            operand2 = operands.pop();
            operand1 = operands.pop();
            BigDecimal result = operand1.add(operand2, stackPrecision);
            operands.push(result);
            //to undo, pushed result first in the archived_operands stack
            //which will stop the undo operation when it is the same with the top of the operator stack
            archived_operands.push(result.toString());
            archived_operands.push(operand2.toString());
            archived_operands.push(operand1.toString());

        } catch (EmptyStackException e) {
            handleEmptyStackException(ValidOperators.ADD, operand1, operand2);
            throw e;
        }

    }

    /**
     * sub function
     * @throws ArithmeticException
     */
    public void sub() throws ArithmeticException {
        BigDecimal operand1 = null;
        BigDecimal operand2 = null;
        try {
            operand2 = operands.pop();
            operand1 = operands.pop();
            BigDecimal result = operand1.subtract(operand2, stackPrecision);
            operands.push(result);
            //to undo, pushed result first in the archived_operands stack
            //which will stop the undo operation when it is the same with the top of the operator stack
            archived_operands.push(result.toString());
            archived_operands.push(operand2.toString());
            archived_operands.push(operand1.toString());


        } catch (EmptyStackException e) {
            handleEmptyStackException(ValidOperators.SUB, operand1, operand2);
            throw e;
        }

    }

    /**
     * multiply function
     */
    public void mul() {
        BigDecimal operand1 = null;
        BigDecimal operand2 = null;
        try {
            operand2 = operands.pop();
            operand1 = operands.pop();
            BigDecimal result = operand1.multiply(operand2, stackPrecision);
            operands.push(result);
            //to undo, pushed result first in the archived_operands stack
            //which will stop the undo operation when it is the same with the top of the operator stack
            archived_operands.push(result.toString());
            archived_operands.push(operand2.toString());
            archived_operands.push(operand1.toString());

        } catch (EmptyStackException e) {
            handleEmptyStackException(ValidOperators.MUL, operand1, operand2);
            throw e;
        }

    }

    /**
     * divide function
     */
    public void div() {
        BigDecimal operand1 = null;
        BigDecimal operand2 = null;
        try {
            operand2 = operands.pop();
            operand1 = operands.pop();
            BigDecimal result = operand1.divide(operand2, stackPrecision);
            operands.push(result);
            //to undo, pushed result first in the archived_operands stack
            //which will stop the undo operation when it is the same with the top of the operator stack
            archived_operands.push(result.toString());
            archived_operands.push(operand2.toString());
            archived_operands.push(operand1.toString());
        } catch (EmptyStackException e) {
            handleEmptyStackException(ValidOperators.DIV, operand1, operand2);
            throw e;
        }

    }

    /**
     * sqrt function
     */
    public void sqrt() {
        BigDecimal operand = null;
        try {
            operand = operands.pop();
            BigDecimal result = operand.sqrt(stackPrecision);
            operands.push(result);
            //to undo, pushed result first in the archived_operands stack
            //which will stop the undo operation when it is the same with the top of the operator stack
            archived_operands.push(result.toString());
            archived_operands.push(operand.toString());
        } catch (Exception e) {
            handleEmptyStackException(ValidOperators.SQRT, operand, null);
            throw e;
        }

    }

    /**
     * clear all operators in the stack
     */
    public void clear() {
        //since the current status in the stack is empty(no top element), use clear operator as the delimiter
        archived_operands.push(ValidOperators.CLEAR);
        //dump all elements in the stack to the archived_operands
        while (!operands.isEmpty()) {
            archived_operands.push(operands.pop().toString());
        }

    }

    /**
     * undo the previous operation
     */
    public void undo() {
        if (operands.isEmpty()) { //clear case
            while (!archived_operands.isEmpty() //dump all involved elements from last operation back to stack
                    && !archived_operands.peek().equals(ValidOperators.CLEAR)) {
                operands.push(new BigDecimal(archived_operands.pop()));
            }
        } else { // non-clear case
            BigDecimal top = operands.pop();
            while (!archived_operands.isEmpty() //dump all involved elements from last operation back to stack
                    && !archived_operands.peek().equals(top.toString())) {
                operands.push(new BigDecimal(archived_operands.pop()));
            }
        }
        //dump the delimiter, then previous operation is restored.
        if (!archived_operands.isEmpty()) {
            archived_operands.pop();
        }
    }

    public void setCurrentPos(int pos) {
        curOperatorPos = pos;
    }

    public int getCurrentPos() {
        return curOperatorPos;
    }

    /**
     * If an operator cannot find a sufficient number of parameters on the stack, a warning is displayed:
     * operator <operator> (position: <pos>): insufficient parameters
     * After displaying the warning, all further processing of the string terminates and the current state of the stack is displayed
     * @param operator
     * @param operand1 if operand1 is not missing, put back to the stack for output
     * @param operand2 if operand2 is not missing, put back to the stack for output
     */
    private void handleEmptyStackException(String operator, BigDecimal operand1, BigDecimal operand2) {
        //put in log for prod system.
        System.out.println("operator " + operator + " (position:" + getCurrentPos() + "): insufficient parameters");
        if (operand2 != null) {
            operands.push(operand2);
        }
        if (operand1 != null) {
            operands.push(operand1);
        }
    }


}