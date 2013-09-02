package de.gdietz.fun.fractal.util;

public interface LinearOperator<T extends Tuple<T>, O extends Operator<T, O>> extends Operator<T, O> {

    public double det();

}
