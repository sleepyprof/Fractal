package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Normed;

public interface Constant<X extends Normed> extends CalculatorAction<X> {

    public X get();

}
