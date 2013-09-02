package de.gdietz.fun.fractal.model;

public interface SizeAnimCanvas extends Size2DCanvas {

    public void setSize(int width, int height, int frames);
    public void setFrames(int frames);
    public int getFrames();

}
