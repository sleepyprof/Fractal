package de.gdietz.fun.fractal.mandel;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.Scaled;
import de.gdietz.fun.fractal.util.BigCoordinate;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MandelbrotPreciseIteratorFactory implements FractalIteratorFactory<BigCoordinate>, Scaled {

    private final int maxScale;
    private final static BigDecimal FOUR = new BigDecimal(4);

    private class MandelbrotPreciseIterator implements FractalIterator<BigCoordinate> {

        private BigDecimal zr, zi;
        private BigDecimal zr2, zi2;

        private final BigDecimal cr, ci;

        public MandelbrotPreciseIterator(BigCoordinate c, BigCoordinate p) {
            cr = c.getX();
            ci = c.getY();
            zr = p.getX();
            zi = p.getY();
            zr2 = zr.multiply(zr);
            zi2 = zi.multiply(zi);
        }

        public boolean isValid() {
            return zr2.add(zi2).compareTo(FOUR) <= 0;
        }

        public boolean isSurvivor() {
            return false;
        }

        public void iterate() {
            BigDecimal zri = zr.multiply(zi);
            BigDecimal r = zr2.subtract(zi2).add(cr);
            BigDecimal i = zri.add(zri).add(ci);
            if (r.scale() > maxScale)
                r = r.setScale(maxScale, RoundingMode.HALF_UP);
            if (i.scale() > maxScale)
                i = i.setScale(maxScale, RoundingMode.HALF_UP);
            zr = r;
            zi = i;
            zr2 = zr.multiply(zr);
            zi2 = zi.multiply(zi);


        }

        public BigCoordinate getCoordinate() {
            return new BigCoordinate(zr, zi);
        }

    }

    public MandelbrotPreciseIteratorFactory(int maxScale) {
        this.maxScale = maxScale;
    }

    public FractalIterator<BigCoordinate> get(BigCoordinate c, BigCoordinate p) {
        return new MandelbrotPreciseIterator(c, p);
    }

    public int getMaxScale() {
        return maxScale;
    }

    public String toString() {
        return "mandelbrot";
    }

}
