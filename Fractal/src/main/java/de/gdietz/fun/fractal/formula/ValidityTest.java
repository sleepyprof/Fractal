package de.gdietz.fun.fractal.formula;

public interface ValidityTest<X> {

    public boolean isValid(X x);

	public boolean isSurvivor(X x);

}
