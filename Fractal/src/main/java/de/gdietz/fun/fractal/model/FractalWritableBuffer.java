package de.gdietz.fun.fractal.model;

public interface FractalWritableBuffer<D> extends FractalBuffer<D> {

    public void setData(int x, int y, D data);
    
}
