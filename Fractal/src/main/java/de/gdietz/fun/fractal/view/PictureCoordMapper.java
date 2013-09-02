package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.util.Tuple;

public interface PictureCoordMapper<T extends Tuple<T>> {

    public T getCoordinate(int x, int y);

}
