package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.controller.Notifiable;
import de.gdietz.fun.fractal.view.CalculationListener;

import java.util.Observer;

public interface FractalModelBase extends Notifiable {

	public void addObserver(Observer o);
    public void addCalculationListener(CalculationListener listener);

	public void startCalculation();
	public void stopCalculation();
	public boolean isCalculating();

}
