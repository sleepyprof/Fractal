package de.gdietz.fun.fractal.formula.meta;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.formula.Scaled;
import de.gdietz.fun.fractal.util.BigNormed;

import java.math.RoundingMode;

public class ApfelPreciseFunctionFactory<X extends BigNormed<X>> implements ApfelFunctionFactory<X>, Scaled {

    private final ApfelFunction<X> function;

    private final int maxScale;

    private final ApfelPreciseFunction preciseFunction = new ApfelPreciseFunction();

    private class ApfelPreciseFunction implements ApfelFunction<X> {

        public X func(X z, X c) {
            X result = function.func(z, c);
            if (result.scale() > maxScale)
                return result.setScale(maxScale, RoundingMode.HALF_UP);
            return result;
        }

    }

    public ApfelPreciseFunctionFactory(ApfelFunction<X> function, int maxScale) {
        this.function = function;
        this.maxScale = maxScale;
    }

    public ApfelFunction<X> get() {
        return preciseFunction;
    }

    public int getMaxScale() {
        return maxScale;
    }

    public String toString() {
        return function.toString();
    }

}
