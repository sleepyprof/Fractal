package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;
import de.gdietz.fun.fractal.util.ZoomPathPair;

public class FractalZoomCanvasImpl implements FractalZoomCanvas {

	protected int width;
	protected int height;
    protected int frames;

	protected ZoomPathPair<Coordinate> pathPair;

	protected Coordinate parameter;

    private double framesInv;

    public FractalZoomCanvasImpl(int width, int height, int frames, Path<Coordinate> fromPath, Path<Coordinate> toPath) {
		this.width = width;
		this.height = height;
        this.frames = frames;
        pathPair = new ZoomPathPair<>(fromPath, toPath);
        calculateRatios();
        parameter = Coordinate.ORIGIN;
	}

    private void calculateRatios() {
        framesInv = (frames <= 1) ? 1.0 : 1.0 / (frames - 1.0);
    }

    private double calculateRatioX(double t) {
        return (width <= 1) ? 1.0 : (pathPair.getToPath().get(t).getX() - pathPair.getFromPath().get(t).getX()) / (width - 1.0);
    }

    private double calculateRatioY(double t) {
        return (height <= 1) ? 1.0 : (pathPair.getToPath().get(t).getY() - pathPair.getFromPath().get(t).getY()) / (height - 1.0);
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

    public void setCorners(Coordinate fromStart, Coordinate toStart, Coordinate fromEnd, Coordinate toEnd) {
        pathPair.setLimits(fromStart, toStart, fromEnd, toEnd);
        calculateRatios();
    }

    public Coordinate getCornerFromStart() {
        return pathPair.getFromPath().getFrom();
    }

    public Coordinate getCornerToStart() {
        return pathPair.getToPath().getFrom();
    }

    public Coordinate getCornerFromEnd() {
        return pathPair.getFromPath().getTo();
    }

    public Coordinate getCornerToEnd() {
        return pathPair.getToPath().getTo();
    }

    public void setCornerPaths(Path<Coordinate> fromPath, Path<Coordinate> toPath) {
        pathPair.setPaths(fromPath, toPath);
        calculateRatios();
    }

    public Path<Coordinate> getFromPath() {
        return pathPair.getFromPath();
    }

    public Path<Coordinate> getToPath() {
        return pathPair.getToPath();
    }

    public void setParameter(Coordinate parameter) {
        this.parameter = parameter;
    }

    public Coordinate getParameter() {
        return parameter;
    }

    public Coordinate getFrom(int frame) {
        return pathPair.getFromPath().get(frame * framesInv);
    }
    
    public Coordinate getTo(int frame) {
        return pathPair.getToPath().get(frame * framesInv);
    }
    
    public Coordinate getCoordinate(int x, int y, int frame) {
        double ratioX = calculateRatioX(frame * framesInv);
        double ratioY = calculateRatioY(frame * framesInv);
        return new Coordinate(ratioX * x  + getFrom(frame).getX(), -ratioY * y + getTo(frame).getY());
    }

    public int getX(Coordinate c, int frame) {
        double ratioX = calculateRatioX(frame * framesInv);
        return (ratioX == 0.0) ? 0 : (int) Math.round((c.getX() - getFrom(frame).getX()) / ratioX);
    }

    public int getY(Coordinate c, int frame) {
        double ratioY = calculateRatioY(frame * framesInv);
        return (ratioY == 0.0) ? 0 : (int) Math.round((c.getY() - getTo(frame).getY()) / -ratioY);
    }

	public void correctAspectRatio(boolean expand) {
        double ratioStartX = calculateRatioX(0.0);
        double ratioStartY = calculateRatioY(0.0);
        double ratioEndX = calculateRatioX(1.0);
        double ratioEndY = calculateRatioY(1.0);
        double deltaStartX = Math.abs(ratioStartX);
        double deltaStartY = Math.abs(ratioStartY);
        double deltaEndX = Math.abs(ratioEndX);
        double deltaEndY = Math.abs(ratioEndY);
        double deltaStart = expand ? Math.max(deltaStartX, deltaStartY) : Math.min(deltaStartX, deltaStartY);
        double deltaEnd = expand ? Math.max(deltaEndX, deltaEndY) : Math.min(deltaEndX, deltaEndY);
        double middleStartX = (pathPair.getToPath().getFrom().getX() + pathPair.getFromPath().getFrom().getX()) / 2.0;
        double middleStartY = (pathPair.getToPath().getFrom().getY() + pathPair.getFromPath().getFrom().getY()) / 2.0;
        double middleEndX = (pathPair.getToPath().getTo().getX() + pathPair.getFromPath().getTo().getX()) / 2.0;
        double middleEndY = (pathPair.getToPath().getTo().getY() + pathPair.getFromPath().getTo().getY()) / 2.0;
        double diffStartX = deltaStart * (width - 1.0);
        double diffStartY = deltaStart * (height - 1.0);
        double diffEndX = deltaEnd * (width - 1.0);
        double diffEndY = deltaEnd * (height - 1.0);
        Coordinate fromStart = new Coordinate(middleStartX - 0.5 * diffStartX, middleStartY - 0.5 * diffStartY);
        Coordinate toStart = new Coordinate(middleStartX + 0.5 * diffStartX, middleStartY + 0.5 * diffStartY);
        Coordinate fromEnd = new Coordinate(middleEndX - 0.5 * diffEndX, middleEndY - 0.5 * diffEndY);
        Coordinate toEnd = new Coordinate(middleEndX + 0.5 * diffEndX, middleEndY + 0.5 * diffEndY);
        pathPair.setLimits(fromStart, toStart, fromEnd, toEnd);
        calculateRatios();
    }

}
