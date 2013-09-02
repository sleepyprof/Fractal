package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.Tuple;

public class TwistedDualCoordMapper<X, Y, T extends Tuple<T>> implements DualCoordMapper<X, Y, T> {

    private final ParamCoordMapper<X, X, T> mapper;

    private final Y param;

    public TwistedDualCoordMapper(ParamCoordMapper<X, X, T> mapper, Y param) {
        this.mapper = mapper;
        this.param = param;
    }

    public X get(T c, T p) {
        return mapper.get(p, c);
    }

    public X getSecond(T c, T p) {
        return mapper.getParam(p, c);
    }

    public Y getParam(T c, T p) {
        return param;
    }

    public T coord(X x) {
        return mapper.coord(x);
    }

    public T coord(X x, X y) {
        return mapper.coord(x);
    }

}
