package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Coordinate;

public abstract class FractalTilesIterateModelAdapter<D> extends FractalTilesModelAdapter<D> implements FractalTilesIterateModel<D> {

    private FractalIteratorFactory<Coordinate> iteratorFactory;
    private int maxiter;

    public FractalTilesIterateModelAdapter(FractalTilesBuffer<D> buffer, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
        super(buffer);
        this.iteratorFactory = iteratorFactory;
        this.maxiter = maxiter;
    }

    public void setIteratorFactory(FractalIteratorFactory<Coordinate> iteratorFactory) {
        stopCalculation();
        this.iteratorFactory = iteratorFactory;
        startCalculation();
    }

    public FractalIteratorFactory<Coordinate> getIteratorFactory() {
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

    public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
        stopCalculation();
        this.iteratorFactory = iteratorFactory;
        this.maxiter = maxiter;
        startCalculation();
    }

}
