package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;

public class FractalAnimCanvasImpl implements FractalAnimCanvas {

	protected int width;
	protected int height;
    protected int frames;

	protected Coordinate from;
	protected Coordinate to;

	protected Path<Coordinate> paramPath;

    private double ratioX;
    private double ratioY;
    private double framesInv;

    public FractalAnimCanvasImpl(int width, int height, int frames, Coordinate from, Coordinate to, Path<Coordinate> paramPath) {
		this.width = width;
		this.height = height;
        this.frames = frames;
		this.from = from;
		this.to = to;
        this.paramPath = paramPath;
        calculateRatios();
	}

    private void calculateRatios() {
        ratioX = (width <= 1) ? 1.0 : (to.getX() - from.getX()) / (width - 1.0);
        ratioY = (height <= 1) ? 1.0 : (to.getY() - from.getY()) / (height - 1.0);
        framesInv = (frames <= 1) ? 1.0 : 1.0 / (frames - 1.0);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        calculateRatios();
    }

    public void setSize(int width, int height, int frames) {
		this.width = width;
		this.height = height;
        this.frames = frames;
        calculateRatios();
    }

    public void setFrames(int frames) {
        this.frames = frames;
        calculateRatios();
    }

    public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

    public int getFrames() {
        return frames;
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

	public void setParameters(Coordinate paramFrom, Coordinate paramTo) {
        paramPath.setLimits(paramFrom, paramTo);
	}

    public Coordinate getParamFrom() {
        return paramPath.getFrom();
    }

    public Coordinate getParamTo() {
        return paramPath.getTo();
    }

    public void setParamPath(Path<Coordinate> paramPath) {
        this.paramPath = paramPath;
    }

    public Path<Coordinate> getParamPath() {
        return paramPath;
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

    public Coordinate getParameter(int frame) {
        return paramPath.get(frame * framesInv);
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
