package de.gdietz.fun.fractal.util;

public class UnitVector3D {

    public static final UnitVector3D X_AXIS = new UnitVector3D(1.0, 0.0, 0.0);
    public static final UnitVector3D Y_AXIS = new UnitVector3D(0.0, 1.0, 0.0);
    public static final UnitVector3D Z_AXIS = new UnitVector3D(0.0, 0.0, 1.0);

    protected final double x, y, z;

    private UnitVector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public UnitVector3D(Coordinate3D c) {
        double normSqr = c.normSqr();
        if (normSqr == 0.0)
            throw new ArithmeticException("Cannot convert zero length vector to unit vector");
        if (normSqr != 1.0)
            c = c.multiply(1.0 / Math.sqrt(normSqr));
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

    public Matrix3D rotationMatrix(double alpha) {
        double sin = Math.sin(alpha);
        double cos = Math.cos(alpha);
        double mcos = 1.0 - cos;
        return new Matrix3D(
                cos + x * x * mcos, x * y * mcos - z * sin, x * z * mcos + y * sin,
                y * x * mcos + z * sin, cos + y * y * mcos, y * z * mcos - x * sin,
                z * x * mcos - y * sin, z * y * mcos + x * sin, cos + z * z * mcos);
    }

}
