package de.gdietz.fun.fractal.util;

public class Matrix3D implements LinearOperator<Coordinate3D, Matrix3D> {

    public static final Matrix3D ZERO = new Matrix3D();
    public static final Matrix3D UNIT = new Matrix3D(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0);
    
    private final double a11, a12, a13;
    private final double a21, a22, a23;
    private final double a31, a32, a33;

    public Matrix3D(double a11, double a12, double a13, double a21, double a22, double a23, double a31, double a32, double a33) {
        this.a11 = a11;
        this.a12 = a12;
        this.a13 = a13;
        this.a21 = a21;
        this.a22 = a22;
        this.a23 = a23;
        this.a31 = a31;
        this.a32 = a32;
        this.a33 = a33;
    }

    public Matrix3D() {
        this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    public Matrix3D(Coordinate3D col1, Coordinate3D col2, Coordinate3D col3) {
        this(col1.x, col2.x, col3.x, col1.y, col2.y, col3.y, col1.z, col2.z, col3.z);
    }

    public Matrix3D(Matrix3D m) {
        a11 = m.a11;
        a12 = m.a12;
        a13 = m.a13;
        a21 = m.a21;
        a22 = m.a22;
        a23 = m.a23;
        a31 = m.a31;
        a32 = m.a32;
        a33 = m.a33;
    }

    public Coordinate3D operate(Coordinate3D v) {
        return new Coordinate3D(
                a11 * v.x + a12 * v.y + a13 * v.z,
                a21 * v.x + a22 * v.y + a23 * v.z,
                a31 * v.x + a32 * v.y + a33 * v.z);
    }

    public Matrix3D compose(Matrix3D m) {
        return new Matrix3D(
                a11 * m.a11 + a12 * m.a21 + a13 * m.a31,
                a11 * m.a12 + a12 * m.a22 + a13 * m.a32,
                a11 * m.a13 + a12 * m.a23 + a13 * m.a33,
                a21 * m.a11 + a22 * m.a21 + a23 * m.a31,
                a21 * m.a12 + a22 * m.a22 + a23 * m.a32,
                a21 * m.a13 + a22 * m.a23 + a23 * m.a33,
                a31 * m.a11 + a32 * m.a21 + a33 * m.a31,
                a31 * m.a12 + a32 * m.a22 + a33 * m.a32,
                a31 * m.a13 + a32 * m.a23 + a33 * m.a33);
    }

    public Matrix3D add(Matrix3D m) {
        return new Matrix3D(
                a11 + m.a11, a12 + m.a12, a13 + m.a13,
                a21 + m.a21, a22 + m.a22, a23 + m.a23,
                a31 + m.a31, a32 + m.a32, a33 + m.a33);
    }

    public Matrix3D multiply(double r) {
        return new Matrix3D(
                a11 * r, a12 * r, a13 * r,
                a21 * r, a22 * r, a23 * r,
                a31 * r, a32 * r, a33 * r);
    }

    public double det() {
        return a11 * a22 * a33 + a12 * a23 * a31 + a13 * a21 * a32
                - a13 * a22 * a31 - a12 * a21 * a33 - a11 * a23 * a32;
    }

    public Matrix3D quasiInverse() {
        return new Matrix3D(a22 * a33 - a23 * a32, a13 * a32 - a12 * a33, a12 * a23 - a13 * a22,
                a23 * a31 - a21 * a33, a11 * a33 - a13 * a31, a13 * a21 - a11 * a23,
                a21 * a32 - a22 * a31, a12 * a31 - a11 * a32, a11 * a22 - a12 * a21);
    }

    public Matrix3D inverse() throws NonInvertibleOperatorException {
        double det = det();
        if (det == 0.0)
            throw new NonInvertibleOperatorException("Singular matrix cannot be inverted.");
        return quasiInverse().multiply(1.0 / det);
    }

    public String toString() {
        return String.format("⎛ %8.4f %8.4f %8.4f ⎞\n⎜ %8.4f %8.4f %8.4f ⎟\n⎝ %8.4f %8.4f %8.4f ⎠",
                a11, a12, a13, a21, a22, a23, a31, a32, a33);
    }

}
