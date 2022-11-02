package de.gdietz.fun.fractal.newton;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.formula.collections.IteratorFactoryCollection;
import de.gdietz.fun.fractal.formula.formulas.*;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;

import java.util.ArrayList;
import java.util.List;

public class NewtonIteratorFactoryCollection implements IteratorFactoryCollection {

    public FractalIteratorFactory<Coordinate> getNewtonIteratorFactory(ApfelDerivableFunction<Complex> function) {
        return new ApfelIteratorFactory<>(new ComplexCoordMapper(), new NewtonFunction<>(function), new ClosedEyesTest<>());
    }

    public FractalIteratorFactory<Coordinate> getDualNewtonIteratorFactory(ApfelDerivableFunction<Complex> function, Complex value) {
        return new DualNewtonIteratorFactory<>(new TwistedDualCoordMapper<>(new ComplexCoordMapper(), value),
                function, new ClosedEyesTest<>());
    }

    public List<FractalIteratorFactory<Coordinate>> getCollection() {
        List<FractalIteratorFactory<Coordinate>> iterFactories = new ArrayList<>();

        for(int n = 2; n <= 8; n++)
            iterFactories.add(getNewtonIteratorFactory(new ApfelFunctionPow<>(n)));

        for(int n = 2; n <= 6; n++)
            for(int m = 1; m < n; m++)
                iterFactories.add(getNewtonIteratorFactory(new ApfelFunctionPowSum<>(n, m)));

        for (int n = 1; n <= 6; n++)
            for (int m = 1; m <= 6; m++)
                if (n != m)
                    iterFactories.add(getNewtonIteratorFactory(new ApfelFunctionMixedPow<>(n, Complex.MINUS_ONE, m)));

        iterFactories.add(getNewtonIteratorFactory(new ApfelFunctionSin()));
        iterFactories.add(getNewtonIteratorFactory(new ApfelFunctionCos()));
        iterFactories.add(getNewtonIteratorFactory(new ApfelFunctionSinh()));
        iterFactories.add(getNewtonIteratorFactory(new ApfelFunctionCosh()));
        iterFactories.add(getNewtonIteratorFactory(new ApfelFunctionExp()));


        for(int n = 2; n <= 8; n++)
            iterFactories.add(getDualNewtonIteratorFactory(new ApfelFunctionPow<>(n), Complex.ONE));

        for(int n = 2; n <= 6; n++)
            for(int m = 1; m < n; m++)
                iterFactories.add(getDualNewtonIteratorFactory(new ApfelFunctionPowSum<>(n, m), Complex.ONE));

        for (int n = 1; n <= 6; n++)
            for (int m = 1; m <= 6; m++)
                if (n != m)
                    iterFactories.add(getDualNewtonIteratorFactory(new ApfelFunctionMixedPow<>(n, Complex.MINUS_ONE, m), Complex.ONE));

        iterFactories.add(getDualNewtonIteratorFactory(new ApfelFunctionSin(), Complex.ZERO));
        iterFactories.add(getDualNewtonIteratorFactory(new ApfelFunctionCos(), Complex.ZERO));
        iterFactories.add(getDualNewtonIteratorFactory(new ApfelFunctionSinh(), Complex.ZERO));
        iterFactories.add(getDualNewtonIteratorFactory(new ApfelFunctionCosh(), Complex.ZERO));
        iterFactories.add(getDualNewtonIteratorFactory(new ApfelFunctionExp(), Complex.ONE));

        return iterFactories;
    }

    public List<FractalIteratorFactory<Coordinate3D>> getCollection3D() {
        return new ArrayList<>();
    }

    public String getName() {
        return "newton";
    }

}
