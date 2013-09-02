package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Coordinate;

public class Fractal2DNullPairedControllerImpl implements Fractal2DPairedController<Coordinate> {

    private final Size2DController controller;

    public Fractal2DNullPairedControllerImpl(Size2DController controller) {
        this.controller = controller;
    }

    public void setSize(int width, int height) {
        controller.setSize(width, height);
    }

    public void setPoint(Coordinate p) {
    }

    public void dragPoint(Coordinate p) {
    }

    public void setCorners(Coordinate from, Coordinate to) {
    }

    public void setPointOther(Coordinate p) {
    }

    public void dragPointOther(Coordinate p) {
    }

    public void setCornersOther(Coordinate from, Coordinate to) {
    }

}
