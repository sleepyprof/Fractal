package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionLogisticPow implements ApfelFunction<Complex> {

    private final int n;

    public ApfelFunctionLogisticPow(int n) {
        this.n = n;
    }

    public Complex func(Complex z, Complex c) {
        Complex exp = z.exp();
        return exp.divide(exp.add(1.0)).pow(n).add(c);
    }

    public String toString() {
        return PowerUtils.powerString("(e^z / (1 + e^z))", n) + " + c";
    }

}
