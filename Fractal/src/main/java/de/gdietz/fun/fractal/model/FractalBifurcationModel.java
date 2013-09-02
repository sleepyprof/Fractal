package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalBifurcationModel extends FractalIterateModelAdapter<Integer> {

	private final FractalSimpleBuffer buffer;

    private boolean calculating;
    private FractalThread thread;

    private class FractalThread extends Thread {

        public boolean stopNow = false;

        public FractalThread() {
            super("FractalBifurcationThread");
        }

        public void run() {
            calculating = true;
            setCalculating(true);
            for (int x = 0; x < getWidth(); x++) {
                setProgress(x, getWidth());
                if (stopNow) {
                    calculating = false;
                    setCalculating(false);
                    return;
                }
                doCalculation(x);
            }
            calculating = false;
            setCalculating(false);
            setChanged();
            notifyObservers();
        }

    }

	private FractalBifurcationModel(FractalSimpleBuffer buffer, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		super(buffer, iteratorFactory, maxiter);
		this.buffer = buffer;
	}

	public FractalBifurcationModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		this(new FractalSimpleBuffer(width, height, from, to), iteratorFactory, maxiter);
	}

	private void setPixel(int x, int y) {
		if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
			buffer.increaseData(x, y);
	}

    private void doCalculation(int x) {
        Complex temp = new Complex(getCoordinate(x, 0).getX(), 0.0);
        temp = temp.multiply(new Complex(getParameter()));

        Coordinate c = temp;
        Coordinate p = Coordinate.ORIGIN;
        FractalIterator<Coordinate> iterator = getIteratorFactory().get(c, p);
        int iter = 0;
        while(iterator.isValid() && iter < getMaxiter() * 100) {
            c = iterator.getCoordinate();
            int py = getY(new Coordinate(0.0, c.getX()));
            if (iter > getMaxiter() * 50)
                setPixel(x, py);
            iterator.iterate();
            iter++;
        }
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
        metadata.setType("Bifurcation");
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setMaxiter(getMaxiter());
        metadata.setSize(getWidth(), getHeight());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        return metadata;
    }

}