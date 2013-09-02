package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;

public interface FractalZoomCanvas extends SizeAnimCanvas {

    public void setSize(int width, int height, int frames);
    public void setFrames(int frames);
    public int getFrames();

    public void setCorners(Coordinate fromStart, Coordinate toStart, Coordinate fromEnd, Coordinate toEnd);
    public Coordinate getCornerFromStart();
    public Coordinate getCornerToStart();
    public Coordinate getCornerFromEnd();
    public Coordinate getCornerToEnd();

    public void setCornerPaths(Path<Coordinate> fromPath, Path<Coordinate> toPath);
    public Path<Coordinate> getFromPath();
    public Path<Coordinate> getToPath();

    public void setParameter(Coordinate parameter);
    public Coordinate getParameter();

    public Coordinate getFrom(int frame);
    public Coordinate getTo(int frame);

    public Coordinate getCoordinate(int x, int y, int frame);
    public int getX(Coordinate c, int frame);
    public int getY(Coordinate c, int frame);

    public void correctAspectRatio(boolean expand);

}
