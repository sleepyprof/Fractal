package de.gdietz.fun.fractal.model;

public interface FractalBuffer<D> extends FractalCanvas {

    public void clearData();

    public D getData(int x, int y);

}
