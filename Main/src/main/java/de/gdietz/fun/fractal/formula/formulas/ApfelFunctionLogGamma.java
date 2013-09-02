package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionLogGamma implements ApfelFunction<Complex> {

    public Complex func(Complex z, Complex c) {
        return FormulaHelper.logGamma(z.add(1.0)).add(c);
    }

    public String toString() {
        return "log \u0393(z + 1) + c";
    }

}
