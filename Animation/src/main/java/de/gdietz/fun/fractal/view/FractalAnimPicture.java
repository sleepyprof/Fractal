package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.util.Tuple;

import java.awt.image.BufferedImage;
import java.util.Observer;

public interface FractalAnimPicture<T extends Tuple<T>> extends PaletteView, SaveableView, PictureCoordMapper<T> {

    public BufferedImage getImage(int frame);

    public int getWidth();
    public int getHeight();
    public int getFrames();

    public void addObserver(Observer o);

}