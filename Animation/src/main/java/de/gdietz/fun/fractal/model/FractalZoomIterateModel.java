package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.controller.FractalIteratorManager;
import de.gdietz.fun.fractal.util.Coordinate;

public interface FractalZoomIterateModel<D> extends FractalZoomModel<D>, FractalIteratorManager<Coordinate> {
}
