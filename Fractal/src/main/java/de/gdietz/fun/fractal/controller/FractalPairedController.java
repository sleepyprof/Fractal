package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.util.Tuple;

public interface FractalPairedController<T extends Tuple<T>> {

    public void setPoint(T p);
    public void dragPoint(T p);
    public void setCorners(T from, T to);

    public void setPointOther(T p);
    public void dragPointOther(T p);
    public void setCornersOther(T from, T to);

}
