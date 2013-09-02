package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.NormedSemiNumber;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionPow<X extends NormedSemiNumber<X>> implements ApfelDerivableFunction<X> {

    private final int n;

    public ApfelFunctionPow(int n) {
        this.n = n;
    }

    public X func(X z, X c) {
        return z.pow(n).add(c);
    }

    public X derivation(X z, X c) {
        return z.pow(n - 1).multiply(n);
    }
    
    public String toString() {
        return PowerUtils.powerString("z", n) + " + c";
    }

}
