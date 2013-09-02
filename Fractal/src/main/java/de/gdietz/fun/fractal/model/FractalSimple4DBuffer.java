package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;

public class FractalSimple4DBuffer extends FractalCanvasImpl implements FractalBuffer<Integer> {

    private static final double YJ_PART = 1.0/2.0 / (1.0 + 1.0/2.0 + 1.0/3.0);
    private static final double YK_PART = 1.0/3.0 / (1.0 + 1.0/2.0 + 1.0/3.0);

    private int lengthX;
    private int lengthYi;
    private int lengthYj;
    private int lengthYk;

    private double ratioX;
    private double ratioYi;
    private double ratioYj;
    private double ratioYk;

    private boolean ready = false;

    private int[][] data;

    private void calculateLengths(int width, int height) {
        int min = Math.min(width, height);
        lengthYj = (int)(min * YJ_PART + 0.5) + 1;
        lengthYk = (int)(min * YK_PART + 0.5) + 1;
        lengthX = width - lengthYj - lengthYk + 2;
        lengthYi = height - lengthYj - lengthYk;
    }

    public void calculateRatios() {
        ratioX = (lengthX <= 1) ? 1.0 : (to.getX() - from.getX()) / (lengthX - 1.0);
        ratioYi = (lengthYi <= 1) ? 1.0 : (to.getY() - from.getY()) / (lengthYi - 1.0);
        ratioYj = (Math.abs(ratioX) + Math.abs(ratioYi)) / Math.sqrt(2.0);
        ratioYk = ratioYj;
    }

    public FractalSimple4DBuffer(int width, int height, Coordinate from, Coordinate to) {
		super(width, height, from, to);
        calculateLengths(width, height);
        calculateRatios();
        data = new int[width][height];
        ready = true;
	}

    public int getLengthX() {
        return lengthX;
    }

    public int getLengthYi() {
        return lengthYi;
    }

    public int getLengthYj() {
        return lengthYj;
    }

    public int getLengthYk() {
        return lengthYk;
    }

    public void clearData() {
        ready = false;
        data = new int[getWidth()][getHeight()];
        ready = true;
	}

	public void setSize(int width, int height) {
		ready = false;
        super.setSize(width, height);
        calculateLengths(width, height);
        calculateRatios();
        data = new int[width][height];
        ready = true;
	}

    public void setCorners(Coordinate from, Coordinate to) {
        super.setCorners(from, to);
        calculateRatios();
    }

    public Integer getData(int x, int y) {
		return ready ? data[x][y] : 0;
	}

	public void setData(int x, int y, int index) {
        if (ready)
		    data[x][y] = index;
	}

    @SuppressWarnings("unused")
    public Coordinate getCoordinate(int x, int yi, int yj, int yk) {
        return new Coordinate(ratioX  * x + from.getX(), ratioYi * yi + from.getY());
    }

    @SuppressWarnings("unused")
    public Coordinate getParameter(int x, int yi, int yj, int yk) {
        return new Coordinate(ratioYj  * yj + parameter.getX(), ratioYk * yk + parameter.getY());
    }

    public Coordinate getCoordinate(int x, int y) {
        return getCoordinate(x - lengthYk + 1, lengthYk + lengthYj + lengthYi - y - 3, 0, 0);
    }

    public int getX(Coordinate c) {
        return (ratioX == 0.0) ? 0 : (int) Math.round((c.getX() - from.getX()) / ratioX);
    }

    public int getY(Coordinate c) {
        return (ratioYi == 0.0) ? 0 : (int) Math.round((c.getY() - to.getY()) / -ratioYi);
    }

    @SuppressWarnings("unused")
    public int getX(int x, int yi, int yj, int yk) {
        return lengthYk - yk - 1 + x + yj;
    }

    @SuppressWarnings("unused")
    public int getY(int x, int yi, int yj, int yk) {
        return lengthYk - yk - 1 + lengthYj - yj - 1 + lengthYi - yi - 1;
    }

    public void setData(int x, int yi, int yj, int yk, int index) {
        setData(getX(x, yi, yj, yk), getY(x, yi, yj, yk), index);
    }

    public void correctAspectRatio(boolean expand) {
        double deltaX = Math.abs(ratioX);
        double deltaY = Math.abs(ratioYi);
        double delta = expand ? Math.max(deltaX, deltaY) : Math.min(deltaX, deltaY);
        double middleX = (to.getX() + from.getX()) / 2.0;
        double middleY = (to.getY() + from.getY()) / 2.0;
        double diffX = delta * (lengthX - 1.0);
        double diffY = delta * (lengthYi - 1.0);
        from = new Coordinate(middleX - 0.5 * diffX, middleY - 0.5 * diffY);
        to = new Coordinate(middleX + 0.5 * diffX, middleY + 0.5 * diffY);
        calculateRatios();
    }

}