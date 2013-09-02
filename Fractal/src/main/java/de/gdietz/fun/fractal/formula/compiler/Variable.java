package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Normed;

public interface Variable<X extends Normed> extends CalculatorAction<X> {

    public X get(X z, X c, X p);

}
