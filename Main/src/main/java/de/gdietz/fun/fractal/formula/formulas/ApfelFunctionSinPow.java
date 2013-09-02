package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionSinPow implements ApfelFunction<Complex> {

    private final int n;

    public ApfelFunctionSinPow(int n) {
        this.n = n;
    }

    public Complex func(Complex z, Complex c) {
        return z.sin().pow(n).add(c);
    }

    public String toString() {
        return PowerUtils.powerString("sin", "z", n) + " + c";
    }

}