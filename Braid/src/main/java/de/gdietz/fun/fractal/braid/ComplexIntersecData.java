package de.gdietz.fun.fractal.braid;

import de.gdietz.fun.fractal.util.Complex;

public class ComplexIntersecData implements IntersecData<Complex> {

	private Complex s11, s22, s33, s12, s13, s23;

	public ComplexIntersecData(Complex s11, Complex s22, Complex s33, Complex s12, Complex s13, Complex s23) {
		this.s11 = s11;
		this.s22 = s22;
		this.s33 = s33;
		this.s12 = s12;
		this.s13 = s13;
		this.s23 = s23;
	}

	public ComplexIntersecData(Complex s12, Complex s13, Complex s23) {
		this(new Complex(-2.0), new Complex(-2.0), new Complex(-2.0), s12, s13, s23);
	}

	public ComplexIntersecData() {
		this(new Complex(), new Complex(), new Complex());
	}

	public Complex getS11() {
		return s11;
	}

	public Complex getS22() {
		return s22;
	}

	public Complex getS33() {
		return s33;
	}

	public Complex getS12() {
		return s12;
	}

	public Complex getS13() {
		return s13;
	}

	public Complex getS23() {
		return s23;
	}

	public ComplexIntersecData sigma1() {
		return new ComplexIntersecData(s22, s11, s33, s12.negate(), s23.subtract(s12.multiply(s13).multiply(2.0).divide(s11)), s13);
	}

	public ComplexIntersecData sigma2() {
		return new ComplexIntersecData(s11, s33, s22, s13.subtract(s12.multiply(s23).multiply(2.0).divide(s22)), s12, s23.negate());
	}

    public ComplexIntersecData sigmaInv1() {
        return new ComplexIntersecData(s22, s11, s33, s12.negate(), s23, s13.subtract(s12.multiply(s23).multiply(2.0).divide(s22)));
    }

    public ComplexIntersecData sigmaInv2() {
        return new ComplexIntersecData(s11, s33, s22, s13, s12.subtract(s23.multiply(s13).multiply(2.0).divide(s33)), s23.negate());
    }

	public double normSqr() {
		return s12.normSqr() + s13.normSqr() + s23.normSqr();
	}

	public void set(int i, int j, Complex value) {
		if (i < 1 || j < 1 || i > 3 || j > 3)
			throw new IndexOutOfBoundsException();
		if (i == 1) {
			if (j == 1) s11 = value;
			else if (j == 2) s12 = value;
			else s13 = value;
		} else if (i == 2) {
			if (j == 1) s12 = value;
			else if (j == 2) s22 = value;
			else s23 = value;
		} else {
			if (j == 1) s13 = value;
			else if (j == 2) s23 = value;
			else s33 = value;
		}
	}

	public Complex get(int i, int j) {
		if (i < 1 || j < 1 || i > 3 || j > 3)
			throw new IndexOutOfBoundsException();
		if (i == 1) {
			if (j == 1) return s11;
			if (j == 2) return s12;
			return s13;
		}
		if (i == 2) {
			if (j == 1) return s12;
			if (j == 2) return s22;
			return s23;
		}
		if (j == 1) return s13;
		if (j == 2) return s23;
		return s33;
	}

	public ComplexIntersecData sigma(int i) {
		if (i < 0)
            return sigmaInv(-i);
        if (i < 1 || i >= 3)
			throw new IndexOutOfBoundsException();
		return i==1 ? sigma1() : sigma2();
	}

    public ComplexIntersecData sigmaInv(int i) {
        if (i < 0)
            return sigma(-i);
        if (i < 1 || i >= 3)
            throw new IndexOutOfBoundsException();
        return i==1 ? sigmaInv1() : sigmaInv2();
    }

	public boolean isDefinite() {
		return false;
	}

}
