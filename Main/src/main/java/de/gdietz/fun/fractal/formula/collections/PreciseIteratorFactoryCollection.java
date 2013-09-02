package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.BigCoordinate;

import java.util.List;

public interface PreciseIteratorFactoryCollection {

    public List<FractalIteratorFactory<BigCoordinate>> getCollection();

    public String getName();

}
