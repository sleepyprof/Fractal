package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.model.FractalIterateModel;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.imageio.PNGWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

public class FractalSurfacePicture<D> implements FractalPicture<Coordinate> {

	private final FractalIterateModel<D> model;

    private final HeightStrategy<D> heightStrategy;
    private final ColorStrategy<D> coloring;

	public FractalSurfacePicture(FractalIterateModel<D> model, HeightStrategy<D> heightStrategy, ColorStrategy<D> coloring) {
		this.model = model;
        this.heightStrategy = heightStrategy;
		this.coloring = coloring;
	}

	public BufferedImage getImage() {
        int maxHeight = heightStrategy.getMaxHeight();

		BufferedImage image = new BufferedImage(
				model.getWidth(), Math.max(model.getHeight() - maxHeight, 1), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

		for (int y = 0; y < model.getHeight(); y++) {
            int yb = y;
			for (int x = 0; x < model.getWidth(); x++) {
				D data = model.getData(x, y);
                int h = heightStrategy.get(data);
                int yt = yb - h;
                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(x, yb, x, yt);
                g.setColor(Color.DARK_GRAY);
                g.drawLine(x + 1, yb, x + 1, yt);
                g.setColor(new Color(coloring.get(data).getRGB()));
                g.drawLine(x, yt, x + 1, yt);
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
        return model.getCoordinate(x, y);
    }

    public int getWidth() {
        return model.getWidth();
    }

    public int getHeight() {
        return model.getHeight();
    }

    public void addObserver(Observer o) {
        model.addObserver(o);
    }
    
}