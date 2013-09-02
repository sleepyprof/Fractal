package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Tuple;

public interface DualCoordMapper<X, Y, T extends Tuple<T>> extends ParamCoordMapper<X, Y, T> {

    public X getSecond(T c, T p);

    public T coord(X x, X y);

}
