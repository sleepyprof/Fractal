package de.gdietz.fun.fractal.util;

public interface NormedSemiNumber<X extends NormedSemiNumber<X>> extends Normed {

    public X negate();

    public X add(X x);
    public X subtract(X x);

    public X multiply(double r);

    public X sqr();
    public X cube();
    public X pow(int n);

    public X getZero();

}
