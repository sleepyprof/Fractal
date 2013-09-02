package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionSinh implements ApfelDerivableFunction<Complex> {

    public Complex func(Complex z, Complex c) {
        return z.sinh().add(c);
    }

    public Complex derivation(Complex z, Complex c) {
        return z.cosh();
    }

    public String toString() {
        return "sinh z + c";
    }

}