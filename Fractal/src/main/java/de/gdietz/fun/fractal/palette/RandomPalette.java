package de.gdietz.fun.fractal.palette;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.awt.*;

public class RandomPalette implements Palette {

    protected static class ColorTriple {
        
        private final int x;
        private final int y;
        private final int z;

        private ColorTriple(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

    }

    protected interface ColorTripleGenerator {
        
        public ColorTriple getNext();
        
    }
    
    protected static class ColorTripleRandomGenerator implements ColorTripleGenerator {
        
        private final Random random;
        
        public ColorTripleRandomGenerator(long seed) {
            random = new Random(seed);
        }

        public ColorTriple getNext() {
            return new ColorTriple(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }
        
    }

    protected interface ColorTripleConverter {
        
        public Color convert(ColorTriple triple);
        
    }

    protected static class ColorTripleRGBConverter implements ColorTripleConverter {

        public Color convert(ColorTriple triple) {
            return new Color(triple.getX(), triple.getY(), triple.getZ());
        }
        
    }

    protected static class ColorTripleHSBConverter implements ColorTripleConverter {

        public Color convert(ColorTriple triple) {
            return Color.getHSBColor(triple.getX() / 255f, triple.getY() / 255f, triple.getZ() / 255f);
        }

    }

    private static class ColorInfo {
        
        private ColorTriple triple;
        private Color color;

        private ColorInfo(ColorTriple triple, ColorTripleConverter converter) {
            this.triple = triple;
            color = converter.convert(triple);
        }

        public Color getColor() {
            return color;
        }
        
        public int getX() {
            return triple.getX();
        }
        
        public int getY() {
            return triple.getY();
        }
        
        public int getZ() {
            return triple.getZ();
        }
        
        public static ColorInfo getNext(ColorTripleGenerator generator, ColorTripleConverter converter) {
            ColorTriple triple = generator.getNext();
            return new ColorInfo(triple, converter);
        }
        
    }

    private final ColorTripleGenerator generator;
    private final ColorTripleConverter converter;

    private final List<ColorInfo> colors;

    private final long seed;

	public RandomPalette(long seed) {
        this.seed = seed;
		colors = Collections.synchronizedList(new ArrayList<>());
        generator = new ColorTripleRandomGenerator(seed);
        converter = seed < 0 ? new ColorTripleHSBConverter() : new ColorTripleRGBConverter();
	}

    public RandomPalette() {
        this(System.currentTimeMillis());
    }

	private synchronized void createNewColors(int index) {
		if (colors.size() == 0)
			colors.add(ColorInfo.getNext(generator, converter));
		while (colors.size() <= index) {
			ColorInfo nextColor = ColorInfo.getNext(generator, converter);
			ColorInfo lastColor = colors.get(colors.size()-1);
			int diff = Math.max(Math.max(
					Math.abs(nextColor.getX()-lastColor.getX()),
					Math.abs(nextColor.getY()-lastColor.getY())),
					Math.abs(nextColor.getZ()-lastColor.getZ()));
			for(int count=1; count<=diff; count++) {
                ColorInfo color = new ColorInfo(new ColorTriple(
                        lastColor.getX() + count * (nextColor.getX() - lastColor.getX()) / diff,
                        lastColor.getY() + count * (nextColor.getY() - lastColor.getY()) / diff,
                        lastColor.getZ() + count * (nextColor.getZ() - lastColor.getZ()) / diff), converter);
                colors.add(color);
			}
		}
	}

	public Color get(int index) {
		if (index < 0)
			return Color.BLACK;
		createNewColors(index);
		return colors.get(index).getColor();
	}

    public String toString() {
        return "RandomPalette(" + seed + ")";
    }
    
}
