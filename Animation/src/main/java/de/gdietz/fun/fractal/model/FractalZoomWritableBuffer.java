package de.gdietz.fun.fractal.model;

public interface FractalZoomWritableBuffer<D> extends FractalZoomBuffer<D> {

    public void setData(int x, int y, int frame, D data);

}
