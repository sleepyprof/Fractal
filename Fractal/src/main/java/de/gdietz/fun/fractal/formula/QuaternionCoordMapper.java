package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Quaternion;
import de.gdietz.fun.fractal.util.Coordinate;

public class QuaternionCoordMapper implements ParamCoordMapper<Quaternion, Quaternion, Coordinate> {

    private final Quaternion param;

    public QuaternionCoordMapper(Quaternion param) {
        this.param = param;
    }

    public QuaternionCoordMapper() {
        this(Quaternion.ZERO);
    }

    public Quaternion get(Coordinate c, Coordinate p) {
        return new Quaternion(c.getX(), c.getY(), p.getX(), p.getY());
    }

    public Quaternion getParam(Coordinate c, Coordinate p) {
        return param;
    }

    public Coordinate coord(Quaternion q) {
        return new Coordinate(q.getReal(), q.imagNorm());
    }

}
