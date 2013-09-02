package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionCoshPow implements ApfelFunction<Complex> {

    private final int n;

    public ApfelFunctionCoshPow(int n) {
        this.n = n;
    }

    public Complex func(Complex z, Complex c) {
        return z.cosh().pow(n).add(c);
    }

    public String toString() {
        return PowerUtils.powerString("cosh", "z", n) + " + c";
    }

}