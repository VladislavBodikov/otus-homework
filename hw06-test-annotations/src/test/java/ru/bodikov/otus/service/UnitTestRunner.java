package ru.bodikov.otus.service;

import ru.bodikov.otus.TestRunnerApplication;

public class UnitTestRunner {

    public static void main(String[] args) {
        TestRunnerApplication.runTestClass(CalculatorTest.class);
    }
}
