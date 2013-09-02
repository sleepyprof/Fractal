package de.gdietz.fun.fractal.mandel;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.util.Coordinate;

public class MandelbrotIteratorFactory implements FractalIteratorFactory<Coordinate> {

	private static class MandelbrotIterator implements FractalIterator<Coordinate> {

		private double zr, zi;
        private double zr2, zi2;

        private final double cr, ci;

		public MandelbrotIterator(Coordinate c, Coordinate p) {
			cr = c.getX();
			ci = c.getY();
			zr = p.getX();
			zi = p.getY();
            zr2 = zr * zr;
            zi2 = zi * zi;
		}

		public boolean isValid() {
			return zr2 + zi2 <= 4.0;
		}

		public boolean isSurvivor() {
			return false;
		}

		public void iterate() {
            double zri = zr * zi;
			double r = zr2 - zi2 + cr;
			double i = zri + zri + ci;
			zr = r;
			zi = i;
            zr2 = zr * zr;
            zi2 = zi * zi;
		}

		public Coordinate getCoordinate() {
			return new Coordinate(zr, zi);
		}

	}

	public FractalIterator<Coordinate> get(Coordinate c, Coordinate p) {
		return new MandelbrotIterator(c, p);
	}

    public String toString() {
        return "mandelbrot";
    }

}
