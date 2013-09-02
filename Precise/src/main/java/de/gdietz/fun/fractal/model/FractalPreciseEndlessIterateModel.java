package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.meta.FractalPreciseMetadata;
import de.gdietz.fun.fractal.model.util.MultithreadFractalThread;
import de.gdietz.fun.fractal.model.util.MultithreadFractalWork;
import de.gdietz.fun.fractal.util.BigCoordinate;

public class FractalPreciseEndlessIterateModel extends FractalPreciseModelAdapter<Integer> implements FractalPreciseIterateModel<Integer> {

    private static final int ITERSTEPS = 30;

	private final FractalPreciseIterateBuffer buffer;

	private MultithreadFractalThread thread;

    private class Work implements MultithreadFractalWork {

        public boolean calculate(int x, int y) {
            return FractalPreciseEndlessIterateModel.this.calculate(x, y);
        }

        public void notifyViews() {
            setChanged();
            notifyObservers();
        }

    }
    
	private FractalPreciseEndlessIterateModel(FractalPreciseIterateBuffer buffer) {
		super(buffer);
		this.buffer = buffer;
	}

	public FractalPreciseEndlessIterateModel(int width, int height, BigCoordinate from, BigCoordinate to, FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter, boolean julia) {
		this(new FractalPreciseIterateBuffer(width, height, from, to, iteratorFactory, maxiter, julia));
	}

	public FractalPreciseEndlessIterateModel(int width, int height, BigCoordinate from, BigCoordinate to, FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter) {
		this(width, height, from, to, iteratorFactory, maxiter, false);
	}

    public boolean isJulia() {
        return buffer.isJulia();
    }

	private boolean calculate(int x, int y) {
		return buffer.iterate(x, y, ITERSTEPS);
	}

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
        thread = new MultithreadFractalThread(getWidth(), getHeight(), new Work(), true, this);
		thread.start();
	}

	public boolean isCalculating() {
		return thread != null && thread.isCalculating();
	}

	public void setIteratorFactory(FractalIteratorFactory<BigCoordinate> iteratorFactory) {
		stopCalculation();
		buffer.setIteratorFactory(iteratorFactory);
		startCalculation();
	}

    public FractalIteratorFactory<BigCoordinate> getIteratorFactory() {
        return buffer.getIteratorFactory();
    }

	public void setMaxiter(int maxiter) {
		stopCalculation();
		buffer.setMaxiter(maxiter);
		startCalculation();
	}

    public int getMaxiter() {
        return buffer.getMaxiter();
    }

    public void setIteratorData(FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter) {
		stopCalculation();
		buffer.setIteratorData(iteratorFactory, maxiter);
		startCalculation();
	}

    protected void finalize() throws Throwable {
        stopCalculationSilent();
        super.finalize();
    }

    public FractalPreciseMetadata getMetadata() {
        FractalPreciseMetadata metadata = new FractalPreciseMetadata();
        metadata.setType(isJulia() ? "Julia Set" : "Mandelbrot Set");
        metadata.setIterationType(true);
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setPreciseType(getIteratorFactory());
        metadata.setSize(getWidth(), getHeight());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        return metadata;
    }

}
