package de.gdietz.fun.fractal.braid;

import de.gdietz.fun.fractal.formula.CoordMapper;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.Coordinate;

public class ComplexIntersecDataCoordMapper implements CoordMapper<IntersecData<Complex>, Coordinate> {

    public ComplexIntersecData get(Coordinate c, Coordinate p) {
        return new ComplexIntersecData(new Complex(c), Complex.ZERO, new Complex(p).add(1.0));
    }

    public Coordinate coord(ComplexIntersecData isd) {
        return isd.getS12();
    }

    public Coordinate coord(IntersecData<Complex> isd) {
        return coord((ComplexIntersecData) isd);
    }

}
