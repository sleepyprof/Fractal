package de.gdietz.fun.fractal.util;

public interface Operator<T extends Tuple<T>, O extends Operator<T, O>> {

    public T operate(T x);
    public O compose(O op);

    public O add(O op);
    public O multiply(double r);

    public O inverse() throws NonInvertibleOperatorException;
    
}
