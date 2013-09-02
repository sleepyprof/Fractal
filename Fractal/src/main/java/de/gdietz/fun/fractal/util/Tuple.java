package de.gdietz.fun.fractal.util;

public interface Tuple<T extends Tuple<T>> extends Normed {

	public T negate();

	public T add(T x);
	public T subtract(T x);

	public T multiply(double r);

}
