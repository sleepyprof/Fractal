package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.formula.formulas.ApfelFunctionPow;
import de.gdietz.fun.fractal.formula.formulas.ApfelFunctionPowSum;
import de.gdietz.fun.fractal.formula.formulas.ApfelFunctionVector3DRot;
import de.gdietz.fun.fractal.formula.formulas.ApfelFunctionVector3DVarRot;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;
import de.gdietz.fun.fractal.util.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Vector3DFunctionCollection implements FunctionCollection<Vector3D> {

    public static final ParamCoordMapper<Vector3D, Vector3D, Coordinate> mapper = new Vector3DCoordMapper2();
    public static final ParamCoordMapper<Vector3D, Vector3D, Coordinate3D> mapper3D = new Vector3DCoord3DMapper();

    public FractalIteratorFactory<Coordinate> getApfelIteratorFactory(ApfelFunction<Vector3D> function, double bound) {
		return new ApfelIteratorFactory<>(mapper, function, new BoundaryTest<>(bound));
    }

    public FractalIteratorFactory<Coordinate3D> getApfel3DIteratorFactory(ApfelFunction<Vector3D> function, double bound) {
        return new ApfelIteratorFactory<>(mapper3D, function, new BoundaryTest<>(bound));
    }

    public static class ApfelFunctionVectorExperimental implements ApfelFunction<Vector3D> {

        private int izx;
        private int izy;
        private int izz;
        
        public ApfelFunctionVectorExperimental(int number) {
            int n = number;
            izx = ((n + 3) % 7) - 3;
            n = n / 7;
            izy = ((n + 3) % 7) - 3;
            n = n / 7;
            izz = ((n + 3) % 7) - 3;
        }
        
        public Vector3D func(Vector3D z, Vector3D c) {
            double zx = z.getX();
            double zy = z.getY();
            double zz = z.getZ();
            double cx = c.getX();
            double cy = c.getY();
            double cz = c.getZ();

            double[] r = new double[3];
            
            r[0] = zx * zx - zy * zy;
            r[1] = 2.0 * zx * zy;
            r[2] = 0.0;
            
            if (izx != 0) {
                double p = 2.0 * zx * zz;
                if (izx > 0)
                    r[izx - 1] += p;
                else
                    r[-izx - 1] -= p;
            }

            if (izy != 0) {
                double p = 2.0 * zy * zz;
                if (izy > 0)
                    r[izy - 1] += p;
                else
                    r[-izy - 1] -= p;
            }

            if (izz != 0) {
                double p = zz * zz;
                if (izz > 0)
                    r[izz - 1] += p;
                else
                    r[-izz - 1] -= p;
            }

            r[0] += cx;
            r[1] += cy;
            r[2] += cz;

            return new Vector3D(r[0], r[1], r[2]);
        }

        public String toString() {
            return "experimental(" + izx + ", " + izy + ", " + izz + ")";
        }

    }

    public List<ApfelFunction<Vector3D>> getCollection() {
        List<ApfelFunction<Vector3D>> functions = new ArrayList<>();

        for(int n = 2; n <= 8; n++)
            functions.add(new ApfelFunctionPow<>(n));

        for (int n = 2; n <= 6; n++)
            for (int m = 1; m < n; m++)
                functions.add(new ApfelFunctionPowSum<>(n, m));

        functions.add(new ApfelFunctionVector3DRot(Math.PI, 2.0));
        functions.add(new ApfelFunctionVector3DRot(Math.PI / 2.0, 2.0));
        functions.add(new ApfelFunctionVector3DRot(Math.PI, 3.0));

        functions.add(new ApfelFunctionVector3DVarRot(2.0, 2.0));
        functions.add(new ApfelFunctionVector3DVarRot(1.0, 2.0));
        functions.add(new ApfelFunctionVector3DVarRot(2.0, 3.0));

        for (int i = 0; i < 343; i++)
            functions.add(new ApfelFunctionVectorExperimental(i));
        
        return functions;
    }

    public String getName() {
        return "vector";
    }

}