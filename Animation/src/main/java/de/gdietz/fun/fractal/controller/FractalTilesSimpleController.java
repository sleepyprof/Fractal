package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.FractalTilesIterateModel;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalTilesSimpleController implements FractalTilesIterateController {

    private final FractalTilesIterateModel<?> model;

    public FractalTilesSimpleController(FractalTilesIterateModel<?> model) {
        this.model = model;
    }

    public void startCalculation() {
        model.startCalculation();
    }

    public void stopCalculation() {
        model.stopCalculation();
    }

    public void setSize(int width, int height, int tilesX, int tilesY) {
        if (width == model.getWidth() && height == model.getHeight() && tilesX == model.getTilesX() && tilesY == model.getTilesY())
            return;
        model.setSize(width, height, tilesX, tilesY);
    }

    public void setSize(int width, int height) {
        model.setSize(width, height);
    }

    public void setTiles(int tilesX, int tilesY) {
        model.setTiles(tilesX, tilesY);
    }

    public void setCorners(Coordinate from, Coordinate to) {
        model.setCorners(from, to);
    }

    public void setParameters(Coordinate paramFrom, Coordinate paramTo) {
        model.setParameters(paramFrom, paramTo);
    }

    public void correctAspectRatio(boolean expand) {
        model.correctAspectRatio(expand);
    }

    public void setIteratorFactory(FractalIteratorFactory<Coordinate> iteratorFactory) {
        model.setIteratorFactory(iteratorFactory);
    }

    public void setMaxiter(int maxiter) {
        model.setMaxiter(maxiter);
    }

    public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
        model.setIteratorData(iteratorFactory, maxiter);
    }

    public void checkAndNotify() {
        model.checkAndNotify();
    }
    
}
