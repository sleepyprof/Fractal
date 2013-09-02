package de.gdietz.fun.fractal.formula.meta;

import de.gdietz.fun.fractal.formula.ApfelFunction;

public interface ApfelParamFunctionFactory<X> {

    public ApfelFunction<X> get(X p);

}
