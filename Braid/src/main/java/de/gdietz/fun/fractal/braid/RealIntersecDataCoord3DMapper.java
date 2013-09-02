package de.gdietz.fun.fractal.braid;

import de.gdietz.fun.fractal.formula.CoordMapper;
import de.gdietz.fun.fractal.util.Coordinate3D;

public class RealIntersecDataCoord3DMapper implements CoordMapper<IntersecData<Double>, Coordinate3D> {

    public RealIntersecData get(Coordinate3D c, Coordinate3D p) {
        return new RealIntersecData(c.getX(), c.getY(), c.getZ());
    }

    public Coordinate3D coord(RealIntersecData isd) {
        return new Coordinate3D(isd.getS12(), isd.getS13(), isd.getS23());
    }

    public Coordinate3D coord(IntersecData<Double> isd) {
        return coord((RealIntersecData) isd);
    }

}