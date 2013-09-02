package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Tuple;

public interface FractalIteratorFactory<T extends Tuple<T>> {

	public FractalIterator<T> get(T c, T p);

}
