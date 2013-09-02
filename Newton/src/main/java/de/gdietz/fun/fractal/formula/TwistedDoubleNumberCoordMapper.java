package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.DoubleNumber;

public class TwistedDoubleNumberCoordMapper implements DualCoordMapper<DoubleNumber, DoubleNumber, Coordinate> {

    public DoubleNumber get(Coordinate c, Coordinate p) {
        return new DoubleNumber(p.getX());
    }

    public DoubleNumber getSecond(Coordinate c, Coordinate p) {
        return new DoubleNumber(p.getY());
    }

    public DoubleNumber getParam(Coordinate c, Coordinate p) {
        return new DoubleNumber(c.getX());
    }

    public Coordinate coord(DoubleNumber x) {
        return new Coordinate(x.getDouble(), 0.0);
    }

    public Coordinate coord(DoubleNumber x, DoubleNumber y) {
        return new Coordinate(x.getDouble(), y.getDouble());
    }

}
