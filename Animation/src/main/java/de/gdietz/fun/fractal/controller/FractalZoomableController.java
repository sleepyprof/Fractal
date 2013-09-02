package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Tuple;

public interface FractalZoomableController<T extends Tuple<T>> extends Notifiable {

    public void startCalculation();
	public void stopCalculation();

	public void setCorners(T fromStart, T toStart, T fromEnd, T toEnd);

	public void setParameter(T parameter);

	public void correctAspectRatio(boolean expand);

}
