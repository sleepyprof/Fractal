package de.gdietz.fun.fractal.view;

public interface CalculationListener {

    public void setCalculating(boolean calculating);
    public void setProgress(int progress, int total);

}
