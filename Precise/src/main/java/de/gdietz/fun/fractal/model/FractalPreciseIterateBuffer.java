package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.controller.FractalIteratorManager;
import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.BigCoordinate;

public class FractalPreciseIterateBuffer extends FractalPreciseCanvasImpl implements FractalPreciseBuffer<Integer>, FractalIteratorManager<BigCoordinate> {

	private FractalIteratorFactory<BigCoordinate> iteratorFactory;

	private final boolean julia;

    private boolean ready = false;

	private int data[][];
	private FractalIterator<BigCoordinate>[][] iterators;

	@SuppressWarnings("unused")
    public FractalPreciseIterateBuffer(int width, int height, BigCoordinate from, BigCoordinate to, FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter, boolean julia) {
		super(width, height, from, to);
		this.iteratorFactory = iteratorFactory;
		this.julia = julia;
		clearData();
	}

    @SuppressWarnings("unchecked")
	public void clearData() {
        ready = false;
		data = new int[getWidth()][getHeight()];
        iterators = (FractalIterator<BigCoordinate>[][]) new FractalIterator[getWidth()][getHeight()];
        ready = true;
	}

	public void setSize(int width, int height) {
		ready = false;
        super.setSize(width, height);
		clearData();
        ready = true;
	}

    public boolean isJulia() {
        return julia;
    }

	public Integer getData(int x, int y) {
        if (!ready)
            return 0;
		FractalIterator<BigCoordinate> iterator = iterators[x][y];
		if (iterator == null || iterator.isValid())
			return -1;
		return ready ? data[x][y] : 0;
	}

    public boolean isValid(int x, int y) {
        if (!ready)
            return false;
        FractalIterator<BigCoordinate> iterator = iterators[x][y];
        return iterator == null || iterator.isValid();
    }

	public boolean iterate(int x, int y, int count) {
        if (!ready)
            return false;
		FractalIterator<BigCoordinate> iterator = iterators[x][y];
        if (iterator == null) {
            BigCoordinate c = getCoordinate(x, y);
            iterator = julia ? iteratorFactory.get(getParameter(), c) : iteratorFactory.get(c, getParameter());
            iterators[x][y] = iterator;
        }
        int iter = 0;
		while (iterator.isValid() && iter < count) {
			iterator.iterate();
			data[x][y]++;
			iter++;
		}
        return iterator.isValid();
	}

	public boolean iterate(int x, int y) {
		return iterate(x, y, 1);
	}

	public void setIteratorFactory(FractalIteratorFactory<BigCoordinate> iteratorFactory) {
		this.iteratorFactory = iteratorFactory;
	}

    public FractalIteratorFactory<BigCoordinate> getIteratorFactory() {
        return iteratorFactory;
    }

    public void setMaxiter(int maxiter) {
	}

    public int getMaxiter() {
        return 0;
    }

    @SuppressWarnings("unused")
	public void setIteratorData(FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter) {
		this.iteratorFactory = iteratorFactory;
	}

}
