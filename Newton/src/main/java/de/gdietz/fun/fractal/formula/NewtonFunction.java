package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.NormedNumber;

public class NewtonFunction<X extends NormedNumber<X>> implements ApfelFunction<X> {

    private final ApfelDerivableFunction<X> function;

    public NewtonFunction(ApfelDerivableFunction<X> function) {
        this.function = function;
    }

    public X func(X z, X c) {
        X fz = function.func(z, c);
        X derz = function.derivation(z, c);
        X delta = fz.divide(derz);
        return z.subtract(delta);
    }

    public String toString() {
        return "newton(" + function + ")";
    }

}
