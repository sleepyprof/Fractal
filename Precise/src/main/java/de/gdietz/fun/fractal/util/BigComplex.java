package de.gdietz.fun.fractal.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigComplex extends BigCoordinate implements NormedNumber<BigComplex>, BigNormed<BigComplex> {

    public static final BigComplex ZERO = new BigComplex();
    public static final BigComplex ONE = new BigComplex(BigDecimal.ONE);
    public static final BigComplex I = new BigComplex(BigDecimal.ZERO, BigDecimal.ONE);
    public static final BigComplex MINUS_ONE = new BigComplex(-1.0);

    private static final BigDecimal THREE = new BigDecimal(3);

    public BigComplex() {
	}

    public BigComplex(BigDecimal real) {
        super(real, BigDecimal.ZERO);
    }

    public BigComplex(BigDecimal real, BigDecimal imag) {
        super(real, imag);
    }

    public BigComplex(BigCoordinate c) {
        super(c);
    }

	public BigComplex(double real) {
		this(new BigDecimal(real), BigDecimal.ZERO);
	}

	public BigComplex(double real, double imag) {
        super(real, imag);
	}

	public BigComplex(Coordinate c) {
		super(c);
	}

	public BigDecimal getReal() {return x; }
	public BigDecimal getImag() {return y; }

    public BigComplex negate() {
		return new BigComplex(x.negate(), y.negate());
	}

    public BigComplex add(BigDecimal r) {
        return new BigComplex(x.add(r), y);
    }

    public BigComplex add(double r) {
        return add(new BigDecimal(r));
    }

    public BigComplex add(BigComplex c) {
		return new BigComplex(x.add(c.x), y.add(c.y));
	}

    public BigComplex subtract(BigDecimal r) {
        return new BigComplex(x.subtract(r), y);
    }

    public BigComplex subtract(double r) {
        return subtract(new BigDecimal(r));
    }

    public BigComplex subtract(BigComplex c) {
		return new BigComplex(x.subtract(c.x), y.subtract(c.y));
	}

	public BigComplex multiply(BigDecimal r) {
		return new BigComplex(r.multiply(x), r.multiply(y));
	}

    public BigComplex multiply(double r) {
        return multiply(new BigDecimal(r));
    }

	public BigComplex multiply(BigComplex c) {
		return new BigComplex(x.multiply(c.x).subtract(y.multiply(c.y)), x.multiply(c.y).add(y.multiply(c.x)));
	}

	public BigComplex sqr() {
        BigDecimal h = x.multiply(y);
		return new BigComplex(x.multiply(x).subtract(y.multiply(y)), h.add(h));
	}

	public BigComplex cube() {
		BigDecimal x2 = x.multiply(x);
		BigDecimal y2 = y.multiply(y);
		return new BigComplex(x.multiply(x2.subtract(THREE.multiply(y2))), THREE.multiply(x2).subtract(y2).multiply(y));
	}

	public BigComplex inverse() {
		BigDecimal den = normSqrPrecise();
        BigDecimal factor = BigDecimal.ONE.divide(den);
		return conjugate().multiply(factor);
	}

    public BigComplex inverse(MathContext mc) {
        BigDecimal den = normSqrPrecise();
        BigDecimal factor = BigDecimal.ONE.divide(den, mc);
        return conjugate().multiply(factor);
    }

    public BigComplex divide(BigDecimal r) {
        BigDecimal factor = BigDecimal.ONE.divide(r);
        return multiply(factor);
    }

    public BigComplex divide(BigComplex c) {
        return multiply(c.inverse());
	}

    public BigComplex divide(BigDecimal r, MathContext mc) {
        BigDecimal factor = BigDecimal.ONE.divide(r, mc);
        return multiply(factor);
    }

    public BigComplex divide(BigComplex c, MathContext mc) {
        return multiply(c.inverse(mc));
	}

	public BigComplex conjugate() {
	    return new BigComplex(x, y.negate());
	}

	public BigComplex pow(int n) {
		if (n==2) return sqr();
		if (n==3) return cube();
		if (n==-1) return inverse();
		if (n==1) return this;
		if (n==0) return BigComplex.ONE;
		if (n<0) return inverse().pow(-n);
		if (n % 3 == 0) return cube().pow(n / 3);
		if (n % 2 == 0) return sqr().pow(n / 2);
		return multiply(sqr().pow((n-1) / 2));
	}

    public BigComplex getZero() {
        return ZERO;
    }

    public BigComplex getUnit() {
        return ONE;
    }

    public boolean isUnit() {
        return x.compareTo(BigDecimal.ONE) == 0 && y.compareTo(BigDecimal.ZERO) == 0;
    }

    public int scale() {
        return Math.max(x.scale(), y.scale());
    }

    public BigComplex setScale(int newScale, RoundingMode roundingMode) {
        return new BigComplex(x.setScale(newScale, roundingMode), y.setScale(newScale, roundingMode));
    }

    public BigComplex setScale(int newScale) {
        return new BigComplex(x.setScale(newScale), y.setScale(newScale));
    }

    public Complex complexValue() {
        return new Complex(x.doubleValue(), y.doubleValue());
    }

    public String toString(boolean parentheses, boolean negativeParentheses, boolean sign) {
        BigComplexNumberFormatter cnf = new BigComplexNumberFormatter(parentheses, negativeParentheses, sign);
        cnf.addNumberString(x);
        cnf.addNumberString(y, "i");
        return cnf.toString();
    }

    public String toString(boolean parentheses) {
        return toString(parentheses, false, false);
    }

    public String toString() {
        return toString(false);
    }

}
