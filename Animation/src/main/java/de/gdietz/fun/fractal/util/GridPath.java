package de.gdietz.fun.fractal.util;

public class GridPath implements Path<Coordinate> {

    private final LinkedPath<Coordinate> delegate;

    public GridPath(Coordinate from, Coordinate to, int n) {
        if (n <= 0)
            throw new IllegalArgumentException("The grid number must be positive");

        @SuppressWarnings("unchecked")
        Path<Coordinate>[] paths = (Path<Coordinate>[]) new Path[2 * n];

        if (n == 1) {
            double midX = (from.getX() + to.getX()) / 2;
            double midY = (from.getY() + to.getY()) / 2;
            paths[0] = new LinePath<>(new Coordinate(from.getX(), midY), new Coordinate(to.getX(), midY));
            paths[1] = new LinePath<>(new Coordinate(midX, from.getY()), new Coordinate(midX, to.getY()));
        } else {
            for (int cy = 0; cy < n; cy++) {
                double y = ((n - 1 - cy) * from.getY() + cy * to.getY()) / (n - 1);
                paths[cy] = new LinePath<>(new Coordinate(from.getX(), y), new Coordinate(to.getX(), y));
            }
            for (int cx = 0; cx < n; cx++) {
                double x = ((n - 1 - cx) * from.getX() + cx * to.getX()) / (n - 1);
                paths[cx + n] = new LinePath<>(new Coordinate(x, from.getY()), new Coordinate(x, to.getY()));
            }
        }
        delegate = new LinkedPath<>(paths, new CoordinateAffineTransformFactory());
    }

    public void setLimits(Coordinate from, Coordinate to) {
        delegate.setLimits(from, to);
    }

    public Coordinate getFrom() {
        return delegate.getFrom();
    }

    public Coordinate getTo() {
        return delegate.getTo();
    }

    public Coordinate get(double t) {
        return delegate.get(t);
    }

}
