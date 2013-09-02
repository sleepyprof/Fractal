package de.gdietz.fun.fractal.util;

public class NonInvertibleOperatorException extends RuntimeException {

    public NonInvertibleOperatorException() {
    }

    public NonInvertibleOperatorException(String message) {
        super(message);
    }

    public NonInvertibleOperatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonInvertibleOperatorException(Throwable cause) {
        super(cause);
    }

}
