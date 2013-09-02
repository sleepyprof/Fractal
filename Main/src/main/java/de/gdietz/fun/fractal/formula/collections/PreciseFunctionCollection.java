package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.BigCoordinate;

import java.util.List;

public interface PreciseFunctionCollection<X> {

    public FractalIteratorFactory<BigCoordinate> getApfelIteratorFactory(ApfelFunction<X> function, double bound, int maxScale);

    public List<ApfelFunction<X>> getCollection();

    public String getName();

}
