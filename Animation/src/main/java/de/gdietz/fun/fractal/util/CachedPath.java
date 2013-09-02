package de.gdietz.fun.fractal.util;

import java.util.Hashtable;

public class CachedPath<T extends Tuple<T>> implements Path<T> {

    private Path<T> delegate;

    private Hashtable<Double, T> cache;

    public CachedPath(Path<T> path) {
        delegate = path;
        cache = new Hashtable<Double, T>();
    }

    public void setLimits(T from, T to) {
        delegate.setLimits(from, to);
        cache = new Hashtable<Double, T>();
    }

    public T getFrom() {
        return delegate.getFrom();
    }

    public T getTo() {
        return delegate.getTo();
    }

    public T get(double t) {
        if (cache.containsKey(t)) {
            return cache.get(t);
        }
        T value = delegate.get(t);
        cache.put(t, value);
        return value;
    }

}
