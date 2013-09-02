package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.Fractal2DPairedController;
import de.gdietz.fun.fractal.util.Tuple;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class FractalScaledPictureView<T extends Tuple<T>> extends FractalPictureViewBase<T> {

    private final static int MIN_WIDTH = 200;
    private final static int MIN_HEIGHT = 150;

    public FractalScaledPictureView(FractalPicture<T> picture, Fractal2DPairedController<T> controller) {
        super(picture, controller);

        addComponentListener(new ResizeListener());
    }

    private class ResizeListener extends ComponentAdapter {
        public void componentResized(ComponentEvent e) {
            update();
        }
    }

    public void update() {
        BufferedImage image = picture.getImage();

        int width = Math.max(MIN_WIDTH, getWidth());
        int height = Math.max(MIN_HEIGHT, getHeight());
        double ratio = Math.min((double)width / Math.max(1, image.getWidth()), (double)height / Math.max(1, image.getHeight()));
        int scaledWidth = (int)(image.getWidth() * ratio + 0.5);
        int scaledHeight = (int)(image.getHeight() * ratio + 0.5);

        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledImage.createGraphics();
        AffineTransform at = AffineTransform.getScaleInstance(ratio, ratio);
        g.drawRenderedImage(image,at);

        ImageIcon icon = new ImageIcon(scaledImage);
        setIcon(icon);
    }

    public T getCoordinate(int x, int y) {
        double ratio = Math.min((double)getWidth() / Math.max(1, picture.getWidth()), (double)getHeight() / Math.max(1, picture.getHeight()));
        int origX = ratio==0.0 ? 0 : (int)(x / ratio + 0.5);
        int origY = ratio==0.0 ? 0 : (int)(y / ratio + 0.5);
        return picture.getCoordinate(origX, origY);
    }

}
