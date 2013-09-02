package de.gdietz.fun.fractal.braid;

import de.gdietz.fun.fractal.formula.CoordMapper;
import de.gdietz.fun.fractal.util.Coordinate;

public class RealIntersecDataCoordMapper implements CoordMapper<IntersecData<Double>, Coordinate> {

    public RealIntersecData get(Coordinate c, Coordinate p) {
        return new RealIntersecData(c.getX(), p.getX(), c.getY());
    }

    public Coordinate coord(RealIntersecData isd) {
        return new Coordinate(isd.getS13(), isd.getS23());
    }

    public Coordinate coord(IntersecData<Double> isd) {
        return coord((RealIntersecData) isd);
    }

}