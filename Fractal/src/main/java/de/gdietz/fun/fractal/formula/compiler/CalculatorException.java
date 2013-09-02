package de.gdietz.fun.fractal.formula.compiler;

public class CalculatorException extends RuntimeException {

    public CalculatorException() {
    }

    public CalculatorException(String message) {
        super(message);
    }

    public CalculatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculatorException(Throwable cause) {
        super(cause);
    }

}
