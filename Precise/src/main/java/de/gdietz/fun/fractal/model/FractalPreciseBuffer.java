package de.gdietz.fun.fractal.model;

public interface FractalPreciseBuffer<D> extends FractalPreciseCanvas {

    public void clearData();

    public D getData(int x, int y);
    
}
