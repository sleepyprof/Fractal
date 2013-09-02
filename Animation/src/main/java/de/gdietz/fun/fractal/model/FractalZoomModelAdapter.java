package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;

public abstract class FractalZoomModelAdapter<D> extends CalculationObservable implements FractalZoomModel<D> {

	private final FractalZoomBuffer<D> buffer;

	public FractalZoomModelAdapter(FractalZoomBuffer<D> buffer) {
		this.buffer = buffer;
	}

	public void checkAndNotify() {
		if (isCalculating()) {
			setChanged();
			notifyObservers();
		}
	}

    public void setSize(int width, int height) {
        stopCalculation();
        buffer.setSize(width, height);
        startCalculation();
    }

	public void setSize(int width, int height, int frames) {
		stopCalculation();
		buffer.setSize(width, height, frames);
		startCalculation();
	}

    public void setFrames(int frames) {
        stopCalculation();
        buffer.setFrames(frames);
        startCalculation();
    }

    public int getWidth() {
		return buffer.getWidth();
	}

	public int getHeight() {
		return buffer.getHeight();
	}

    public int getFrames() {
        return buffer.getFrames();
    }

    public void setCorners(Coordinate fromStart, Coordinate toStart, Coordinate fromEnd, Coordinate toEnd) {
        stopCalculation();
        buffer.setCorners(fromStart, toStart, fromEnd, toEnd);
        startCalculation();
    }

    public Coordinate getCornerFromStart() {
        return buffer.getCornerFromStart();
    }

    public Coordinate getCornerToStart() {
        return buffer.getCornerToStart();
    }

    public Coordinate getCornerFromEnd() {
        return buffer.getCornerFromEnd();
    }

    public Coordinate getCornerToEnd() {
        return buffer.getCornerToEnd();
    }

    public void setCornerPaths(Path<Coordinate> fromPath, Path<Coordinate> toPath) {
        stopCalculation();
        buffer.setCornerPaths(fromPath, toPath);
        startCalculation();
    }

    public Path<Coordinate> getFromPath() {
        return buffer.getFromPath();
    }

    public Path<Coordinate> getToPath() {
        return buffer.getToPath();
    }

    public void setParameter(Coordinate parameter) {
        stopCalculation();
        buffer.setParameter(parameter);
        startCalculation();
    }

    public Coordinate getParameter() {
        return buffer.getParameter();
    }

    public Coordinate getFrom(int frame) {
        return buffer.getFrom(frame);
    }

    public Coordinate getTo(int frame) {
        return buffer.getTo(frame);
    }

    public Coordinate getCoordinate(int x, int y, int frame) {
        return buffer.getCoordinate(x, y, frame);
    }

    public int getX(Coordinate c, int frame) {
        return buffer.getX(c, frame);
    }

    public int getY(Coordinate c, int frame) {
        return buffer.getY(c, frame);
    }

	public void correctAspectRatio(boolean expand) {
		stopCalculation();
		buffer.correctAspectRatio(expand);
		startCalculation();
	}

	public void clearData() {
		buffer.clearData();
	}

	public D getData(int x, int y, int frame) {
		return buffer.getData(x, y, frame);
	}

	protected abstract void stopCalculationSilent();

	protected abstract void startCalculationSilent();

	public void startCalculation() {
		stopCalculationSilent();
		clearData();
		startCalculationSilent();
		setChanged();
		notifyObservers();
	}

	public void stopCalculation() {
		stopCalculationSilent();
		setChanged();
		notifyObservers();
	}

	public abstract boolean isCalculating();

	public FractalZoomBuffer<D> getBuffer() {
		return buffer;
	}

}
