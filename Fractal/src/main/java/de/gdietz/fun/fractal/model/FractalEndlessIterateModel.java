package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.model.util.MultithreadFractalWork;
import de.gdietz.fun.fractal.model.util.MultithreadFractalThread;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;

public class FractalEndlessIterateModel extends FractalModelAdapter<Integer> implements FractalIterateModel<Integer> {

    private static final int ITERSTEPS = 300;

	private final FractalIterateBuffer buffer;

	private MultithreadFractalThread thread;

    private class Work implements MultithreadFractalWork {

        public boolean calculate(int x, int y) {
            return FractalEndlessIterateModel.this.calculate(x, y);
        }

        public void notifyViews() {
            setChanged();
            notifyObservers();
        }
        
    }
    
	private FractalEndlessIterateModel(FractalIterateBuffer buffer) {
		super(buffer);
		this.buffer = buffer;
	}

	public FractalEndlessIterateModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
		this(new FractalIterateBuffer(width, height, from, to, iteratorFactory, maxiter, julia));
	}

	public FractalEndlessIterateModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
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

	public void setIteratorFactory(FractalIteratorFactory<Coordinate> iteratorFactory) {
		stopCalculation();
		buffer.setIteratorFactory(iteratorFactory);
		startCalculation();
	}

    public FractalIteratorFactory<Coordinate> getIteratorFactory() {
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

    public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		stopCalculation();
		buffer.setIteratorData(iteratorFactory, maxiter);
		startCalculation();
	}

    protected void finalize() throws Throwable {
        stopCalculationSilent();
        super.finalize();
    }

    public FractalMetadata getMetadata() {
        FractalMetadata metadata = new FractalMetadata();
        metadata.setType(isJulia() ? "Julia Set" : "Mandelbrot Set");
        metadata.setIterationType(true);
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setSize(getWidth(), getHeight());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        return metadata;
    }

}
