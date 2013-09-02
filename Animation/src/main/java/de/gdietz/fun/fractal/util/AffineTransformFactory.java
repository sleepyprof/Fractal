package de.gdietz.fun.fractal.util;

public interface AffineTransformFactory<T extends Tuple<T>, O extends LinearOperator<T, O>> {

    public AffineOperator<T, O> get(T oldFrom, T oldTo, T newFrom, T newTo);

}
