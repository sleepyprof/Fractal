package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelDerivableFunction;
import de.gdietz.fun.fractal.util.NormedNumber;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionPowCommutator<X extends NormedNumber<X>> implements ApfelDerivableFunction<X> {

    private final int n;
    private final X a;
    private final X aplus1;

    public ApfelFunctionPowCommutator(int n, X a) {
        this.n = n;
        this.a = a;
        aplus1 = a.add(1.0);
    }

    public X func(X z, X c) {
        X zn = z.pow(n);
        return aplus1.multiply(zn).subtract(zn.multiply(a)).add(c);
    }

    public X derivation(X z, X c) {
        X znder = z.pow(n - 1).multiply(n);
        return aplus1.multiply(znder).subtract(znder.multiply(a));
    }

    public String toString() {
        return PowerUtils.powerString(aplus1, "z", n) + " " + PowerUtils.powerString("z", n, a.negate(), true) + " + c";
    }

}
