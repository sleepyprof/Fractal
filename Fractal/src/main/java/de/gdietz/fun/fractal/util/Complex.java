package de.gdietz.fun.fractal.util;

import java.text.NumberFormat;

public class Complex extends Coordinate implements NormedNumber<Complex> {

    public static final Complex ZERO = new Complex();
    public static final Complex ONE = new Complex(1.0);
    public static final Complex I = new Complex(0.0, 1.0);
    public static final Complex MINUS_ONE = new Complex(-1.0);

    public static final Complex NaN = new Complex(Double.NaN, Double.NaN);

    public Complex() {
	}

	public Complex(double real) {
		super(real, 0.0);
	}

	public Complex(double real, double imag) {
		super(real, imag);
	}

	public Complex(Coordinate c) {
		super(c);
	}

	public double getReal() {return x; }
	public double getImag() {return y; }

    public Complex negate() {
		return new Complex(-x, -y);
	}

    public Complex add(double r) {
        return new Complex(x + r, y);
    }

    public Complex add(Complex c) {
		return new Complex(x + c.x, y + c.y);
	}

    public Complex subtract(double r) {
        return new Complex(x - r, y);
    }

	public Complex subtract(Complex c) {
		return new Complex(x - c.x, y - c.y);
	}

	public Complex multiply(double r) {
		return new Complex(r * x, r * y);
	}

	public Complex multiply(Complex c) {
		return new Complex(x * c.x - y * c.y, x * c.y + y * c.x);
	}

	public Complex sqr() {
        double h = x * y;
		return new Complex(x * x - y * y, h + h);
	}

	public Complex cube() {
		double x2 = x * x;
		double y2 = y * y;
		return new Complex(x * (x2 - 3.0 * y2), (3.0 * x2 - y2) * y);
	}

	public Complex inverse() {
		double den=normSqr();
		return new Complex(x / den, -y / den);
	}

    public Complex divide(double r) {
        return new Complex(x / r, y / r);
    }

    public Complex divide(Complex c) {
	    double den=c.normSqr();
		return new Complex((x * c.x + y * c.y) / den, (y * c.x - x * c.y) / den);
	}

	public Complex conjugate() {
	    return new Complex(x, -y);
	}

	public Complex pow(int n) {
		if (n==2) return sqr();
		if (n==3) return cube();
		if (n==-1) return inverse();
		if (n==1) return this;
		if (n==0) return Complex.ONE;
		if (n<0) return inverse().pow(-n);
		if (n % 3 == 0) return cube().pow(n / 3);
		if (n % 2 == 0) return sqr().pow(n / 2);
		return multiply(sqr().pow((n-1) / 2));
	}

    public Complex exp() {
    	double er=Math.exp(x);
	    return new Complex(er * Math.cos(y), er * Math.sin(y));
    }

    public Complex log() {
        return new Complex(Math.log(norm()), arg());
    }

	public Complex pow(Complex c) {
        if (x == 0.0 && y == 0.0)
            return new Complex();
		return log().multiply(c).exp();
	}

    public Complex pow(double d) {
        if (x == 0.0 && y == 0.0)
            return new Complex();
        double pr = Math.pow(norm(), d);
        double pa = d * arg();
        return new Complex(pr * Math.cos(pa), pr * Math.sin(pa));
    }

    public Complex sqrt() {
	    if (y == 0.0)
		    return x >= 0.0 ? new Complex(Math.sqrt(x)) : new Complex(0.0, Math.sqrt(-x));
	    double r = norm();
	    double w = Math.sqrt(2.0 * (x + r));
	    return new Complex(0.5 * w, y / w);
    }

    @SuppressWarnings({"SuspiciousNameCombination"})
    public Complex sin() {
        return new Complex(Math.sin(x) * Math.cosh(y), Math.cos(x) * Math.sinh(y));
    }

    @SuppressWarnings({"SuspiciousNameCombination"})
    public Complex cos() {
        return new Complex(Math.cos(x) * Math.cosh(y), -Math.sin(x) * Math.sinh(y));
    }

    public Complex sinh() {
        return new Complex(Math.sinh(x) * Math.cos(y), Math.cosh(x) * Math.sin(y));
    }

    public Complex cosh() {
        return new Complex(Math.cosh(x) * Math.cos(y), Math.sinh(x) * Math.sin(y));
    }

    public Complex tan() {
        double dx = x + x;
        double dy = y + y;
        double h = Math.cos(dx) + Math.cosh(dy);
        return new Complex(Math.sin(dx) / h, Math.sinh(dy) / h);
    }

    public Complex tanh() {
        double dx = x + x;
        double dy = y + y;
        double h = Math.cosh(dx) + Math.cos(dy);
        return new Complex(Math.sinh(dx) / h, Math.sin(dy) / h);
    }

    public static Complex fromPolar(double r, double phi) {
        return new Complex(r * Math.cos(phi), r * Math.sin(phi));
    }

    public Complex getZero() {
        return ZERO;
    }

    public Complex getUnit() {
        return ONE;
    }

    public boolean isUnit() {
        return x == 1.0 && y == 0.0;
    }

    public String toString(NumberFormat nf, boolean parentheses, boolean negativeParentheses, boolean sign) {
        ComplexNumberFormatter cnf = new ComplexNumberFormatter(nf, parentheses, negativeParentheses, sign);
        cnf.addNumberString(x);
        cnf.addNumberString(y, "i");
        return cnf.toString();
    }

    public String toString(NumberFormat nf, boolean parentheses) {
        return toString(nf, parentheses, false, false);
    }

    public String toString(boolean parentheses, boolean negativeParentheses, boolean sign) {
        return toString(NumberFormat.getInstance(), parentheses, negativeParentheses, sign);
    }

    public String toString(boolean parentheses) {
        return toString(parentheses, false, false);
    }

    public String toString() {
        return toString(false);
    }

}
