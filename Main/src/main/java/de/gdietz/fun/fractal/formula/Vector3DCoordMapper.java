package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Vector3D;

public class Vector3DCoordMapper implements ParamCoordMapper<Vector3D, Vector3D, Coordinate> {

    private final double cz;
    private final double pz;

    public Vector3DCoordMapper(double cz, double pz) {
        this.cz = cz;
        this.pz = pz;
    }

    public Vector3DCoordMapper() {
        this(0.0, 0.0);
    }

    public Vector3D get(Coordinate c, Coordinate p) {
        return new Vector3D(c.getX(), c.getY(), cz);
    }

    public Vector3D getParam(Coordinate c, Coordinate p) {
        return new Vector3D(p.getX(), p.getY(), pz);
    }

    public Coordinate coord(Vector3D v) {
        return new Coordinate(v.getX(), v.getY());
    }

}