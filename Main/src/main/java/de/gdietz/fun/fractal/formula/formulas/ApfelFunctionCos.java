package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionCos implements ApfelDerivableFunction<Complex> {

    public Complex func(Complex z, Complex c) {
        return z.cos().add(c);
    }

    public Complex derivation(Complex z, Complex c) {
        return z.sin().negate();
    }

    public String toString() {
        return "cos z + c";
    }

}