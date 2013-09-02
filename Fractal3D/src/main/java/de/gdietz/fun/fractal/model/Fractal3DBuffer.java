package de.gdietz.fun.fractal.model;

public interface Fractal3DBuffer extends Fractal3DCanvas {

    public void clearData();

    public boolean getData(int x, int y, int z);

}
