package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.NormedSemiNumber;

public class PowOperator<X extends NormedSemiNumber<X>> implements UnaryOperator<X> {

    private final int n;

    public PowOperator(int n) {
        this.n = n;
    }

    public X operate(X x) {
        return x.pow(n);
    }

    public String toString() {
        return "^" + n;
    }

}
