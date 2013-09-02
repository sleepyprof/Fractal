package de.gdietz.fun.fractal.formula.compiler;

import de.gdietz.fun.fractal.util.Normed;

public interface BinaryOperator<X extends Normed> extends CalculatorAction<X> {

    public X compose(X x, X y);

}
