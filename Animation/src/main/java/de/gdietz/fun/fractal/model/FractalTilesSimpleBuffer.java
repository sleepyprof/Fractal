package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;

public class FractalTilesSimpleBuffer extends FractalTilesCanvasImpl implements FractalTilesWritableBuffer<Integer> {

    private boolean ready = false;

    private int[][][][] data;

    public FractalTilesSimpleBuffer(int tileWidth, int tileHeight, int tilesX, int tilesY, Coordinate from, Coordinate to, Coordinate paramFrom, Coordinate paramTo) {
        super(tileWidth, tileHeight, tilesX, tilesY, from, to, paramFrom, paramTo);
        data = new int[tileWidth][tileHeight][tilesX][tilesY];
        ready = true;
    }

    public void clearData() {
        ready = false;
        data = new int[getTileWidth()][getTileHeight()][getTilesX()][getTilesY()];
        ready = true;
    }

    public void setSize(int tileWidth, int tileHeight, int tilesX, int tilesY) {
        ready = false;
        super.setSize(tileWidth, tileHeight, tilesX, tilesY);
        data = new int[getTileWidth()][getTileHeight()][getTilesX()][getTilesY()];
        ready = true;
    }

    public Integer getData(int x, int y, int tileX, int tileY) {
        return ready ? data[x][y][tileX][tileY] : 0;
    }

    public void setData(int x, int y, int tileX, int tileY, Integer index) {
        if (ready)
            data[x][y][tileX][tileY] = index;
    }
    
}
