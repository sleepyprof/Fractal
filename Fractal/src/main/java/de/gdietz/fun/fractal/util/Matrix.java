package de.gdietz.fun.fractal.util;

public class Matrix implements LinearOperator<Coordinate, Matrix> {

    public static final Matrix ZERO = new Matrix();
    public static final Matrix UNIT = new Matrix(1.0, 0.0, 0.0, 1.0);
    
    private final double a, b, c, d;

    public Matrix(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Matrix() {
        this(0.0, 0.0, 0.0, 0.0);
    }

    public Matrix(Coordinate col1, Coordinate col2) {
        this(col1.x, col2.x, col1.y, col2.y);
    }
    
    public Matrix(Matrix m) {
        a = m.a;
        b = m.b;
        c = m.c;
        d = m.d;
    }

    public Coordinate operate(Coordinate v) {
        return new Coordinate(a * v.x + b * v.y, c * v.x + d * v.y);
    }

    public Matrix compose(Matrix m) {
        return new Matrix(a * m.a + b * m.c, a * m.b + b * m.d, c * m.a + d * m.c, c * m.b + d * m.d);
    }

    public Matrix add(Matrix m) {
        return new Matrix(a + m.a, b + m.b, c + m.c, d + m.d);
    }

    public Matrix multiply(double r) {
        return new Matrix(a * r, b * r, c * r, d * r);
    }

    public double det() {
        return a * d - b * c;
    }

    public Matrix quasiInverse() {
        return new Matrix(d, -b, -c, a);
    }

    public Matrix inverse() throws NonInvertibleOperatorException {
        double det = det();
        if (det == 0.0)
            throw new NonInvertibleOperatorException("Singular matrix cannot be inverted.");
        return quasiInverse().multiply(1.0 / det);
    }

    public String toString() {
        return String.format("⎛ %8.4f %8.4f ⎞\n⎝ %8.4f %8.4f ⎠", a, b, c, d);
    }

}
