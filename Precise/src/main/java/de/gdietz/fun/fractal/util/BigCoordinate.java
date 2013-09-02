package de.gdietz.fun.fractal.util;

import java.math.BigDecimal;

public class BigCoordinate implements Tuple<BigCoordinate> {

    public static final BigCoordinate ORIGIN = new BigCoordinate();

    protected final BigDecimal x;
    protected final BigDecimal y;

    public BigCoordinate(BigDecimal x, BigDecimal y) {
        if (x == null || y == null)
            throw new NullPointerException();

        this.x = x;
        this.y = y;
    }

    public BigCoordinate() {
        this(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    public BigCoordinate(BigCoordinate c) {
        x = c.x;
        y = c.y;
    }

    public BigCoordinate(double x, double y) {
        this(new BigDecimal(x), new BigDecimal(y));
    }

    public BigCoordinate(Coordinate c) {
        this(c.x, c.y);
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal normSqrPrecise() {
        return x.multiply(x).add(y.multiply(y));
    }
    
    public double normSqr() {
        return normSqrPrecise().doubleValue();
    }

    public double norm() {
        return Math.sqrt(normSqr());
    }

    public BigCoordinate negate() {
        return new BigCoordinate(x.negate(), y.negate());
    }

    public BigCoordinate add(BigCoordinate c) {
        return new BigCoordinate(x.add(c.x), y.add(c.y));
    }

    public BigCoordinate subtract(BigCoordinate c) {
        return new BigCoordinate(x.subtract(c.x), y.subtract(c.y));
    }

    public BigCoordinate multiply(BigDecimal r) {
        return new BigCoordinate(r.multiply(x), r.multiply(y));
    }

    public BigCoordinate multiply(double r) {
        return multiply(new BigDecimal(r));
    }

    public boolean isZero() {
        return x.compareTo(BigDecimal.ZERO) == 0 && y.compareTo(BigDecimal.ZERO) == 0;
    }

    public Coordinate coordinateValue() {
        return new Coordinate(x.doubleValue(), y.doubleValue());
    }

    public String toString() {
        return "(" + x.toString() + ", " + y.toString() + ")";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BigCoordinate)) return false;

        BigCoordinate that = (BigCoordinate) o;

        return x.equals(that.x) && y.equals(that.y);
    }

    public int hashCode() {
        int result = x.hashCode();
        result = 31 * result + y.hashCode();
        return result;
    }

}
