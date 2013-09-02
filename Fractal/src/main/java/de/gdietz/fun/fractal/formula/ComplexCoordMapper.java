package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Complex;

public class ComplexCoordMapper implements ParamCoordMapper<Complex, Complex, Coordinate> {

    public Complex get(Coordinate c, Coordinate p) {
        return new Complex(c);
    }

    public Complex getParam(Coordinate c, Coordinate p) {
        return new Complex(p);
    }

    public Coordinate coord(Complex c) {
        return c;
    }
    
}
