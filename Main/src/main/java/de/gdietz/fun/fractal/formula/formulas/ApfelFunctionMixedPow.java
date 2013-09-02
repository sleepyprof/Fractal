package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.NormedNumber;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionMixedPow <X extends NormedNumber<X>> implements ApfelDerivableFunction<X> {

    private final int n;
    private final X factor;
    private final int m;

    public ApfelFunctionMixedPow(int n, X factor, int m) {
        this.n = n;
        this.factor = factor;
        this.m = m;
    }

    public X func(X z, X c) {
        return z.pow(n).add(factor.multiply(z.pow(m))).add(c);
    }

    public X derivation(X z, X c) {
        return z.pow(n - 1).multiply(n).add(factor.multiply(z.pow(m - 1).multiply(m))).add(c);
    }

    public String toString() {
        return PowerUtils.powerString("z", n) + PowerUtils.powerString(factor, "z", m, true) + " + c";
    }

}