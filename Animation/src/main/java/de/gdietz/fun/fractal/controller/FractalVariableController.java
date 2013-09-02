package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Tuple;

public interface FractalVariableController<T extends Tuple<T>> extends Notifiable {

    public void startCalculation();
	public void stopCalculation();

	public void setCorners(T from, T to);

	public void setParameters(T paramFrom, T paramTo);

	public void correctAspectRatio(boolean expand);

}
