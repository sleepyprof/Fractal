package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.FractalAnimPairedController;
import de.gdietz.fun.fractal.util.Tuple;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class FractalAnimPictureView<T extends Tuple<T>> extends FractalAnimPictureViewBase<T> {

    public FractalAnimPictureView(FractalAnimPicture<T> picture, FractalAnimPairedController<T> controller) {
        super(picture, controller);

        addComponentListener(new ResizeListener());
    }

    private class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            stopAnimation();
            controller.setSize(getSize().width, getSize().height);
            startAnimation();
        }
    }

    public synchronized void update(int currentFrame, int frames) {
        BufferedImage image = picture.getImage(currentFrame);
        ImageIcon icon = new ImageIcon(image);
        setIcon(icon);
    }

    public T getCoordinate(int x, int y) {
        return picture.getCoordinate(x, y);
    }

}




