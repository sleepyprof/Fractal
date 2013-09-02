package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Coordinate3D;

public abstract class Fractal3DIterateModelAdapter extends Fractal3DModelAdapter implements Fractal3DIterateModel {

    private FractalIteratorFactory<Coordinate3D> iteratorFactory;
    private int maxiter;

    public Fractal3DIterateModelAdapter(Fractal3DBuffer buffer, FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter) {
        super(buffer);
        this.iteratorFactory = iteratorFactory;
        this.maxiter = maxiter;
    }

    public void setIteratorFactory(FractalIteratorFactory<Coordinate3D> iteratorFactory) {
        stopCalculation();
        this.iteratorFactory = iteratorFactory;
        startCalculation();
    }

    public FractalIteratorFactory<Coordinate3D> getIteratorFactory() {
        return iteratorFactory;
    }

    public void setMaxiter(int maxiter) {
        stopCalculation();
        this.maxiter = maxiter;
        startCalculation();
    }

    public int getMaxiter() {
        return maxiter;
    }

    public void setIteratorData(FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter) {
        stopCalculation();
        this.iteratorFactory = iteratorFactory;
        this.maxiter = maxiter;
        startCalculation();
    }

}
