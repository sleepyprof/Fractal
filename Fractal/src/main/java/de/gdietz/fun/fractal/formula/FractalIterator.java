package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Tuple;

public interface FractalIterator<T extends Tuple<T>> {

	public boolean isValid();

	public boolean isSurvivor();

	public void iterate();

    public T getCoordinate();

}
