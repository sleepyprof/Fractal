package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionPowHalf implements ApfelDerivableFunction<Complex> {

    private final int n;

    public ApfelFunctionPowHalf(int n) {
        this.n = n;
    }

    public Complex func(Complex z, Complex c) {
        return z.sqrt().pow(n).add(c);
    }

    public Complex derivation(Complex z, Complex c) {
        return z.sqrt().pow(n - 2).multiply((double)n / 2.0);
    }

    public String toString() {
        return "z^(" + n + "/2) + c";
    }

}

