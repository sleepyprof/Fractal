package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.formula.formulas.*;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;
import de.gdietz.fun.fractal.util.Quaternion;

import java.util.ArrayList;
import java.util.List;

public class QuaternionFunctionCollection implements FunctionCollection<Quaternion> {

    public static final ParamCoordMapper<Quaternion, Quaternion, Coordinate> mapper = new QuaternionCoordMapper();
    public static final ParamCoordMapper<Quaternion, Quaternion, Coordinate3D> mapper3D = new QuaternionCoord3DMapper();

    public FractalIteratorFactory<Coordinate> getApfelIteratorFactory(ApfelFunction<Quaternion> function, double bound) {
        return new ApfelIteratorFactory<>(mapper, function, new BoundaryTest<>(bound));
    }

    public FractalIteratorFactory<Coordinate3D> getApfel3DIteratorFactory(ApfelFunction<Quaternion> function, double bound) {
        return new ApfelIteratorFactory<>(mapper3D, function, new BoundaryTest<>(bound));
    }

    public List<ApfelFunction<Quaternion>> getCollection() {
        List<ApfelFunction<Quaternion>> functions = new ArrayList<>();

        for(int n = 2; n <= 8; n++) {
            functions.add(new ApfelFunctionPow<>(n));
            functions.add(new ApfelFunctionFactorPow<>(n, Quaternion.I));
            functions.add(new ApfelFunctionFactorPow<>(n, Quaternion.J));
            functions.add(new ApfelFunctionFactorPow<>(n, Quaternion.K));
            functions.add(new ApfelFunctionPowCommutator<>(n, Quaternion.I));
            functions.add(new ApfelFunctionPowCommutator<>(n, Quaternion.J));
            functions.add(new ApfelFunctionPowCommutator<>(n, Quaternion.K));
        }

        for (int n = 2; n <= 6; n++)
            for (int m = 1; m < n; m++)
                functions.add(new ApfelFunctionPowSum<>(n, m));

        for (int n = 1; n <= 5; n++)
            for (int m = 1; m <= 5; m++)
                if (n != m) {
                    functions.add(new ApfelFunctionMixedPow<>(n, Quaternion.MINUS_ONE, m));
                    functions.add(new ApfelFunctionMixedPow<>(n, Quaternion.I, m));
                    functions.add(new ApfelFunctionMixedPow<>(n, Quaternion.J, m));
                    functions.add(new ApfelFunctionMixedPow<>(n, Quaternion.K, m));
                }
        
        return functions;
    }

    public String getName() {
        return "quaternion";
    }
    
}
