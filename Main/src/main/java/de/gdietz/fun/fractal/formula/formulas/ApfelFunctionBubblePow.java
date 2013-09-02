package de.gdietz.fun.fractal.formula.formulas;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.NormedNumber;
import de.gdietz.fun.fractal.util.PowerUtils;

public class ApfelFunctionBubblePow<X extends NormedNumber<X>> implements ApfelFunction<X> {

    private final int n;
    private X a;

    public ApfelFunctionBubblePow(int n, X a) {
        this.n = n;
        this.a = a;
    }

    public X func(X z, X c) {
        return c.multiply(z).add(a).pow(n).add(c);
    }

    public String toString() {
        String base = "c z + " + a;
        return PowerUtils.powerString("(" + base + ")", n) + " + c";
    }

}
