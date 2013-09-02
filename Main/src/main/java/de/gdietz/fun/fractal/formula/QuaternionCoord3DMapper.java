package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Coordinate3D;
import de.gdietz.fun.fractal.util.Quaternion;

public class QuaternionCoord3DMapper implements ParamCoordMapper<Quaternion, Quaternion, Coordinate3D> {

    private final double cw;
    private final double pw;

    public QuaternionCoord3DMapper(double cw, double pw) {
        this.cw = cw;
        this.pw = pw;
    }

    public QuaternionCoord3DMapper() {
        this(0.0, 0.0);
    }

    public Quaternion get(Coordinate3D c, Coordinate3D p) {
        return new Quaternion(c.getX(), c.getY(), c.getZ(), cw);
    }

    public Quaternion getParam(Coordinate3D c, Coordinate3D p) {
        return new Quaternion(p.getX(), p.getY(), p.getZ(), pw);
    }

    public Coordinate3D coord(Quaternion q) {
        return new Coordinate3D(q.getReal(), q.getImagI(), q.getImagJ());
    }

}
