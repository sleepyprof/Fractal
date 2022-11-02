package de.gdietz.fun.fractal.util;

public class Coordinate3D implements Tuple<Coordinate3D> {

    public static final Coordinate3D ORIGIN = new Coordinate3D();

    public static final Coordinate3D NaN = new Coordinate3D(Double.NaN, Double.NaN, Double.NaN);

    protected final double x;
    protected final double y;
    protected final double z;

    public Coordinate3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate3D() {
        this(0.0, 0.0, 0.0);
    }

    public Coordinate3D(Coordinate3D c) {
        x = c.x;
        y = c.y;
        z = c.z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double normSqr() {
        return x * x + y * y + z * z;
    }

    public double norm() {
        return Math.sqrt(normSqr());
    }

    public Coordinate3D negate() {
        return new Coordinate3D(-x, -y, -z);
    }

    public Coordinate3D add(Coordinate3D v) {
        return new Coordinate3D(x + v.x, y + v.y, z + v.z);
    }

    public Coordinate3D subtract(Coordinate3D v) {
        return new Coordinate3D(x - v.x, y - v.y, z - v.z);
    }

    public Coordinate3D multiply(double r) {
        return new Coordinate3D(r * x, r * y, r * z);
    }

    public double getPhi() {
        if (x == 0.0 && y == 0.0)
            return 0.0;
        return Math.atan2(y, x);
    }

    public double getTheta() {
        if (x == 0.0 && y == 0.0 && z == 0.0)
            return 0.0;
        return Math.acos(z / norm());
    }

    public double getThetaVar() {
        if (x == 0.0 && y == 0.0 && z == 0.0)
            return 0.0;
        return Math.asin(z / norm());
    }

    public static Coordinate3D fromSpherical(double r, double phi, double theta) {
        double rho = r * Math.sin(theta);
        return new Coordinate3D(rho * Math.cos(phi), rho * Math.sin(phi), r * Math.cos(theta));
    }

    public static Coordinate3D fromSphericalVar(double r, double phi, double theta) {
        double rho = r * Math.cos(theta);
        return new Coordinate3D(rho * Math.cos(phi), rho * Math.sin(phi), r * Math.sin(theta));
    }

    public boolean isZero() {
        return x == 0.0 && y == 0.0 && z == 0.0;
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate3D)) return false;

        Coordinate3D that = (Coordinate3D) o;

        return x == that.x && y == that.y && z == that.z;
    }

    public int hashCode() {
        int result;
        long temp;
        temp = x != 0.0d ? Double.doubleToLongBits(x) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = y != 0.0d ? Double.doubleToLongBits(y) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = z != 0.0d ? Double.doubleToLongBits(z) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
