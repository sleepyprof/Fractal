package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionComplexPow implements ApfelFunction<Complex> {

    private final Complex exponent;

    public ApfelFunctionComplexPow(Complex exponent) {
        this.exponent = exponent;
    }

    public Complex func(Complex z, Complex c) {
        return z.pow(exponent).add(c);
    }

    public String toString() {
        return PowerUtils.powerString("z", exponent) + " + c";
    }

}