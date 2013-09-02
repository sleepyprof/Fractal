package de.gdietz.fun.fractal.util;

public abstract class TransformedPath<T extends Tuple<T>> implements Path<T> {

    private final Path<T> path;

    public TransformedPath(Path<T> path) {
        this.path = path;
    }

    public TransformedPath(T from, T to) {
        this(new LinePath<T>(from, to));
    }

    public void setLimits(T from, T to) {
        path.setLimits(from, to);
    }

    public T getFrom() {
        return path.getFrom();
    }

    public T getTo() {
        return path.getTo();
    }

    protected abstract double transform(double t);

    public T get(double t) {
        return path.get(transform(t));
    }

}
