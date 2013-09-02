package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;

public interface FractalTilesCanvas extends Size2DCanvas {

    public void setSize(int tileWidth, int tileHeight, int tilesX, int tilesY);
    public void setTiles(int tilesX, int tilesY);
    public int getTileWidth();
    public int getTileHeight();
    public int getTilesX();
    public int getTilesY();

    public void setCorners(Coordinate from, Coordinate to);
    public Coordinate getCornerFrom();
    public Coordinate getCornerTo();

    public void setParameters(Coordinate paramFrom, Coordinate paramTo);
    public Coordinate getParamFrom();
    public Coordinate getParamTo();

    public Coordinate getCoordinate(int x, int y);
    public int getX(Coordinate c);
    public int getY(Coordinate c);

    public Coordinate getParameter(int tileX, int tileY);

    public void correctAspectRatio(boolean expand);

}
