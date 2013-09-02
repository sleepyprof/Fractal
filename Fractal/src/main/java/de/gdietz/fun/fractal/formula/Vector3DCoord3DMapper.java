package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Coordinate3D;
import de.gdietz.fun.fractal.util.Vector3D;

public class Vector3DCoord3DMapper implements ParamCoordMapper<Vector3D, Vector3D, Coordinate3D> {

    public Vector3D get(Coordinate3D c, Coordinate3D p) {
        return new Vector3D(c);
    }

    public Vector3D getParam(Coordinate3D c, Coordinate3D p) {
        return new Vector3D(p);
    }

    public Coordinate3D coord(Vector3D v) {
        return v;
    }

}
