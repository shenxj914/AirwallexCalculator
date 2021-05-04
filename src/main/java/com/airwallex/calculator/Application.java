package com.airwallex.calculator;

import org.assertj.core.util.VisibleForTesting;

import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.Scanner;

/**
 * Main application to parse input and run the calculator
 */
public class Application {
    @VisibleForTesting
    Calculator calculator;

    public Application() {
        this(new Calculator());
    }

    public Application(Calculator calculator) {
        this.calculator = calculator;
    }

    public static void main(String[] args) {
        System.out.println("Input numbers and operators separated by whitespace.");
        System.out.println("Support operator: +, -, *, /, sqrt, undo, clear.");
        Application app = new Application();
        app.run();
    }

    public void run() {
        String readLine = null;
        Scanner scanner = new Scanner(System.in);

        while ((readLine = scanner.nextLine()) != null) {
            parse(readLine);
            System.out.println(calculator.getPrintString());
        }
    }


    /**
     * parse the input line and process
     * @param line
     * @throws IllegalArgumentException if input line cannot be parsed. e.g. unsupported operators
     * @throws ArithmeticException if calculation cannot be completed, e.g. divdend = 0
     */
    public void parse(String line) throws IllegalArgumentException, ArithmeticException {
        try {
            String[] operators = line.split(" ");
            int pos = 1; //start pos as 1
            for (String op: operators) {
                if (calculator.operators.containsKey(op)) {
                    calculator.setCurrentPos(pos);
                    calculator.operators.get(op).run();
                } else if (op.length() > 0) {//skip unnessary whitespace
                    calculator.store(new BigDecimal(op)); //use custom object to store pos for stage 3.
                }
                pos += op.length();
                pos++; //for whitespace
            }
        } catch (EmptyStackException e) {
            //if insufficient operands for the opperator, do nothing, just stop processing the line
        } catch (ArithmeticException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong input. Please check the instruction.");
        }

    }


}
