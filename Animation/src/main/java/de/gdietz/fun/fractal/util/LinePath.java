package de.gdietz.fun.fractal.util;

public class LinePath<T extends Tuple<T>> implements Path<T> {

    private T to;
    private T from;

    private T diff;

    public LinePath(T from, T to) {
        this.from = from;
        this.to = to;
        diff = to.subtract(from);
    }

    public void setLimits(T from, T to) {
        this.from = from;
        this.to = to;
        diff = to.subtract(from);
    }

    public T getFrom() {
        return from;
    }

    public T getTo() {
        return to;
    }

    public T get(double t) {
        return from.add(diff.multiply(t));
    }

}
