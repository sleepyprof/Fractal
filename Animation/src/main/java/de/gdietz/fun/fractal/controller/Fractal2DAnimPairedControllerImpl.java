package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Coordinate;

public class Fractal2DAnimPairedControllerImpl implements Fractal2DPairedController<Coordinate> {

    private final Fractal2DController controller;
    private final FractalAnimController controllerOther;

    public Fractal2DAnimPairedControllerImpl(Fractal2DController controller, FractalAnimController controllerOther) {
        this.controller = controller;
        this.controllerOther = controllerOther;
    }

    public Fractal2DAnimPairedControllerImpl(Fractal2DController controller) {
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
    }

    public void dragPointOther(Coordinate p) {
    }

    public void setCornersOther(Coordinate from, Coordinate to) {
        if (controllerOther != null)
            controllerOther.setParameters(from, to);
    }

}
