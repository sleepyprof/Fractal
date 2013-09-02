package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Matrix3D;
import de.gdietz.fun.fractal.util.UnitVector3D;
import de.gdietz.fun.fractal.util.Vector3D;

public class ApfelFunctionVector3DVarRot implements ApfelFunction<Vector3D> {

    private static final Vector3D X_AXIS = new Vector3D(1.0, 0.0, 0.0);

    private final double rotFactor;
    private final double exponent;

    public ApfelFunctionVector3DVarRot(double rotFactor, double exponent) {
        this.rotFactor = rotFactor;
        this.exponent = exponent;
    }

    public Vector3D func(Vector3D z, Vector3D c) {
        try {
            UnitVector3D axis = new UnitVector3D(z);
            double alpha = rotFactor * z.getTheta();
            Matrix3D rot = axis.rotationMatrix(alpha);
            return new Vector3D(rot.operate(X_AXIS).multiply(Math.pow(z.norm(), exponent))).add(c);
        } catch(ArithmeticException e) {
            return c;
        }
    }

    public String toString() {
        return "varrot(" + rotFactor + ", " + exponent + ")";
    }

}
