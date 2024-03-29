package de.gdietz.fun.fractal.newton;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.formula.collections.IteratorFactoryCollection;
import de.gdietz.fun.fractal.formula.formulas.*;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;
import de.gdietz.fun.fractal.util.DoubleNumber;

import java.util.ArrayList;
import java.util.List;

public class RealNewtonIteratorFactoryCollection implements IteratorFactoryCollection {

    public FractalIteratorFactory<Coordinate> getRealDualNewtonIteratorFactory(ApfelDerivableFunction<DoubleNumber> function) {
        return new DualNewtonIteratorFactory<>(new TwistedDoubleNumberCoordMapper(), function, new ClosedEyesTest<>());
    }

    public List<FractalIteratorFactory<Coordinate>> getCollection() {
        List<FractalIteratorFactory<Coordinate>> iterFactories = new ArrayList<>();

        for(int n = 2; n <= 8; n++)
            iterFactories.add(getRealDualNewtonIteratorFactory(new ApfelFunctionPow<>(n)));

        for (int n = 1; n <= 6; n++)
            for (int m = 1; m <= 6; m++)
                if (n != m)
                    iterFactories.add(getRealDualNewtonIteratorFactory(new ApfelFunctionMixedPow<>(n, DoubleNumber.MINUS_ONE, m)));

        return iterFactories;
    }

    public List<FractalIteratorFactory<Coordinate3D>> getCollection3D() {
        return new ArrayList<>();
    }

    public String getName() {
        return "newton real";
    }

}