package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionTanPow implements ApfelFunction<Complex> {

    private final int n;

    public ApfelFunctionTanPow(int n) {
        this.n = n;
    }

    public Complex func(Complex z, Complex c) {
        return z.tan().pow(n).add(c);
    }

    public String toString() {
        return PowerUtils.powerString("tan", "z", n) + " + c";
    }

}