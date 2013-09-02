package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionGamma implements ApfelFunction<Complex> {

    public Complex func(Complex z, Complex c) {
        return FormulaHelper.gamma(z.add(1.0)).add(c);
    }

    public String toString() {
        return "\u0393(z + 1) + c";
    }

}
