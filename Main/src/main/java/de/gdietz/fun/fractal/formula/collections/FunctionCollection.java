package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;

import java.util.List;

public interface FunctionCollection<X> {

    public FractalIteratorFactory<Coordinate> getApfelIteratorFactory(ApfelFunction<X> function, double bound);
    public FractalIteratorFactory<Coordinate3D> getApfel3DIteratorFactory(ApfelFunction<X> function, double bound);

    public List<ApfelFunction<X>> getCollection();

    public String getName();

}
