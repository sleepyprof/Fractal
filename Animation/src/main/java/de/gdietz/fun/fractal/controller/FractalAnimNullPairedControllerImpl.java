package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Coordinate;

public class FractalAnimNullPairedControllerImpl implements FractalAnimPairedController<Coordinate> {

    private final SizeAnimController controller;

    public FractalAnimNullPairedControllerImpl(SizeAnimController controller) {
        this.controller = controller;
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
    }

    public void setPointOther(Coordinate p) {
    }

    public void dragPointOther(Coordinate p) {
    }

    public void setCornersOther(Coordinate from, Coordinate to) {
    }

}
