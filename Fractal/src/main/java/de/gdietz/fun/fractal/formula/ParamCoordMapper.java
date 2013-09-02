package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Tuple;

public interface ParamCoordMapper<X, Y, T extends Tuple<T>> extends CoordMapper<X, T> {

    public Y getParam(T c, T p);

}