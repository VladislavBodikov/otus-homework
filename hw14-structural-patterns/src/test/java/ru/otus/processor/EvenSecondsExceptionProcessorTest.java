package ru.otus.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvenSecondsExceptionProcessorTest {

    Processor processor = new EvenSecondsExceptionProcessor();

    @Test
    void process() {
        while (System.currentTimeMillis() / 1000 % 2 != 0){

        }
        Assertions.assertThrows(RuntimeException.class, () -> processor.process(null));
    }
}