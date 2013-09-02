package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.Complex;

public class ApfelFunctionSin implements ApfelDerivableFunction<Complex> {

    public Complex func(Complex z, Complex c) {
        return z.sin().add(c);
    }

    public Complex derivation(Complex z, Complex c) {
        return z.cos();
    }

    public String toString() {
        return "sin z + c";
    }

}

