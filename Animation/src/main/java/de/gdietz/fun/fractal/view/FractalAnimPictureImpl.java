package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.model.FractalAnimModel;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.imageio.AnimWriter;
import de.gdietz.imageio.ImageDeliverer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

public class FractalAnimPictureImpl<D> implements FractalAnimPicture<Coordinate>, ImageDeliverer {

    private final FractalAnimModel<D> model;

    private final ColorStrategy<D> coloring;

    private Component parent;

    public FractalAnimPictureImpl(FractalAnimModel<D> model, ColorStrategy<D> coloring) {
        this.model = model;
        this.coloring = coloring;
    }

    public BufferedImage getImage(int frame) {
        BufferedImage image = new BufferedImage(
                model.getWidth(), model.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < model.getHeight(); y++) {
            for (int x = 0; x < model.getWidth(); x++) {
                D data = model.getData(x, y, frame);
                image.setRGB(x, y, 0xFF000000 + coloring.get(data).getRGB());
            }
        }
        return image;
    }

    public void writeTo(File file) throws IOException {
        AnimWriter writer = AnimWriter.create(this, file, parent);
        FractalMetadata metadata = model.getMetadata();
        metadata.setColorStrategy(coloring);
        writer.addMetadata(metadata);
        writer.write();
    }

    public String getExtension() {
        return AnimWriter.DEFAULT_EXTENSION;
    }

    public void setPalette(Palette palette) {
        coloring.setPalette(palette);
    }

    public Palette getPalette() {
        return coloring.getPalette();
    }

    public Coordinate getCoordinate(int x, int y) {
        return model.getCoordinate(x, y);
    }

    public int getWidth() {
        return model.getWidth();
    }

    public int getHeight() {
        return model.getHeight();
    }

    public int getFrames() {
        return model.getFrames();
    }

    public void addObserver(Observer o) {
        model.addObserver(o);
        if (o instanceof Component && o instanceof FractalAnimPictureView)
            parent = (Component) o;
    }

}