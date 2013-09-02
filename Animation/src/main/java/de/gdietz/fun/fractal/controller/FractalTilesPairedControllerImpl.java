package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Coordinate;

public class FractalTilesPairedControllerImpl implements FractalTilesPairedController {

    private final FractalTilesController controller;

    public FractalTilesPairedControllerImpl(FractalTilesController controller) {
        this.controller = controller;
    }

    public void setSize(int width, int height, int tilesX, int tilesY) {
        controller.setSize(width, height, tilesX, tilesY);
    }

    public void setSize(int width, int height) {
        controller.setSize(width, height);
    }

    public void setTiles(int tilesX, int tilesY) {
        controller.setTiles(tilesX, tilesY);
    }

    public void setPoint(Coordinate p) {
    }

    public void dragPoint(Coordinate p) {
    }

    public void setCorners(Coordinate from, Coordinate to) {
        controller.setCorners(from, to);
    }

    public void setPointOther(Coordinate p) {
    }

    public void dragPointOther(Coordinate p) {
    }

    public void setCornersOther(Coordinate from, Coordinate to) {
    }

}
