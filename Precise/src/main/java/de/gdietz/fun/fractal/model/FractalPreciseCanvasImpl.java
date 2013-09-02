package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.BigCoordinate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class FractalPreciseCanvasImpl implements FractalPreciseCanvas {

    private final static int PRECISION = 8;

    private final static BigDecimal HALF = BigDecimal.ONE.divide(new BigDecimal(2));

    protected int width;
    protected int height;

    protected BigCoordinate from;
    protected BigCoordinate to;

    protected BigCoordinate parameter;

    private BigDecimal ratioX;
    private BigDecimal ratioY;

    public FractalPreciseCanvasImpl(int width, int height, BigCoordinate from, BigCoordinate to) {
        this.width = width;
        this.height = height;
        this.from = from;
        this.to = to;
        calculateRatios();
        parameter = BigCoordinate.ORIGIN;
    }

    private static BigDecimal[] adjustPrecision(BigDecimal from, BigDecimal to, int precision, RoundingMode roundingMode) {
        BigDecimal diff = from.subtract(to);
        diff = diff.round(new MathContext(precision, roundingMode)).stripTrailingZeros();
        int scale = diff.scale();
        return new BigDecimal[] {from.setScale(scale, roundingMode).stripTrailingZeros(), to.setScale(scale, roundingMode).stripTrailingZeros()};
    }

    private void adjustPrecision() {
        BigDecimal[] arrX = adjustPrecision(from.getX(), to.getX(), PRECISION, RoundingMode.HALF_UP);
        BigDecimal[] arrY = adjustPrecision(from.getY(), to.getY(), PRECISION, RoundingMode.HALF_UP);
        from = new BigCoordinate(arrX[0], arrY[0]);
        to = new BigCoordinate(arrX[1], arrY[1]);
    }

    private static BigDecimal ratio(BigDecimal diff, int size) {
        if (size <= 1)
            return BigDecimal.ONE;
        BigDecimal result = diff.divide(new BigDecimal(size - 1), diff.scale() + 2 * PRECISION, RoundingMode.HALF_UP);
        return result.round(new MathContext(PRECISION, RoundingMode.HALF_UP)).stripTrailingZeros();
    }

    private void calculateRatios() {
        ratioX = ratio(to.getX().subtract(from.getX()), width);
        ratioY = ratio(to.getY().subtract(from.getY()), height);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        calculateRatios();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setCorners(BigCoordinate from, BigCoordinate to) {
        this.from = from;
        this.to = to;
        calculateRatios();
    }

    public BigCoordinate getCornerFrom() {
        return from;
    }

    public BigCoordinate getCornerTo() {
        return to;
    }

    public void setParameter(BigCoordinate parameter) {
        this.parameter = parameter;
    }

    public BigCoordinate getParameter() {
        return parameter;
    }

    public BigCoordinate getCoordinate(int x, int y) {
        return new BigCoordinate(ratioX.multiply(new BigDecimal(x)).add(from.getX()), 
                to.getY().subtract(ratioY.multiply(new BigDecimal(y))));
    }

    public int getX(BigCoordinate c) {
        try {
            return c.getX().subtract(from.getX()).divide(ratioX, 0, BigDecimal.ROUND_HALF_UP).intValue();
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public int getY(BigCoordinate c) {
        try {
            return c.getY().subtract(to.getY()).divide(ratioY, 0, BigDecimal.ROUND_HALF_UP).intValue();
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public void correctAspectRatio(boolean expand) {
        BigDecimal deltaX = ratioX.abs();
        BigDecimal deltaY = ratioY.abs();
        BigDecimal delta = expand ? deltaX.max(deltaY) : deltaX.min(deltaY);
        BigDecimal middleX = to.getX().add(from.getX()).multiply(HALF);
        BigDecimal middleY = to.getY().add(from.getY()).multiply(HALF);
        BigDecimal diffX = delta.multiply(new BigDecimal(width - 1));
        BigDecimal diffY = delta.multiply(new BigDecimal(height - 1));
        from = new BigCoordinate(middleX.subtract(HALF.multiply(diffX)), middleY.subtract(HALF.multiply(diffY)));
        to = new BigCoordinate(middleX.add(HALF.multiply(diffX)), middleY.add(HALF.multiply(diffY)));
        adjustPrecision();
        calculateRatios();
    }

}
