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
        return new ApfelMetaIteratorFactory<>(mapper, new ApfelPreciseFunctionFactory<>(function, maxScale), new BoundaryTest<>(bound));
    }

    public List<ApfelFunction<BigComplex>> getCollection() {
        List<ApfelFunction<BigComplex>> functions = new ArrayList<>();

        for (int n = 2; n <= 8; n++)
            functions.add(new ApfelFunctionPow<>(n));

        for (int n = 2; n <= 6; n++)
            for (int m = 1; m < n; m++)
                functions.add(new ApfelFunctionPowSum<>(n, m));

        for (int n = 1; n <= 5; n++)
            for (int m = 1; m <= 5; m++)
                if (n != m) {
                    functions.add(new ApfelFunctionMixedPow<>(n, BigComplex.MINUS_ONE, m));
                    functions.add(new ApfelFunctionMixedPow<>(n, BigComplex.I, m));
                }

        return functions;
    }

    public String getName() {
        return "complex";
    }

}
