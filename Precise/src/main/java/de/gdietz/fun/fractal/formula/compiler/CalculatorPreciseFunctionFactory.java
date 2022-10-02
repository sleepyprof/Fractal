package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.formula.Scaled;
import de.gdietz.fun.fractal.formula.meta.ApfelFunctionFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelParamFunctionFactory;
import de.gdietz.fun.fractal.util.BigNormed;

import java.math.RoundingMode;
import java.util.List;

public class CalculatorPreciseFunctionFactory<X extends BigNormed<X>> implements ApfelFunctionFactory<X>, ApfelParamFunctionFactory<X>, Scaled {

    private final List<CalculatorAction<X>> algorithm;

    private final int maxScale;

    private class ParamCalculatorPreciseFunction implements ApfelFunction<X> {

        private final Calculator<X> calc;

        private final X p;

        public ParamCalculatorPreciseFunction(Calculator<X> calc, X p) {
            this.calc = calc;
            this.p = p;
        }

        public X func(X z, X c) {
            X result = calc.calculate(algorithm, z, c, p);
            if (result.scale() > maxScale)
                return result.setScale(maxScale, RoundingMode.HALF_UP);
            return result;
        }

    }

    protected class CalculatorPreciseFunction implements ApfelFunction<X> {

        private final Calculator<X> calc;

        public CalculatorPreciseFunction(Calculator<X> calc) {
            this.calc = calc;
        }

        public X func(X z, X c) {
            X result = calc.calculate(algorithm, z, c);
            if (result.scale() > maxScale)
                return result.setScale(maxScale, RoundingMode.HALF_UP);
            return result;
        }

    }

    public CalculatorPreciseFunctionFactory(List<CalculatorAction<X>> algorithm, int maxScale) {
        this.algorithm = algorithm;
        this.maxScale = maxScale;
    }

    public List<CalculatorAction<X>> getAlgorithm() {
        return algorithm;
    }

    public ApfelFunction<X> get() {
        return new CalculatorPreciseFunction(new Calculator<>());
    }

    public ApfelFunction<X> get(X p) {
        return new ParamCalculatorPreciseFunction(new Calculator<>(), p);
    }

    public int getMaxScale() {
        return maxScale;
    }

    public String toString() {
        StringBuilder str = new StringBuilder("calculator: ");
        for(int i = 0; i < algorithm.size(); i++) {
            str.append(algorithm.get(i));
            if (i < algorithm.size() - 1)
                str.append(",");
        }
        return str.toString();
    }

}
