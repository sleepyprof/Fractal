package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Coordinate;

public abstract class FractalAnimSimpleIterateModelAdapter<D> extends FractalAnimIterateModelAdapter<D> {

	private final FractalAnimWritableBuffer<D> buffer;

	private final boolean julia;

	private boolean calculating;
	private FractalThread thread;

	private class FractalThread extends Thread {

        public boolean stopNow = false;

        public FractalThread() {
            super("FractalAnimThread");
        }

        public void run() {
            calculating = true;
            setCalculating(true);
            for (int frame = 0; frame < getFrames(); frame++) {
                setProgress(frame, getFrames());
                for (int y = 0; y < getHeight(); y++) {
                    for (int x = 0; x < getWidth(); x++) {
                        if (stopNow) {
                            calculating = false;
                            setCalculating(false);
                            return;
                        }
                        Coordinate c = getCoordinate(x, y);
                        Coordinate p = getParameter(frame);
                        D data = julia ? calculate(p, c) : calculate(c, p);
                        buffer.setData(x, y, frame, data);
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // no action required
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    // no action required
                }
            }
            calculating = false;
            setCalculating(false);
            setChanged();
            notifyObservers();
        }

    }

	public FractalAnimSimpleIterateModelAdapter(FractalAnimWritableBuffer<D> buffer, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
		super(buffer, iteratorFactory, maxiter);
		this.buffer = buffer;
		this.julia = julia;
	}

    public FractalAnimSimpleIterateModelAdapter(FractalAnimWritableBuffer<D> buffer, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
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
