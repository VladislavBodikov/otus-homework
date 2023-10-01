package ru.bodikov.otus.utils.exception;

public class AssertionFailedError extends RuntimeException {
    private final String message;

    public AssertionFailedError(Object expected, Object actual) {
        this.message = "\nExpected: " + expected + "\nactual was: " + actual;
    }


    @Override
    public String toString() {
        String className = getClass().getName();
        return ("".equals(message) ? className : (className + ": " + message));
    }

    @Override
    public String getMessage() {
        return message;
    }

}
