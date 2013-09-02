package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.formula.formulas.*;
import de.gdietz.fun.fractal.formula.meta.ApfelMetaIteratorFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelPreciseFunctionFactory;
import de.gdietz.fun.fractal.util.*;

import java.util.ArrayList;
import java.util.List;

public class BigComplexFunctionCollection implements PreciseFunctionCollection<BigComplex> {

    public static final ParamCoordMapper<BigComplex, BigComplex, BigCoordinate> mapper = new BigComplexCoordMapper();

    public FractalIteratorFactory<BigCoordinate> getApfelIteratorFactory(ApfelFunction<BigComplex> function, double bound, int maxScale) {
        return new ApfelMetaIteratorFactory<BigComplex, BigCoordinate>(mapper, new ApfelPreciseFunctionFactory<BigComplex>(function, maxScale), new BoundaryTest<BigComplex>(bound));
    }

    public List<ApfelFunction<BigComplex>> getCollection() {
        List<ApfelFunction<BigComplex>> functions = new ArrayList<ApfelFunction<BigComplex>>();

        for (int n = 2; n <= 8; n++)
            functions.add(new ApfelFunctionPow<BigComplex>(n));

        for (int n = 2; n <= 6; n++)
            for (int m = 1; m < n; m++)
                functions.add(new ApfelFunctionPowSum<BigComplex>(n, m));

        for (int n = 1; n <= 5; n++)
            for (int m = 1; m <= 5; m++)
                if (n != m) {
                    functions.add(new ApfelFunctionMixedPow<BigComplex>(n, BigComplex.MINUS_ONE, m));
                    functions.add(new ApfelFunctionMixedPow<BigComplex>(n, BigComplex.I, m));
                }

        return functions;
    }

    public String getName() {
        return "complex";
    }

}
