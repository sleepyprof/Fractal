package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.util.Coordinate3D;
import org.apache.log4j.Logger;

public class Fractal3DSimpleIterateModel extends Fractal3DIterateModelAdapter {

    private final Fractal3DSimpleBuffer buffer;

    private final boolean julia;

    private boolean calculating;
    private FractalThread thread;

    private static Logger log = Logger.getLogger(Fractal3DSimpleIterateModel.class);

    private class FractalThread extends Thread {

        public boolean stopNow = false;

        public FractalThread() {
            super("Fractal3DThread");
        }

        public void run() {
            calculating = true;
            setCalculating(true);
            log.info("Calculating body...");
            for (int z = 0; z < getSize(); z++) {
                setProgress(z, getSize());
                for (int y = 0; y < getSize(); y++) {
                    for (int x = 0; x < getSize(); x++) {
                        if (stopNow) {
                            calculating = false;
                            setCalculating(false);
                            return;
                        }
                        Coordinate3D c = getCoordinate(x, y, z);
                        Coordinate3D p = getParameter();
                        boolean inside = julia ? calculate(p, c) : calculate(c, p);
                        buffer.setData(x, y, z, inside);
                    }
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    // no action required
                }
            }
            calculating = false;
            setCalculating(false);
            log.info("body finished.");
            setChanged();
            notifyObservers();
        }
    }

    private Fractal3DSimpleIterateModel(Fractal3DSimpleBuffer buffer, FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter, boolean julia) {
        super(buffer, iteratorFactory, maxiter);
        this.buffer = buffer;
        this.julia = julia;
    }

    public Fractal3DSimpleIterateModel(int size, Coordinate3D from, Coordinate3D to, FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter, boolean julia) {
        this(new Fractal3DSimpleBuffer(size, from, to), iteratorFactory, maxiter, julia);
    }

    public Fractal3DSimpleIterateModel(int size, Coordinate3D from, Coordinate3D to, FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter) {
        this(size, from, to, iteratorFactory, maxiter, false);
    }

    public boolean isJulia() {
        return julia;
    }

    public boolean calculate(Coordinate3D c, Coordinate3D p) {
        FractalIterator<Coordinate3D> iterator = getIteratorFactory().get(c, p);
        if (iterator.isSurvivor())
            return true;
        int iter = 0;
        while(iterator.isValid() && iter < getMaxiter()) {
            iterator.iterate();
            iter++;
        }
        return iterator.isValid();
    }

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

    public FractalMetadata getMetadata() {
        FractalMetadata metadata = new FractalMetadata();
        metadata.setType(isJulia() ? "Julia Set 3D" : "Mandelbrot Set 3D");
        metadata.setIterationType(false);
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setMaxiter(getMaxiter());
        metadata.setSize(getSize());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        return metadata;
    }

}
