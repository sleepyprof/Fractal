package de.gdietz.fun.fractal.model;

public interface FractalZoomBuffer<D> extends FractalZoomCanvas {

    public void clearData();

    public D getData(int x, int y, int frame);

}
