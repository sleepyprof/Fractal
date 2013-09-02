package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.controller.FractalIteratorManager;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.BigCoordinate;

public interface FractalPreciseIterateModel<D> extends FractalPreciseModel<D>, FractalIteratorManager<BigCoordinate> {

    public FractalIteratorFactory<BigCoordinate> getIteratorFactory();
    public int getMaxiter();

}
