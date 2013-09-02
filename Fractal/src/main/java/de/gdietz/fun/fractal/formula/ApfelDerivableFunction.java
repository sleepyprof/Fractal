package de.gdietz.fun.fractal.formula;

public interface ApfelDerivableFunction<X> extends ApfelFunction<X> {

    public X derivation(X z, X c);

}
