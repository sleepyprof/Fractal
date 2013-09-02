package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;

import java.util.List;

public interface IteratorFactoryCollection {

    public List<FractalIteratorFactory<Coordinate>> getCollection();
    public List<FractalIteratorFactory<Coordinate3D>> getCollection3D();

    public String getName();

}
