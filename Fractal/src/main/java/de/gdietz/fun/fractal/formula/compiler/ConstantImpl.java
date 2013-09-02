package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Normed;

public class ConstantImpl<X extends Normed> implements Constant<X> {

    private final X c;

    public ConstantImpl(X c) {
        this.c = c;
    }

    public X get() {
        return c;
    }

    public String toString() {
        return c.toString();
    }
    
}
