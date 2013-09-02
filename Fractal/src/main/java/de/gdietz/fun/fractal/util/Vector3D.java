package de.gdietz.fun.fractal.util;

public class Vector3D extends Coordinate3D implements NormedSemiNumber<Vector3D> {

    public static final Vector3D ORIGIN = new Vector3D();

    public static final Vector3D NaN = new Vector3D(Double.NaN, Double.NaN, Double.NaN);

    public Vector3D() {
    }

    public Vector3D(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector3D(Coordinate3D c) {
        super(c);
    }

    public Vector3D negate() {
        return new Vector3D(-x, -y, -z);
    }

    public Vector3D add(Vector3D v) {
        return new Vector3D(x + v.x, y + v.y, z + v.z);
    }

    public Vector3D subtract(Vector3D v) {
        return new Vector3D(x - v.x, y - v.y, z - v.z);
    }

    public Vector3D multiply(double r) {
        return new Vector3D(r * x, r * y, r * z);
    }

    public Vector3D cross(Vector3D v) {
        return new Vector3D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    public Vector3D sqr() {
        return pow(2);
    }

    public Vector3D cube() {
        return pow(3);
    }

    public Vector3D pow(int n) {
        return pow((double) n);
    }

    public Vector3D pow(double d) {
        if (x == 0.0 && y == 0.0 && z == 0.0)
            return new Vector3D();
        return fromSphericalVar(Math.pow(norm(), d), d * getPhi(), d * getThetaVar());
    }

    public static Vector3D fromSpherical(double r, double phi, double theta) {
        double rho = r * Math.sin(theta);
        return new Vector3D(rho * Math.cos(phi), rho * Math.sin(phi), r * Math.cos(theta));
    }

    public static Vector3D fromSphericalVar(double r, double phi, double theta) {
        double rho = r * Math.cos(theta);
        return new Vector3D(rho * Math.cos(phi), rho * Math.sin(phi), r * Math.sin(theta));
    }

    public Vector3D getZero() {
        return ORIGIN;
    }

}
