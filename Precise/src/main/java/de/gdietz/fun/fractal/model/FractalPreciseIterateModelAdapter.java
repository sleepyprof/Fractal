package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.BigCoordinate;

public abstract class FractalPreciseIterateModelAdapter<D> extends FractalPreciseModelAdapter<D> implements FractalPreciseIterateModel<D> {

    private FractalIteratorFactory<BigCoordinate> iteratorFactory;
    private int maxiter;

    public FractalPreciseIterateModelAdapter(FractalPreciseBuffer<D> buffer, FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter) {
        super(buffer);
        this.iteratorFactory = iteratorFactory;
        this.maxiter = maxiter;
    }

    public void setIteratorFactory(FractalIteratorFactory<BigCoordinate> iteratorFactory) {
        stopCalculation();
        this.iteratorFactory = iteratorFactory;
        startCalculation();
    }

    public FractalIteratorFactory<BigCoordinate> getIteratorFactory() {
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

    public void setIteratorData(FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter) {
        stopCalculation();
        this.iteratorFactory = iteratorFactory;
        this.maxiter = maxiter;
        startCalculation();
    }

    
}
