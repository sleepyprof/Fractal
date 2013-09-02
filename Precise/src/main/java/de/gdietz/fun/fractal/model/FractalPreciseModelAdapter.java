package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.BigCoordinate;

public abstract class FractalPreciseModelAdapter<D> extends CalculationObservable implements FractalPreciseModel<D> {

    private final FractalPreciseBuffer<D> buffer;

    protected FractalPreciseModelAdapter(FractalPreciseBuffer<D> buffer) {
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

    public void setCorners(BigCoordinate from, BigCoordinate to) {
        stopCalculation();
        buffer.setCorners(from, to);
        startCalculation();
    }

    public BigCoordinate getCornerFrom() {
        return buffer.getCornerFrom();
    }

    public BigCoordinate getCornerTo() {
        return buffer.getCornerTo();
    }

    public void setParameter(BigCoordinate parameter) {
        stopCalculation();
        buffer.setParameter(parameter);
        startCalculation();
    }

    public BigCoordinate getParameter() {
        return buffer.getParameter();
    }

    public BigCoordinate getCoordinate(int x, int y) {
        return buffer.getCoordinate(x, y);
    }

    public int getX(BigCoordinate c) {
        return buffer.getX(c);
    }

    public int getY(BigCoordinate c) {
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

    public FractalPreciseBuffer<D> getBuffer() {
        return buffer;
    }

}
