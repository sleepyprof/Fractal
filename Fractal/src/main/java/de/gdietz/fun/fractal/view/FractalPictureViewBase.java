package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.controller.Fractal2DMouseController;
import de.gdietz.fun.fractal.controller.Fractal2DPairedController;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Tuple;
import de.gdietz.gui.swing.MouseDraggingJLabel;

import javax.swing.*;
import java.util.Observable;
import java.io.*;

public abstract class FractalPictureViewBase<T extends Tuple<T>> extends MouseDraggingJLabel implements FractalView, PaletteView, SaveableView, PictureCoordMapper<T> {

	protected final FractalPicture<T> picture;
	protected final Fractal2DPairedController<T> controller;

    public FractalPictureViewBase(FractalPicture<T> picture, Fractal2DPairedController<T> controller) {
		this.picture = picture;
        this.controller = controller;
		picture.addObserver(this);
		update();

        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.TOP);

        Fractal2DMouseController<T> mouseController = new Fractal2DMouseController<>(controller, this, this);
        addMouseListener(mouseController);
        addMouseMotionListener(mouseController);
    }


	public abstract void update();

	public void update(Observable o, Object arg) {
		update();
	}

	public void writeTo(File file) throws IOException {
        picture.writeTo(file);
	}

    public String getExtension() {
        return picture.getExtension();
    }

	public void setPalette(Palette palette) {
		picture.setPalette(palette);
		update();
	}

    public Palette getPalette() {
        return picture.getPalette();
    }

    public abstract T getCoordinate(int x, int y);

}