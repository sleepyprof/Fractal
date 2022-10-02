package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionCosh implements ApfelDerivableFunction<Complex> {

    public Complex func(Complex z, Complex c) {
        return z.cosh().add(c);
    }

    public Complex derivation(Complex z, Complex c) {
        return z.sinh();
    }

    public String toString() {
        return "cosh z + c";
    }

}