package de.gdietz.fun.fractal.util;

import de.gdietz.util.NumberFormatHelper;

import java.text.NumberFormat;

public class Quaternion extends Coordinate4D implements NormedNumber<Quaternion> {

    public static final Quaternion ZERO = new Quaternion();
    public static final Quaternion ONE = new Quaternion(1.0);
    public static final Quaternion I = new Quaternion(0.0, 1.0);
    public static final Quaternion J = new Quaternion(0.0, 0.0, 1.0, 0.0);
    public static final Quaternion K = new Quaternion(0.0, 0.0, 0.0, 1.0);
    public static final Quaternion MINUS_ONE = new Quaternion(-1.0);

    public static final Quaternion NaN = new Quaternion(Double.NaN, Double.NaN, Double.NaN, Double.NaN);

    public Quaternion() {
    }

    public Quaternion(double x) {
        super(x, 0.0, 0.0, 0.0);
    }

    public Quaternion(double x, double y) {
        super(x, y, 0.0, 0.0);
    }

    public Quaternion(double x, double y, double yj, double yk) {
        super(x, y, yj, yk);
    }

    public Quaternion(Coordinate c) {
        this(c.x, c.y);
    }

    public Quaternion(Quaternion q) {
        super(q);
    }

    public double getReal() {return x; }
    public double getImagI() {return y; }
    public double getImagJ() {return z; }
    public double getImagK() {return w; }

    public double imagNormSqr() {
		return y * y + z * z + w * w;
	}

	public double imagNorm() {
		return Math.sqrt(imagNormSqr());
	}

    public Quaternion negate() {
        return new Quaternion(-x, -y, -z, -w);
    }

    public Quaternion add(double r) {
        return new Quaternion(x + r, y, z, w);
    }

    public Quaternion add(Quaternion q) {
        return new Quaternion(x + q.x, y + q.y, z + q.z, w + q.w);
    }

    public Quaternion subtract(double r) {
        return new Quaternion(x - r, y, z, w);
    }

    public Quaternion subtract(Quaternion q) {
        return new Quaternion(x - q.x, y - q.y, z - q.z, w - q.w);
    }

    public Quaternion multiply(double r) {
        return new Quaternion(r * x, r * y, r * z, r * w);
    }

    public Quaternion multiply(Quaternion q) {
        return new Quaternion(x * q.x - y * q.y - z * q.z - w * q.w,
                x * q.y + y * q.x + z * q.w - w * q.z,
                x * q.z + z * q.x + w * q.y - y * q.w,
                x * q.w + w * q.x + y * q.z - z * q.y);
    }

    public Quaternion multiplySwitched(Quaternion q) {
        return q.multiply(this);    
    }

    public Quaternion sqr() {
        double hi = x * y;
        double hj = x * z;
        double hk = x * w;
        return new Quaternion(x * x - y * y - z * z - w * w, hi + hi, hj + hj, hk + hk);
    }

    public Quaternion cube() {
        double x2 = x * x;
        double y2 = y * y;
        double z2 = z * z;
        double w2 = w * w;
        double yy2 = y2 + z2 + w2;
        return new Quaternion(x * (x2 - 3.0 * yy2), (3.0 * x2 - yy2) * y, (3.0 * x2 - yy2) * z, (3.0 * x2 - yy2) * w);
    }

    public Quaternion inverse() {
        double den=normSqr();
        return new Quaternion(x / den, -y / den, -z / den, -w / den);
    }

    public Quaternion divide(double r) {
        return new Quaternion(x / r, y / r, z / r, w / r);
    }

    public Quaternion divide(Quaternion q) {
        return multiply(q.inverse());
    }

    public Quaternion divideSwitched(Quaternion q) {
        return q.inverse().multiply(this);
    }

    public Quaternion conjugate() {
        return new Quaternion(x, -y, -z, -w);
    }

    public Quaternion pow(int n) {
        if (n==2) return sqr();
        if (n==3) return cube();
        if (n==-1) return inverse();
        if (n==1) return this;
        if (n==0) return Quaternion.ONE;
        if (n<0) return inverse().pow(-n);
        if (n % 3 == 0) return cube().pow(n / 3);
        if (n % 2 == 0) return sqr().pow(n / 2);
        return multiply(sqr().pow((n-1) / 2));
    }

    public Quaternion getZero() {
        return ZERO;
    }

    public Quaternion getUnit() {
        return ONE;
    }

    public boolean isUnit() {
        return x == 1.0 && y == 0.0 && z == 0.0 && w == 0.0;
    }

    public String toString(NumberFormat nf, boolean parentheses, boolean negativeParentheses, boolean sign) {
        ComplexNumberFormatter cnf = new ComplexNumberFormatter(nf, parentheses, negativeParentheses, sign);
        cnf.addNumberString(x);
        cnf.addNumberString(y, "i");
        cnf.addNumberString(z, "j");
        cnf.addNumberString(w, "k");
        return cnf.toString();
    }

    public String toString(NumberFormat nf, boolean parentheses) {
        return toString(nf, parentheses, false, false);
    }

    public String toString(boolean parentheses, boolean negativeParentheses, boolean sign) {
        return toString(NumberFormatHelper.getDefaultNumberFormat(), parentheses, negativeParentheses, sign);
    }

    public String toString(boolean parentheses) {
        return toString(parentheses, false, false);
    }

    public String toString() {
        return toString(false);
    }

}

