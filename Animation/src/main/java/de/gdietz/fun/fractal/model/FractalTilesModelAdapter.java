package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;

public abstract class FractalTilesModelAdapter<D> extends CalculationObservable implements FractalTilesModel<D> {

    private final FractalTilesBuffer<D> buffer;

    public FractalTilesModelAdapter(FractalTilesBuffer<D> buffer) {
        this.buffer = buffer;
    }

    public void checkAndNotify() {
        if (isCalculating()) {
            setChanged();
            notifyObservers();
        }
    }

    public void setSize(int tileWidth, int tileHeight, int tilesX, int tilesY) {
        stopCalculation();
        buffer.setSize(tileWidth, tileHeight, tilesX, tilesY);
        startCalculation();
    }

    public void setTiles(int tilesX, int tilesY) {
        stopCalculation();
        buffer.setTiles(tilesX, tilesY);
        startCalculation();
    }

    public int getTileWidth() {
        return buffer.getTileWidth();
    }

    public int getTileHeight() {
        return buffer.getTileHeight();
    }

    public int getTilesX() {
        return buffer.getTilesX();
    }

    public int getTilesY() {
        return buffer.getTilesY();
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

    public Coordinate getCoordinate(int x, int y) {
        return buffer.getCoordinate(x, y);
    }

    public int getX(Coordinate c) {
        return buffer.getX(c);
    }

    public int getY(Coordinate c) {
        return buffer.getY(c);
    }

    public Coordinate getParameter(int tileX, int tileY) {
        return buffer.getParameter(tileX, tileY);
    }

    public void correctAspectRatio(boolean expand) {
        stopCalculation();
        buffer.correctAspectRatio(expand);
        startCalculation();
    }

    public void clearData() {
        buffer.clearData();
    }

    public D getData(int x, int y, int tileX, int tileY) {
        return buffer.getData(x, y, tileX, tileY);
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

    public FractalTilesBuffer<D> getBuffer() {
        return buffer;
    }

}
