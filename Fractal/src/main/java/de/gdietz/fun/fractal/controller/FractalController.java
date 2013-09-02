package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Tuple;

public interface FractalController<T extends Tuple<T>> extends Notifiable {

	public void startCalculation();
	public void stopCalculation();

	public void setCorners(T from, T to);

	public void setParameter(T parameter);

	public void correctAspectRatio(boolean expand);
	
}
