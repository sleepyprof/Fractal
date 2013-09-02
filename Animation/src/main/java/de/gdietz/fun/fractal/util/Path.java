package de.gdietz.fun.fractal.util;

public interface Path<T extends Tuple<T>> {

    public void setLimits(T from, T to);

    public T getFrom();
    public T getTo();

    public T get(double t);

}
