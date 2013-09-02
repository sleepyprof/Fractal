package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.FractalPreciseIterateModel;
import de.gdietz.fun.fractal.util.BigCoordinate;

public class FractalPreciseSimpleController implements FractalPrecise2DIterateController {

    private final FractalPreciseIterateModel<?> model;

    public FractalPreciseSimpleController(FractalPreciseIterateModel<?> model) {
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

    public void setCorners(BigCoordinate from, BigCoordinate to) {
        model.setCorners(from, to);
    }

    public void setParameter(BigCoordinate parameter) {
        model.setParameter(parameter);
    }

    public void correctAspectRatio(boolean expand) {
        model.correctAspectRatio(expand);
    }

    public void setIteratorFactory(FractalIteratorFactory<BigCoordinate> iteratorFactory) {
        model.setIteratorFactory(iteratorFactory);
    }

    public void setMaxiter(int maxiter) {
        model.setMaxiter(maxiter);
    }

    public void setIteratorData(FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter) {
        model.setIteratorData(iteratorFactory, maxiter);
    }

    public void checkAndNotify() {
        model.checkAndNotify();
    }

}
