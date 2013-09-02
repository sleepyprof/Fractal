package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.newton.IterateInfo;
import de.gdietz.fun.fractal.util.Coordinate;


public class FractalInfoBuffer<I> extends FractalCanvasImpl implements FractalWritableBuffer<IterateInfo<I>> {

    private final IterateInfo<I> INVALID = new IterateInfo<I>();

    private boolean ready = false;

    private IterateInfo<I>[][] data;

	public FractalInfoBuffer(int width, int height, Coordinate from, Coordinate to) {
		super(width, height, from, to);
        clearData();
        ready = true;
	}

    @SuppressWarnings("unchecked")
	public void clearData() {
        ready = false;
		data = (IterateInfo<I>[][]) new IterateInfo[getWidth()][getHeight()];
        ready = true;
	}

	public void setSize(int width, int height) {
		ready = false;
        super.setSize(width, height);
		clearData();
        ready = true;
	}

	public IterateInfo<I> getData(int x, int y) {
        IterateInfo<I> result = ready ? data[x][y] : null;
        return result == null ? INVALID : result;
	}

    public void setData(int x, int y, IterateInfo<I> info) {
        if (ready)
            data[x][y] = info;
    }

}
