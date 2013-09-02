package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.util.MultithreadFractalThread;
import de.gdietz.fun.fractal.model.util.MultithreadFractalWork;
import de.gdietz.fun.fractal.util.BigCoordinate;

public abstract class FractalPreciseSimpleIterateModelAdapter<D> extends FractalPreciseIterateModelAdapter<D> {

    private final FractalPreciseWritableBuffer<D> buffer;

    private final boolean julia;

    private MultithreadFractalThread thread;

    private class Work implements MultithreadFractalWork {

        public boolean calculate(int x, int y) {
            BigCoordinate c = getCoordinate(x, y);
            BigCoordinate p = getParameter();
            D data = julia ? FractalPreciseSimpleIterateModelAdapter.this.calculate(p, c) : FractalPreciseSimpleIterateModelAdapter.this.calculate(c, p);
            buffer.setData(x, y, data);
            
            return false;
        }

        public void notifyViews() {
            setChanged();
            notifyObservers();
        }

    }
    
    public FractalPreciseSimpleIterateModelAdapter(FractalPreciseWritableBuffer<D> buffer, FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter, boolean julia) {
        super(buffer, iteratorFactory, maxiter);
        this.buffer = buffer;
        this.julia = julia;
    }

    public FractalPreciseSimpleIterateModelAdapter(FractalPreciseWritableBuffer<D> buffer, FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter) {
        this(buffer, iteratorFactory, maxiter, false);
    }

    public boolean isJulia() {
        return julia;
    }

    public abstract D calculate(BigCoordinate c, BigCoordinate p);

    protected void stopCalculationSilent() {
        if (thread != null) {
            thread.stopNow();
            try {
                thread.join();
            } catch (InterruptedException e) {
                // no action required
            }
        }
        thread = null;
    }

    protected void startCalculationSilent() {
        thread = new MultithreadFractalThread(getWidth(), getHeight(), new Work(), false, this);
        thread.start();
    }

    public boolean isCalculating() {
        return thread != null && thread.isCalculating();
    }

    protected void finalize() throws Throwable {
        stopCalculationSilent();
        super.finalize();
    }

}
