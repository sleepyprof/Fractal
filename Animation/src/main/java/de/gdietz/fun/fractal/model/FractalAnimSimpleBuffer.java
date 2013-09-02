package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;

public class FractalAnimSimpleBuffer extends FractalAnimCanvasImpl implements FractalAnimWritableBuffer<Integer> {

    private boolean ready = false;

    private int[][][] data;

    public FractalAnimSimpleBuffer(int width, int height, int frames, Coordinate from, Coordinate to, Path<Coordinate> paramPath) {
        super(width, height, frames, from, to, paramPath);
        data = new int[width][height][frames];
        ready = true;
    }

    public void clearData() {
        ready = false;
        data = new int[getWidth()][getHeight()][getFrames()];
        ready = true;
    }

	public void setSize(int width, int height, int frames) {
		ready = false;
        super.setSize(width, height, frames);
		data = new int[width][height][frames];
        ready = true;
	}

	public Integer getData(int x, int y, int frame) {
		return ready ? data[x][y][frame] : 0;
	}

	public void setData(int x, int y, int frame, Integer index) {
		if (ready)
            data[x][y][frame] = index;
	}

}
