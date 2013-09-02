package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.FractalZoomIterateModel;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalZoomSimpleController implements FractalZoomIterateController {

    private final FractalZoomIterateModel<?> model;

    public FractalZoomSimpleController(FractalZoomIterateModel<?> model) {
        this.model = model;
    }

    public void startCalculation() {
        model.startCalculation();
    }

    public void stopCalculation() {
        model.stopCalculation();
    }

    public void setSize(int width, int height) {
        if (width == model.getWidth() && height == model.getHeight())
            return;
        model.setSize(width, height);
    }

    public void setSize(int width, int height, int frames) {
        if (width == model.getWidth() && height == model.getHeight() && frames == model.getFrames())
            return;
        model.setSize(width, height, frames);
    }

    public void setFrames(int frames) {
        if (frames == model.getFrames())
            return;
        model.setFrames(frames);
    }

    public void setCorners(Coordinate fromStart, Coordinate toStart, Coordinate fromEnd, Coordinate toEnd) {
        model.setCorners(fromStart, toStart, fromEnd, toEnd);
    }

    public void setParameter(Coordinate parameter) {
        model.setParameter(parameter);
    }

    public void correctAspectRatio(boolean expand) {
        model.correctAspectRatio(expand);
    }

    public void setIteratorFactory(FractalIteratorFactory<Coordinate> iteratorFactory) {
        model.setIteratorFactory(iteratorFactory);
    }

    public void setMaxiter(int maxiter) {
        model.setMaxiter(maxiter);
    }

    public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
        model.setIteratorData(iteratorFactory, maxiter);
    }

    public void checkAndNotify() {
        model.checkAndNotify();
    }

}
