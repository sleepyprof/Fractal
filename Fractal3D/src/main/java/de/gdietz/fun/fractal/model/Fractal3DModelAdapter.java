package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate3D;

public abstract class Fractal3DModelAdapter extends CalculationObservable implements Fractal3DModel {

    private final Fractal3DBuffer buffer;

    public Fractal3DModelAdapter(Fractal3DBuffer buffer) {
        this.buffer = buffer;
    }

    public void checkAndNotify() {
        if (isCalculating()) {
            setChanged();
            notifyObservers();
        }
    }

    public void setSize(int size) {
        stopCalculation();
        buffer.setSize(size);
        startCalculation();
    }

    public int getSize() {
        return buffer.getSize();
    }

    public void setCorners(Coordinate3D from, Coordinate3D to) {
        stopCalculation();
        buffer.setCorners(from, to);
        startCalculation();
    }

    public Coordinate3D getCornerFrom() {
        return buffer.getCornerFrom();
    }

    public Coordinate3D getCornerTo() {
        return buffer.getCornerTo();
    }

    public void setParameter(Coordinate3D parameter) {
        stopCalculation();
        buffer.setParameter(parameter);
        startCalculation();
    }

    public Coordinate3D getParameter() {
        return buffer.getParameter();
    }

    public Coordinate3D getCoordinate(int x, int y, int z) {
        return buffer.getCoordinate(x, y, z);
    }

    public int getX(Coordinate3D c) {
        return buffer.getX(c);
    }

    public int getY(Coordinate3D c) {
        return buffer.getY(c);
    }

    public int getZ(Coordinate3D c) {
        return buffer.getZ(c);
    }

    public void correctAspectRatio(boolean expand) {
        stopCalculation();
        buffer.correctAspectRatio(expand);
        startCalculation();
    }

    public void clearData() {
        buffer.clearData();
    }

    public boolean getData(int x, int y, int z) {
        return buffer.getData(x, y, z);
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
//		clearData();
        setChanged();
        notifyObservers();
    }

    public abstract boolean isCalculating();

    public Fractal3DBuffer getBuffer() {
        return buffer;
    }
    
}
