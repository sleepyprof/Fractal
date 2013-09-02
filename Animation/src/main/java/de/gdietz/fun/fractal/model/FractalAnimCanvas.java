package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;

public interface FractalAnimCanvas extends SizeAnimCanvas {

    public void setSize(int width, int height, int frames);
    public void setFrames(int frames);
    public int getFrames();

    public void setCorners(Coordinate from, Coordinate to);
    public Coordinate getCornerFrom();
    public Coordinate getCornerTo();

    public void setParameters(Coordinate paramFrom, Coordinate paramTo);
    public Coordinate getParamFrom();
    public Coordinate getParamTo();

    public void setParamPath(Path<Coordinate> paramPath);
    public Path<Coordinate> getParamPath();

    public Coordinate getCoordinate(int x, int y);
    public int getX(Coordinate c);
    public int getY(Coordinate c);

    public Coordinate getParameter(int frame);

    public void correctAspectRatio(boolean expand);

}
