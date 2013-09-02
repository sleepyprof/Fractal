package de.gdietz.fun.fractal.model;

public interface FractalTilesBuffer<D> extends FractalTilesCanvas {

    public void clearData();

    public D getData(int x, int y, int tileX, int tileY);
    
}
