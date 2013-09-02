package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;

public class FractalCanvasImpl implements FractalCanvas {

	protected int width;
	protected int height;

	protected Coordinate from;
	protected Coordinate to;

	protected Coordinate parameter;

    private double ratioX;
    private double ratioY;

    public FractalCanvasImpl(int width, int height, Coordinate from, Coordinate to) {
		this.width = width;
		this.height = height;
		this.from = from;
		this.to = to;
        calculateRatios();
        parameter = Coordinate.ORIGIN;
	}

    private void calculateRatios() {
        ratioX = (width <= 1) ? 1.0 : (to.getX() - from.getX()) / (width - 1.0);
        ratioY = (height <= 1) ? 1.0 : (to.getY() - from.getY()) / (height - 1.0);
    }

    public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
        calculateRatios();
    }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setCorners(Coordinate from, Coordinate to) {
		this.from = from;
		this.to = to;
        calculateRatios();
    }

	public Coordinate getCornerFrom() {
		return from;
	}

	public Coordinate getCornerTo() {
		return to;
	}

	public void setParameter(Coordinate parameter) {
		this.parameter = parameter;
	}

	public Coordinate getParameter() {
		return parameter;
	}

	public Coordinate getCoordinate(int x, int y) {
		return new Coordinate(ratioX * x  + from.getX(), -ratioY * y + to.getY());
	}

	public int getX(Coordinate c) {
		return (ratioX == 0.0) ? 0 : (int) Math.round((c.getX() - from.getX()) / ratioX);
	}

	public int getY(Coordinate c) {
		return (ratioY == 0.0) ? 0 : (int) Math.round((c.getY() - to.getY()) / -ratioY);
	}

	public void correctAspectRatio(boolean expand) {
		double deltaX = Math.abs(ratioX);
		double deltaY = Math.abs(ratioY);
		double delta = expand ? Math.max(deltaX, deltaY) : Math.min(deltaX, deltaY);
		double middleX = (to.getX() + from.getX()) / 2.0;
		double middleY = (to.getY() + from.getY()) / 2.0;
		double diffX = delta * (width - 1.0);
		double diffY = delta * (height - 1.0);
		from = new Coordinate(middleX - 0.5 * diffX, middleY - 0.5 * diffY);
		to = new Coordinate(middleX + 0.5 * diffX, middleY + 0.5 * diffY);
        calculateRatios();
    }

}
