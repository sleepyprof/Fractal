package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.controller.FractalIteratorManager;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Coordinate;

public interface FractalIterateModel<D> extends FractalModel<D>, FractalIteratorManager<Coordinate> {

    public FractalIteratorFactory<Coordinate> getIteratorFactory();
    public int getMaxiter();

}
