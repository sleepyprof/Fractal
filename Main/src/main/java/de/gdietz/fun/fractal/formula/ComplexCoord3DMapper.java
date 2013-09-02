package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.Coordinate3D;

public class ComplexCoord3DMapper implements ParamCoordMapper<Complex, Complex, Coordinate3D> {

    public Complex get(Coordinate3D c, Coordinate3D p) {
        return new Complex(c.getX() + p.getZ(), c.getY());
    }

    public Complex getParam(Coordinate3D c, Coordinate3D p) {
        return new Complex(p.getX() + c.getZ(), p.getY());
    }

    public Coordinate3D coord(Complex c) {
        return new Coordinate3D(c.getX(), c.getY(), 0.0);
    }

}
