package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.BigCoordinate;

public class FractalPrecise2DPairedControllerImpl implements Fractal2DPairedController<BigCoordinate> {

    private final FractalPrecise2DController controller;
    private final FractalPrecise2DController controllerOther;

    public FractalPrecise2DPairedControllerImpl(FractalPrecise2DController controller, FractalPrecise2DController controllerOther) {
        this.controller = controller;
        this.controllerOther = controllerOther;
    }

    public FractalPrecise2DPairedControllerImpl(FractalPrecise2DController controller) {
        this(controller, null);
    }

    public void setSize(int width, int height) {
        controller.setSize(width, height);
    }

    public void setPoint(BigCoordinate p) {
    }

    public void dragPoint(BigCoordinate p) {
    }

    public void setCorners(BigCoordinate from, BigCoordinate to) {
        controller.setCorners(from, to);
    }

    public void setPointOther(BigCoordinate p) {
        if (controllerOther != null)
            controllerOther.setParameter(p);
    }

    public void dragPointOther(BigCoordinate p) {
        if (controllerOther != null)
            controllerOther.setParameter(p);
    }

    public void setCornersOther(BigCoordinate from, BigCoordinate to) {
    }

}
