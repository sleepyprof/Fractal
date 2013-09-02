package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionLogTrigonHyperbolPow implements ApfelFunction<Complex> {

    private final int n;
    private final FormulaHelper.TrigonHyperbolFunc func;

    public ApfelFunctionLogTrigonHyperbolPow(int n, FormulaHelper.TrigonHyperbolFunc func) {
        this.n = n;
        this.func = func;
    }

    public Complex func(Complex z, Complex c) {
        return func.calc(z).add(1.0).log().pow(n).add(c);
    }

    public String toString() {
        return "log(" + func + " z + 1)^" + n + " + c";
    }

}
