package de.gdietz.fun.fractal.util;

public class LinkedPath<T extends Tuple<T>> implements Path<T> {

    private final Path<T>[] paths;
    private final AffineTransformFactory<T, ?> transformFactory;

    public LinkedPath(Path<T>[] paths, AffineTransformFactory<T, ?> transformFactory) {
        if (paths.length == 0)
            throw new IllegalArgumentException("At least one path needed.");
        this.paths = paths;
        this.transformFactory = transformFactory;
    }

    public void setLimits(T from, T to) {
        T oldFrom = paths[0].getFrom();
        T oldTo = paths[paths.length - 1].getTo();
        AffineOperator<T, ?> op = transformFactory.get(oldFrom, oldTo, from, to);
        for(Path<T> path : paths)
            path.setLimits(op.operate(path.getFrom()), op.operate(path.getTo()));
    }

    public T getFrom() {
        return paths[0].getFrom();
    }

    public T getTo() {
        return paths[paths.length - 1].getTo();
    }

    public T get(double t) {
        if (t < 0.0 || t > 1.0)
            throw new IllegalArgumentException("Parameter t must be between 0 and 1");
        double nt = paths.length * t;
        int i = (int) nt;
        if (i == paths.length)
            i = paths.length - 1;
        double pt = nt - i;
        return paths[i].get(pt);
    }

}
