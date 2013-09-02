package de.gdietz.fun.fractal.model.util;

public interface MultithreadFractalWork {

    public boolean calculate(int x, int y);

    public void notifyViews();

}
