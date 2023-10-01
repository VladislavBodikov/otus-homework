package ru.bodikov.otus.service;


import ru.bodikov.otus.annotation.After;
import ru.bodikov.otus.annotation.Before;
import ru.bodikov.otus.annotation.Test;
import ru.bodikov.otus.utils.Assertions;

class CalculatorTest {

    Calculator calculator;

    @Before
    void init(){
        this.calculator = new Calculator();
    }

    @Before
    void init2(){
        this.calculator = new Calculator();
    }

    @Test
    void sum() {
        Assertions.assertEquals(2,calculator.sum(1,1));
    }

    @Test
    void sum_NotEqual() {
        Assertions.assertEquals(5,calculator.sum(1,1));
    }

    @Test
    void minus() {
        Assertions.assertEquals(0,calculator.minus(1,1));
    }

    @Test
    void divide() {
        Assertions.assertEquals(5,calculator.divide(10,2));
    }

    @Test
    void runtimeException() {
        throw new RuntimeException("Unpredictable exception in TEST method");
    }

    @After
    void clearData(){
        this.calculator = null;
    }
}