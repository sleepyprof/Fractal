package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.formula.meta.ApfelFunctionFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelParamFunctionFactory;
import de.gdietz.fun.fractal.util.Normed;

import java.util.List;

public class CalculatorFunctionFactory<X extends Normed> implements ApfelFunctionFactory<X>, ApfelParamFunctionFactory<X> {

    private final List<CalculatorAction<X>> algorithm;

    private class ParamCalculatorFunction implements ApfelFunction<X> {

        private final Calculator<X> calc;

        private final X p;

        public ParamCalculatorFunction(Calculator<X> calc, X p) {
            this.calc = calc;
            this.p = p;
        }

        public X func(X z, X c) {
            return calc.calculate(algorithm, z, c, p);
        }

    }

    protected class CalculatorFunction implements ApfelFunction<X> {

        private final Calculator<X> calc;

        public CalculatorFunction(Calculator<X> calc) {
            this.calc = calc;
        }

        public X func(X z, X c) {
            return calc.calculate(algorithm, z, c);
        }

    }

    public CalculatorFunctionFactory(List<CalculatorAction<X>> algorithm) {
        this.algorithm = algorithm;
    }

    public List<CalculatorAction<X>> getAlgorithm() {
        return algorithm;
    }

    public ApfelFunction<X> get() {
        return new CalculatorFunction(new Calculator<X>());
    }

    public ApfelFunction<X> get(X p) {
        return new ParamCalculatorFunction(new Calculator<X>(), p);
    }

    public String toString() {
        String str = "calculator: ";
        for(int i = 0; i < algorithm.size(); i++) {
            str += algorithm.get(i);
            if (i < algorithm.size() - 1)
                str += ",";
        }
        return str;
    }

}
