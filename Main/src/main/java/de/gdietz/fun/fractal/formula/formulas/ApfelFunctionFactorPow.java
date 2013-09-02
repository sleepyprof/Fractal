package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.NormedNumber;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionFactorPow<X extends NormedNumber<X>> implements ApfelDerivableFunction<X> {

    private final int n;
    private final X a;

    public ApfelFunctionFactorPow(int n, X a) {
        this.n = n;
        this.a = a;
    }

    public X func(X z, X c) {
        return a.multiply(z.pow(n)).add(c);
    }

    public X derivation(X z, X c) {
        return a.multiply(z.pow(n - 1).multiply(n));
    }

    public String toString() {
        return PowerUtils.powerString(a, "z", n) + " + c";
    }

}