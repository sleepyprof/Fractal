package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Tuple;

public interface FractalIteratorManager<T extends Tuple<T>> {

	public void setIteratorFactory(FractalIteratorFactory<T> iteratorFactory);
	public void setMaxiter(int maxiter);

	public void setIteratorData(FractalIteratorFactory<T> iteratorFactory, int maxiter);

}
