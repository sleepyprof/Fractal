package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Coordinate;

public abstract class FractalTilesSimpleIterateModelAdapter<D> extends FractalTilesIterateModelAdapter<D> {

    private final FractalTilesWritableBuffer<D> buffer;

    private final boolean julia;

    private boolean calculating;
    private FractalThread thread;

    private class FractalThread extends Thread {

        public boolean stopNow = false;

        public FractalThread() {
            super("FractalTilesThread");
        }

        public void run() {
            calculating = true;
            setCalculating(true);
            for (int tileY = 0; tileY < getTilesY(); tileY++) {
                for (int tileX = 0; tileX < getTilesX(); tileX++) {
                    setProgress(tileX + getTilesX() * tileY, getTilesX() * getTilesY());
                    for (int y = 0; y < getTileHeight(); y++) {
                        for (int x = 0; x < getTileWidth(); x++) {
                            if (stopNow) {
                                calculating = false;
                                setCalculating(false);
                                return;
                            }
                            Coordinate c = getCoordinate(x, y);
                            Coordinate p = getParameter(tileX, tileY);
                            D data = julia ? calculate(p, c) : calculate(c, p);
                            buffer.setData(x, y, tileX, tileY, data);
                        }
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // no action required
                    }
                }
            }
            calculating = false;
            setCalculating(false);
            setChanged();
            notifyObservers();
        }

    }

    public FractalTilesSimpleIterateModelAdapter(FractalTilesWritableBuffer<D> buffer, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
        super(buffer, iteratorFactory, maxiter);
        this.buffer = buffer;
        this.julia = julia;
    }

    public FractalTilesSimpleIterateModelAdapter(FractalTilesWritableBuffer<D> buffer, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
        this(buffer, iteratorFactory, maxiter, false);
    }

    public boolean isJulia() {
        return julia;
    }

    public abstract D calculate(Coordinate c, Coordinate p);

    protected void stopCalculationSilent() {
        if (thread != null) {
            thread.stopNow = true;
            try {
                thread.join();
            } catch (InterruptedException e) {
                // no action required
            }
        }
        thread = null;
    }

    protected void startCalculationSilent() {
        calculating = true;
        thread = new FractalThread();
        thread.start();
    }

    public boolean isCalculating() {
        return calculating;
    }

    protected void finalize() throws Throwable {
        stopCalculationSilent();
        super.finalize();
    }

}
