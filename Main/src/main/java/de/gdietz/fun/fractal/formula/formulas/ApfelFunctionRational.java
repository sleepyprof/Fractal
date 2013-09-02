package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionRational implements ApfelFunction<Complex> {

    private final int n;
    private final int m;

    public ApfelFunctionRational(int n, int m) {
        this.n = n;
        this.m = m;
    }

    public Complex func(Complex z, Complex c) {
        Complex zn = z.pow(n);
        Complex zm = m == n ? zn : z.pow(m);
        return zn.divide(zm.add(1.0)).add(c);
    }

    public String toString() {
        return PowerUtils.powerString("z", n)  + " / (" + PowerUtils.powerString("z", m) + " + 1) + c";
    }

}
