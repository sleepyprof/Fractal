package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.newton.IterateInfo;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;

import java.awt.*;

public class CoordinateColorStrategy implements ColorStrategy<IterateInfo<Coordinate>> {

    private Palette palette;

    private final double radius;
    private final double delta;

    public CoordinateColorStrategy(Palette palette, double radius, double delta) {
        this.palette = palette;
        this.radius = radius;
        this.delta = delta;
    }

    public CoordinateColorStrategy(Palette palette, double radius) {
        this(palette, radius, Math.log(0.75) / Math.min(Math.PI - radius, 1.0));
    }

    public CoordinateColorStrategy(Palette palette) {
        this(palette, 0.0);
    }

    public Color get(IterateInfo<Coordinate> data) {
        if (!data.isValid())
            return Color.BLACK;
        if (data.getInfo().equals(Coordinate.ORIGIN))
            return Color.WHITE;

        double norm = data.getInfo().norm();
        double arg = data.getInfo().arg();

        double hue = arg / Math.PI / 2.0;
        double saturation = norm >= radius ? 1.0f : norm / radius;
        double brightness = norm <= radius ? 1.0f : Math.min(1.0, Math.exp(delta * (norm - radius)));

        brightness = 0.1 + 0.9 * brightness;

        return Color.getHSBColor((float)hue, (float)saturation, (float)brightness);
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    public Palette getPalette() {
        return palette;
    }

}
