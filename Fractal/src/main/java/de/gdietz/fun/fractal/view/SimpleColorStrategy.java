package de.gdietz.fun.fractal.view;

import de.gdietz.fun.fractal.palette.Palette;

import java.awt.*;

public class SimpleColorStrategy implements ColorStrategy<Integer> {

    private Palette palette;

    private final boolean clearZero;

	public SimpleColorStrategy(Palette palette, boolean clearZero) {
		this.palette = palette;
		this.clearZero = clearZero;
	}

    public Color get(Integer data) {
        return (clearZero && data == 0) ? Color.BLACK : palette.get(data);
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    public Palette getPalette() {
        return palette;
    }
    
}
