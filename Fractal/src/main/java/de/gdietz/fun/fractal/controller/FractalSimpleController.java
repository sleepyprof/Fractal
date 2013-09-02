package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.FractalIterateModel;

public class FractalSimpleController implements Fractal2DIterateController {

    private final FractalIterateModel<?> model;

    public FractalSimpleController(FractalIterateModel<?> model) {
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

    public void setCorners(Coordinate from, Coordinate to) {
        model.setCorners(from, to);
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
