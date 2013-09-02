package de.gdietz.fun.fractal.model;

public interface FractalAnimBuffer<D> extends FractalAnimCanvas {

    public void clearData();

    public D getData(int x, int y, int frame);

}
