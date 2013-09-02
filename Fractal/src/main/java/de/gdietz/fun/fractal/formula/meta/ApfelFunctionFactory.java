package de.gdietz.fun.fractal.formula.meta;

import de.gdietz.fun.fractal.formula.ApfelFunction;

public interface ApfelFunctionFactory<X> {

    public ApfelFunction<X> get();

}
