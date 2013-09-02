package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionPowTrigonHyperbol implements ApfelFunction<Complex> {

    private final int n;
    private final FormulaHelper.TrigonHyperbolFunc func;

    public ApfelFunctionPowTrigonHyperbol(int n, FormulaHelper.TrigonHyperbolFunc func) {
        this.n = n;
        this.func = func;
    }

    public Complex func(Complex z, Complex c) {
        return z.pow(n).multiply(func.calc(z)).add(c);
    }

    public String toString() {
        return PowerUtils.powerString("z", n, func.toString() + " z") + " + c";
    }

}
