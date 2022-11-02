package de.gdietz.fun.fractal.newton;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.util.Coordinate;

public class CoordinateInfoProvider implements IterateInfoProvider<Coordinate, FractalIterator<Coordinate>> {

    public IterateInfo<Coordinate> get(FractalIterator<Coordinate> iterator) {
        return new IterateInfo<>(iterator.getCoordinate(), iterator.isValid());
    }
    
}
