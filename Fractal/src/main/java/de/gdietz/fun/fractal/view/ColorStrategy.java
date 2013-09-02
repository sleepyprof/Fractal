package de.gdietz.fun.fractal.view;

import java.awt.*;

public interface ColorStrategy<D> extends PaletteView {

    public Color get(D data);

}
