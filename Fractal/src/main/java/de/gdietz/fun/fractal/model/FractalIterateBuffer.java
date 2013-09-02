package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.controller.FractalIteratorManager;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalIterateBuffer extends FractalCanvasImpl implements FractalBuffer<Integer>, FractalIteratorManager<Coordinate> {

	private FractalIteratorFactory<Coordinate> iteratorFactory;

	private final boolean julia;

    private boolean ready = false;

	private int data[][];
	private FractalIterator<Coordinate>[][] iterators;

	@SuppressWarnings("unused")
    public FractalIterateBuffer(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
		super(width, height, from, to);
		this.iteratorFactory = iteratorFactory;
		this.julia = julia;
		clearData();
	}

    @SuppressWarnings("unchecked")
	public void clearData() {
        ready = false;
		data = new int[getWidth()][getHeight()];
        iterators = (FractalIterator<Coordinate>[][]) new FractalIterator[getWidth()][getHeight()];
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
		FractalIterator<Coordinate> iterator = iterators[x][y];
		if (iterator == null || iterator.isValid())
			return -1;
		return ready ? data[x][y] : 0;
	}

    public boolean isValid(int x, int y) {
        if (!ready)
            return false;
        FractalIterator<Coordinate> iterator = iterators[x][y];
        return iterator == null || iterator.isValid();
    }

	public boolean iterate(int x, int y, int count) {
        if (!ready)
            return false;
		FractalIterator<Coordinate> iterator = iterators[x][y];
        if (iterator == null) {
            Coordinate c = getCoordinate(x, y);
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

	public void setIteratorFactory(FractalIteratorFactory<Coordinate> iteratorFactory) {
		this.iteratorFactory = iteratorFactory;
	}

    public FractalIteratorFactory<Coordinate> getIteratorFactory() {
        return iteratorFactory;
    }

    public void setMaxiter(int maxiter) {
	}

    public int getMaxiter() {
        return 0;
    }

    @SuppressWarnings("unused")
	public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		this.iteratorFactory = iteratorFactory;
	}

}
