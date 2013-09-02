package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Coordinate;

public class FractalAnim2DPairedControllerImpl implements FractalAnimPairedController<Coordinate> {

    private final FractalAnimController controller;
    private final Fractal2DController controllerOther;

    public FractalAnim2DPairedControllerImpl(FractalAnimController controller, Fractal2DController controllerOther) {
        this.controller = controller;
        this.controllerOther = controllerOther;
    }

    public FractalAnim2DPairedControllerImpl(FractalAnimController controller) {
        this(controller, null);
    }

    public void setSize(int width, int height) {
        controller.setSize(width, height);
    }

    public void setSize(int width, int height, int frames) {
        controller.setSize(width, height, frames);
    }

    public void setFrames(int frames) {
        controller.setFrames(frames);
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
