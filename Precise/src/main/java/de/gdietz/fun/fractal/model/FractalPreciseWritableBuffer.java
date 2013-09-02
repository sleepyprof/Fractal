package de.gdietz.fun.fractal.model;

public interface FractalPreciseWritableBuffer<D> extends FractalPreciseBuffer<D> {

    public void setData(int x, int y, D data);
    
}
