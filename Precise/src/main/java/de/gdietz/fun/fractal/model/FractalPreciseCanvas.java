package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.BigCoordinate;

public interface FractalPreciseCanvas extends Size2DCanvas {

    public void setCorners(BigCoordinate from, BigCoordinate to);
    public BigCoordinate getCornerFrom();
    public BigCoordinate getCornerTo();

    public void setParameter(BigCoordinate parameter);
    public BigCoordinate getParameter();

    public BigCoordinate getCoordinate(int x, int y);
    public int getX(BigCoordinate c);
    public int getY(BigCoordinate c);

    public void correctAspectRatio(boolean expand);

}
