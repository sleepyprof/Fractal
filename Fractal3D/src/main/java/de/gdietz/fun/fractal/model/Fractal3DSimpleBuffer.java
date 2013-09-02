package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate3D;

public class Fractal3DSimpleBuffer extends Fractal3DCanvasImpl implements Fractal3DBuffer {

    private boolean ready = false;

    private boolean[][][] data;

	public Fractal3DSimpleBuffer(int size, Coordinate3D from, Coordinate3D to) {
		super(size, from, to);
		data = new boolean[size][size][size];
        ready = true;
	}

    public void clearData() {
        ready = false;
		data = new boolean[getSize()][getSize()][getSize()];
        ready = true;
	}

	public void setSize(int size) {
        ready = false;
		super.setSize(size);
		data = new boolean[size][size][size];
        ready = true;
	}

	public boolean getData(int x, int y, int z) {
		return ready && data[x][y][z];
	}

	public void setData(int x, int y, int z, boolean inside) {
		if (ready)
            data[x][y][z] = inside;
	}

}
