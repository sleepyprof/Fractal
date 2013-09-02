package de.gdietz.fun.fractal.braid;

public interface IntersecData<X> {

	public void set(int i, int j, X value);

	public X get(int i, int j);

	public IntersecData<X> sigma(int i);

	public double normSqr();

	public boolean isDefinite();

}
