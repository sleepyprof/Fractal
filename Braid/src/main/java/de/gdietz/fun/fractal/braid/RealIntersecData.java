package de.gdietz.fun.fractal.braid;

public class RealIntersecData implements IntersecData<Double> {

	private double s11, s22, s33, s12, s13, s23;

	public RealIntersecData(double s11, double s22, double s33, double s12, double s13, double s23) {
		this.s11 = s11;
		this.s22 = s22;
		this.s33 = s33;
		this.s12 = s12;
		this.s13 = s13;
		this.s23 = s23;
	}

	public RealIntersecData(double s12, double s13, double s23) {
		this(-2.0, -2.0, -2.0, s12, s13, s23);
	}

	public RealIntersecData() {
		this(0.0, 0.0, 0.0);
	}

	public double getS11() {
		return s11;
	}

	public double getS22() {
		return s22;
	}

	public double getS33() {
		return s33;
	}

	public double getS12() {
		return s12;
	}

	public double getS13() {
		return s13;
	}

	public double getS23() {
		return s23;
	}

	public RealIntersecData sigma1() {
		return new RealIntersecData(s22, s11, s33, -s12, s23 - 2.0 / s11 * s12 * s13, s13);
	}

	public RealIntersecData sigma2() {
		return new RealIntersecData(s11, s33, s22, s13 - 2.0 / s22 * s12 * s23, s12, -s23);
	}

    public RealIntersecData sigmaInv1() {
        return new RealIntersecData(s22, s11, s33, -s12, s23, s13 - 2.0 / s22 * s12 * s23);
    }

    public RealIntersecData sigmaInv2() {
        return new RealIntersecData(s11, s33, s22, s13, s12 - 2.0 / s33 * s23 * s13, -s23);
    }

	public double normSqr() {
		return s12 * s12 + s13 * s13 + s23 * s23;
	}

	public double det() {
		return s11 * s22 * s33 + 2.0 * s12 * s13 * s23 - s11 * s23 * s23 - s22 * s13 * s13 - s33 * s12 * s12;
	}

	public boolean isPosDef() {
		return s11 > 0.0 && s22 > 0.0 && s33 > 0.0 &&
				s11 * s22 > s12 * s12 && s11 * s33 > s13 * s13 && s22 * s33 > s23 * s23 &&
				det() > 0.0;
	}

	public boolean isNegDef() {
		return s11 < 0.0 && s22 < 0.0 && s33 < 0.0 &&
				s11 * s22 > s12 * s12 && s11 * s33 > s13 * s13 && s22 * s33 > s23 * s23 &&
				det() < 0.0;
	}

	public void set(int i, int j, Double value) {
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

	public Double get(int i, int j) {
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

	public RealIntersecData sigma(int i) {
		if (i < 0)
            return sigmaInv(-i);
        if (i < 1 || i >= 3)
			throw new IndexOutOfBoundsException();
		return i==1 ? sigma1() : sigma2();
	}

    public RealIntersecData sigmaInv(int i) {
        if (i < 0)
            return sigma(-i);
        if (i < 1 || i >= 3)
            throw new IndexOutOfBoundsException();
        return i==1 ? sigmaInv1() : sigmaInv2();
    }

	public boolean isDefinite() {
		return isPosDef() || isNegDef();
	}

    public String toString() {
        return String.format("⎛ %8.4f %8.4f %8.4f ⎞\n⎜ %8.4f %8.4f %8.4f ⎟\n⎝ %8.4f %8.4f %8.4f ⎠",
                s11, s12, s13, s12, s22, s23, s13, s23, s33);
    }

}
