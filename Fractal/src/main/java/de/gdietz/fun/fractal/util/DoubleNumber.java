package de.gdietz.fun.fractal.util;

import de.gdietz.util.NumberFormatHelper;

import java.text.NumberFormat;

public class DoubleNumber implements NormedNumber<DoubleNumber> {

    public static final DoubleNumber ZERO = new DoubleNumber();
    public static final DoubleNumber ONE = new DoubleNumber(1.0);
    public static final DoubleNumber MINUS_ONE = new DoubleNumber(-1.0);

    public static final DoubleNumber NaN = new DoubleNumber(Double.NaN);

    private final double x;

    public DoubleNumber(double x) {
        this.x = x;
    }

    public DoubleNumber() {
        this(0.0);
    }

    public DoubleNumber(DoubleNumber d) {
        x = d.x;
    }

    public double getDouble() {
        return x;
    }

    public double normSqr() {
        return x * x;
    }

    public double norm() {
        return Math.abs(x);
    }

    public DoubleNumber negate() {
        return new DoubleNumber(-x);
    }

    public DoubleNumber add(double r) {
        return new DoubleNumber(x + r);
    }

    public DoubleNumber add(DoubleNumber d) {
        return new DoubleNumber(x + d.x);
    }

    public DoubleNumber subtract(double r) {
        return new DoubleNumber(x - r);
    }

    public DoubleNumber subtract(DoubleNumber d) {
        return new DoubleNumber(x - d.x);
    }

    public DoubleNumber multiply(double r) {
        return new DoubleNumber(r * x);
    }

    public DoubleNumber multiply(DoubleNumber d) {
        return new DoubleNumber(x * d.x);
    }

    public DoubleNumber sqr() {
        return new DoubleNumber(x * x);
    }

    public DoubleNumber cube() {
        return new DoubleNumber(x * x * x);
    }

    public DoubleNumber inverse() {
        return new DoubleNumber(1 / x);
    }

    public DoubleNumber divide(double r) {
        return new DoubleNumber(x / r);
    }

    public DoubleNumber divide(DoubleNumber d) {
        return new DoubleNumber(x / d.x);
    }

    public DoubleNumber pow(int n) {
        return new DoubleNumber(Math.pow(x, n));
    }

    public DoubleNumber getZero() {
        return ZERO;
    }

    public DoubleNumber getUnit() {
        return ONE;
    }

    public boolean isZero() {
        return x == 0.0;
    }

    public boolean isUnit() {
        return x == 1.0;
    }

    public String toString(NumberFormat nf, boolean parentheses, boolean negativeParentheses, boolean sign) {
        ComplexNumberFormatter cnf = new ComplexNumberFormatter(nf, parentheses, negativeParentheses, sign);
        cnf.addNumberString(x);
        return cnf.toString();
    }

    public String toString(NumberFormat nf, boolean parentheses) {
        return toString(nf, parentheses, false, false);
    }

    public String toString(boolean parentheses, boolean negativeParentheses, boolean sign) {
        return toString(NumberFormatHelper.getDefaultNumberFormat(), parentheses, negativeParentheses, sign);
    }

    public String toString(boolean parentheses) {
        return toString(parentheses, false, false);
    }

    public String toString() {
        return toString(false);
    }

}
