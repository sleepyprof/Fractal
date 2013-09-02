package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Tuple;

public interface CoordMapper<X, T extends Tuple<T>> {

    public X get(T c, T p);

    public T coord(X x);

}