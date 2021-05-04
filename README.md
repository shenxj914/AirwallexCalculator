# AirwallexCalculator
This sample project provides a command line tool to do RPN Calculator operations.
Details can be found in .pdf file.

## System Requirements
1. JDK 1.8
2. Maven Build tool (3.X.X)

## Build project
mvn clean package

## Run
java -jar target/calculator-1.0-SNAPSHOT.jar

## Unit Test
**for parse the input line**

mvn clean test -Dtest=ApplicationTest

**for calculator function, add, sub...etc**

mvn clean test -Dtest=CalculatorTest

**for all example use cases in the pdf file**

mvn clean test -Dtest=ExampleTest

## Code structure
- src
    - main
        - java
            - com.airwallex.calculator.Application//main class, parse the input line
            - com.airwallex.calculator.Calculator //calculator function class
            - com.airwallex.calculator.ValidOperators //constant class to put valid operators.
    - test
        - java
            

