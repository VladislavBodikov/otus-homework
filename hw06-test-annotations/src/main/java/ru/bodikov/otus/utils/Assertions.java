package ru.bodikov.otus.utils;

import ru.bodikov.otus.utils.exception.AssertionFailedError;

public class Assertions {
    public static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            failNotEqual(expected, actual);
        }
    }

    private static void failNotEqual(Object expected, Object actual){
        throw new AssertionFailedError(expected, actual);
    }

    private Assertions(){
        throw new IllegalStateException("Utility class");
    }
}
