package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;

public abstract class FractalModelAdapter<D> extends CalculationObservable implements FractalModel<D> {

	private final FractalBuffer<D> buffer;

	public FractalModelAdapter(FractalBuffer<D> buffer) {
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

	public int getWidth() {
		return buffer.getWidth();
	}

	public int getHeight() {
		return buffer.getHeight();
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

	public void setParameter(Coordinate parameter) {
		stopCalculation();
		buffer.setParameter(parameter);
		startCalculation();
	}

	public Coordinate getParameter() {
		return buffer.getParameter();
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

	public void correctAspectRatio(boolean expand) {
		stopCalculation();
		buffer.correctAspectRatio(expand);
		startCalculation();
	}

	public void clearData() {
		buffer.clearData();
	}

	public D getData(int x, int y) {
		return buffer.getData(x, y);
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

	public FractalBuffer<D> getBuffer() {
		return buffer;
	}

}
