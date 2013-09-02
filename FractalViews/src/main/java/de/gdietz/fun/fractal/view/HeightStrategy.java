package de.gdietz.fun.fractal.view;

public interface HeightStrategy<D> {

    public int get(D data);
    public int getMaxHeight();

}
