package de.gdietz.fun.fractal.util;

public interface NormedNumber<X extends NormedNumber<X>> extends NormedSemiNumber<X> {

    public X add(double r);
    public X subtract(double r);

    public X inverse();

    public X multiply(X x);
    public X divide(X x);

    public X getUnit();

    public String toString(boolean parentheses, boolean negativeParentheses, boolean sign);

    public boolean isUnit();

}
