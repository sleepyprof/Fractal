package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.Fractal2DPairedController;
import de.gdietz.fun.fractal.util.Tuple;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FractalPictureView<T extends Tuple<T>> extends FractalPictureViewBase<T> {

    public FractalPictureView(FractalPicture<T> picture, Fractal2DPairedController<T> controller) {
        super(picture, controller);

        addComponentListener(new ResizeListener());
    }

	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			controller.setSize(getSize().width, getSize().height);
		}
	}

	public void update() {
		BufferedImage image = picture.getImage();
		ImageIcon icon = new ImageIcon(image);
		setIcon(icon);
	}

    public T getCoordinate(int x, int y) {
        return picture.getCoordinate(x, y);
    }

}
