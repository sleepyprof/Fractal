package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;

public abstract class FractalAnimModelAdapter<D> extends CalculationObservable implements FractalAnimModel<D> {

	private final FractalAnimBuffer<D> buffer;

	public FractalAnimModelAdapter(FractalAnimBuffer<D> buffer) {
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

	public void setCorners(Coordinate from, Coordinate to) {
		stopCalculation();
		buffer.setCorners(from, to);
		startCalculation();
	}

	public Coordinate getCornerFrom() {
		return buffer.getCornerFrom();
	}

	public Coordinate getCornerTo() {
		return buffer.getCornerTo();
	}

	public void setParameters(Coordinate paramFrom, Coordinate paramTo) {
		stopCalculation();
		buffer.setParameters(paramFrom, paramTo);
		startCalculation();
	}

	public Coordinate getParamFrom() {
		return buffer.getParamFrom();
	}

    public Coordinate getParamTo() {
        return buffer.getParamTo();
    }

    public void setParamPath(Path<Coordinate> paramPath) {
        stopCalculation();
        buffer.setParamPath(paramPath);
        startCalculation();
    }

    public Path<Coordinate> getParamPath() {
        return buffer.getParamPath();
    }

    public Coordinate getCoordinate(int x, int y) {
		return buffer.getCoordinate(x, y);
	}

	public int getX(Coordinate c) {
		return buffer.getX(c);
	}

	public int getY(Coordinate c) {
		return buffer.getY(c);
	}

    public Coordinate getParameter(int frame) {
        return buffer.getParameter(frame);
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

	public FractalAnimBuffer<D> getBuffer() {
		return buffer;
	}

}
