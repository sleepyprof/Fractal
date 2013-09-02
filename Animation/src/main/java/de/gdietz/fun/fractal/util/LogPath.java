package de.gdietz.fun.fractal.util;

public class LogPath<T extends Tuple<T>> extends TransformedPath<T> {

    private double alpha;

    public LogPath(Path<T> path, double alpha) {
        super(path);
        this.alpha = alpha;
    }

    public LogPath(T from, T to, double alpha) {
        super(from, to);
        this.alpha = alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getAlpha() {
        return alpha;
    }

    protected double transform(double t) {
        return alpha == 0.0 ? t : (1.0 - Math.exp(-alpha * t)) / (1.0 - Math.exp(-alpha));
    }

}
