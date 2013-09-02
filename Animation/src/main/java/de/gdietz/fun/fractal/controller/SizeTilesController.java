package de.gdietz.fun.fractal.controller;

public interface SizeTilesController extends Size2DController {

    public void setSize(int width, int height, int tilesX, int tilesY);
    public void setTiles(int tilesX, int tilesY);

}
