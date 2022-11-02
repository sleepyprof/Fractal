package de.gdietz.fun.fractal.util;

public class Coordinate implements Tuple<Coordinate> {

    public static final Coordinate ORIGIN = new Coordinate();

    public static final Coordinate NaN = new Coordinate(Double.NaN, Double.NaN);

    protected final double x;
    protected final double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate() {
        this(0.0, 0.0);
    }

    public Coordinate(Coordinate c) {
        x = c.x;
        y = c.y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double normSqr() {
        return x * x + y * y;
    }

    public double norm() {
        return Math.sqrt(normSqr());
    }

    public double arg() {
        if (x == 0.0 && y == 0.0)
            return 0.0;
        return Math.atan2(y, x);
    }

    public Coordinate negate() {
        return new Coordinate(-x, -y);
    }

    public Coordinate add(Coordinate c) {
        return new Coordinate(x + c.x, y + c.y);
    }

    public Coordinate subtract(Coordinate c) {
        return new Coordinate(x - c.x, y - c.y);
    }

    public Coordinate multiply(double r) {
        return new Coordinate(r * x, r * y);
    }

    public static Coordinate fromPolar(double r, double phi) {
        return new Coordinate(r * Math.cos(phi), r * Math.sin(phi));
    }

    public boolean isZero() {
        return x == 0.0 && y == 0.0;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;

        Coordinate that = (Coordinate) o;

        return x == that.x && y == that.y;
    }

    public int hashCode() {
        int result;
        long temp;
        temp = x != 0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != 0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
