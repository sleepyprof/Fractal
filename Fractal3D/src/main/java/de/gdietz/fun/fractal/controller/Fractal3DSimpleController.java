package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.Fractal3DIterateModel;
import de.gdietz.fun.fractal.util.Coordinate3D;

public class Fractal3DSimpleController implements Fractal3DIterateController {

    private final Fractal3DIterateModel model;

    public Fractal3DSimpleController(Fractal3DIterateModel model) {
        this.model = model;
    }

    public void startCalculation() {
        model.startCalculation();
    }

    public void stopCalculation() {
        model.stopCalculation();
    }

    public void setSize(int size) {
        if (size == model.getSize())
            return;
        model.setSize(size);
    }

    public void setCorners(Coordinate3D from, Coordinate3D to) {
        model.setCorners(from, to);
    }

    public void setParameter(Coordinate3D parameter) {
        model.setParameter(parameter);
    }

    public void correctAspectRatio(boolean expand) {
        model.correctAspectRatio(expand);
    }

    public void setIteratorFactory(FractalIteratorFactory<Coordinate3D> iteratorFactory) {
        model.setIteratorFactory(iteratorFactory);
    }

    public void setMaxiter(int maxiter) {
        model.setMaxiter(maxiter);
    }

    public void setIteratorData(FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter) {
        model.setIteratorData(iteratorFactory, maxiter);
    }

    public void checkAndNotify() {
        model.checkAndNotify();
    }

}
