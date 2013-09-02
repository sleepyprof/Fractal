package de.gdietz.fun.fractal.model;

public interface FractalTilesWritableBuffer<D> extends FractalTilesBuffer<D> {

    public void setData(int x, int y, int tileX, int tileY, D data);

}
