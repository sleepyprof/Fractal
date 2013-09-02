package de.gdietz.fun.fractal.formula.algebra;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.util.NormedSemiNumber;

public class ApfelFunctionSum<X extends NormedSemiNumber<X>> implements ApfelFunction<X> {

    protected final ApfelFunction<X> f;
    protected final ApfelFunction<X> g;

    public ApfelFunctionSum(ApfelFunction<X> f, ApfelFunction<X> g) {
        this.f = f;
        this.g = g;
    }

    public X func(X z, X c) {
        return f.func(z, c).add(g.func(z, c)).subtract(c);
    }

    public String toString() {
        String fStr = f.toString();
        String gStr = g.toString();

        if (fStr.endsWith(" + c")) {
            fStr = fStr.substring(0, fStr.length() - 1);
            return fStr + gStr;
        }

        if (gStr.endsWith(" + c")) {
            gStr = gStr.substring(0, fStr.length() - 4);
            return fStr + " + " + gStr;
        }

        return fStr + " + " + gStr + " - c";
    }

}
