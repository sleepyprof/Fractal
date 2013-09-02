package de.gdietz.fun.fractal.model;

public interface FractalAnimWritableBuffer<D> extends FractalAnimBuffer<D> {

    public void setData(int x, int y, int frame, D data);

}
