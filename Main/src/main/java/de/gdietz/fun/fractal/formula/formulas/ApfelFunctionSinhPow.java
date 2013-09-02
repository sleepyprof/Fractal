package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionSinhPow implements ApfelFunction<Complex> {

    private final int n;

    public ApfelFunctionSinhPow(int n) {
        this.n = n;
    }

    public Complex func(Complex z, Complex c) {
        return z.sinh().pow(n).add(c);
    }

    public String toString() {
        return PowerUtils.powerString("sinh", "z", n) + " + c";
    }

}