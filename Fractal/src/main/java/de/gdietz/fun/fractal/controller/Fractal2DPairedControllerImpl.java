package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Coordinate;

public class Fractal2DPairedControllerImpl implements Fractal2DPairedController<Coordinate> {

    private final Fractal2DController controller;
    private final Fractal2DController controllerOther;

    public Fractal2DPairedControllerImpl(Fractal2DController controller, Fractal2DController controllerOther) {
        this.controller = controller;
        this.controllerOther = controllerOther;
    }

    public Fractal2DPairedControllerImpl(Fractal2DController controller) {
        this(controller, null);
    }

    public void setSize(int width, int height) {
        controller.setSize(width, height);
    }

    public void setPoint(Coordinate p) {
    }

    public void dragPoint(Coordinate p) {
    }

    public void setCorners(Coordinate from, Coordinate to) {
        controller.setCorners(from, to);
    }

    public void setPointOther(Coordinate p) {
        if (controllerOther != null)
            controllerOther.setParameter(p);
    }

    public void dragPointOther(Coordinate p) {
        if (controllerOther != null)
            controllerOther.setParameter(p);
    }

    public void setCornersOther(Coordinate from, Coordinate to) {
    }

}
