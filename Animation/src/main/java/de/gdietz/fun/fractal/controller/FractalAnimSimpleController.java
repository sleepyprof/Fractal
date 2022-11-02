package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.FractalAnimIterateModel;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalAnimSimpleController implements FractalAnimIterateController {

    private final FractalAnimIterateModel<?> model;

    public FractalAnimSimpleController(FractalAnimIterateModel<?> model) {
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

    public void setCorners(Coordinate from, Coordinate to) {
        model.setCorners(from, to);
    }

    public void setParameters(Coordinate paramFrom, Coordinate paramTo) {
        model.setParameters(paramFrom, paramTo);
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
