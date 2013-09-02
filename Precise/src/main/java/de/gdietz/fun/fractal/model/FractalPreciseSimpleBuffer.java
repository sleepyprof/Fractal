package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.BigCoordinate;

public class FractalPreciseSimpleBuffer extends FractalPreciseCanvasImpl implements FractalPreciseWritableBuffer<Integer> {

    private boolean ready = false;

    private int[][] data;

	public FractalPreciseSimpleBuffer(int width, int height, BigCoordinate from, BigCoordinate to) {
		super(width, height, from, to);
		data = new int[width][height];
	    ready = true;
    }

	public void clearData() {
        ready = false;
		data = new int[getWidth()][getHeight()];
        ready = true;
	}

	public void setSize(int width, int height) {
		ready = false;
        super.setSize(width, height);
		data = new int[width][height];
        ready = true;
	}

	public Integer getData(int x, int y) {
		return ready ? data[x][y] : 0;
	}

	public void setData(int x, int y, Integer index) {
		if (ready)
            data[x][y] = index;
	}

	public void increaseData(int x, int y) {
		if (ready)
            data[x][y]++;
	}

	public void increaseData(int x, int y, int diff) {
		if (ready)
            data[x][y] += diff;
	}

}
