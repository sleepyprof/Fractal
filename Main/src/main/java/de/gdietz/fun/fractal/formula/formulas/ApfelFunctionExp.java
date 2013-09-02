package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionExp implements ApfelDerivableFunction<Complex> {

    public Complex func(Complex z, Complex c) {
        return z.exp().add(c);
    }

    public Complex derivation(Complex z, Complex c) {
        return z.exp();
    }

    public String toString() {
        return "e^z + c";
    }

}
