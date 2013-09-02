package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.model.FractalTilesModel;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.imageio.PNGWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

public class FractalTilesPicture<D> implements FractalPicture<Coordinate> {

    private final FractalTilesModel<D> model;

    private final ColorStrategy<D> coloring;

    public FractalTilesPicture(FractalTilesModel<D> model, ColorStrategy<D> coloring) {
        this.model = model;
        this.coloring = coloring;
    }

    public BufferedImage getImage() {
        BufferedImage image = new BufferedImage(
                model.getWidth(), model.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int tileY = 0; tileY < model.getTilesY(); tileY++) {
            for (int tileX = 0; tileX < model.getTilesX(); tileX++) {
                for (int y = 0; y < model.getTileHeight(); y++) {
                    for (int x = 0; x < model.getTileWidth(); x++) {
                        D data = model.getData(x, y, tileX, tileY);
                        image.setRGB(x + tileX * model.getTileWidth(), y + tileY * model.getTileHeight(), 0xFF000000 + coloring.get(data).getRGB());
                    }
                }
            }
        }
        return image;
    }

    public void writeTo(File file) throws IOException {
        BufferedImage image = getImage();
        PNGWriter writer = new PNGWriter(image, file);
        FractalMetadata metadata = model.getMetadata();
        metadata.setColorStrategy(coloring);
        writer.addMetadata(metadata);
        writer.write();
    }

    public String getExtension() {
        return "png";
    }

    public void setPalette(Palette palette) {
        coloring.setPalette(palette);
    }

    public Palette getPalette() {
        return coloring.getPalette();
    }

    public Coordinate getCoordinate(int x, int y) {
        int width = Math.max(1, model.getWidth());
        int height = Math.max(1, model.getHeight());

        return model.getCoordinate(x % width, y % height);
    }

    public int getWidth() {
        return model.getWidth();
    }

    public int getHeight() {
        return model.getHeight();
    }

    public int getTilesX() {
        return model.getTilesX();
    }

    public int getTilesY() {
        return model.getTilesY();
    }

    public void addObserver(Observer o) {
        model.addObserver(o);
    }
    
}