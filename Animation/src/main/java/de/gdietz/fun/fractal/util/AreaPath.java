package de.gdietz.fun.fractal.util;

public class AreaPath implements Path<Coordinate> {

    private Coordinate from;
    private Coordinate to;

    public AreaPath(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }

    public void setLimits(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
    }

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }

    public Coordinate get(double t) {
        if (t < 0.0) t = 0.0;
        if (t >= 1.0) t = 1.0;
        Coordinate split = getSplit(t);
        return new Coordinate(from.getX() * (1.0 - split.getX()) + to.getX() * split.getX(),
                from.getY() * (1.0 - split.getY()) + to.getY() * split.getY());
    }


    private static final int DIGITS = 150;

    private static boolean[] getBinary(double x, int digits) {
        if (x < 0.0 || x >= 1.0)
            throw new IllegalArgumentException("Number must be between 0 and 1");
        boolean[] result = new boolean[digits];
        double u = x;
        for (int i = 0; i < digits; i++) {
            u += u;
            if (u >= 1.0) {
                result[i] = true;
                u -= 1.0;
            }
        }
        return result;
    }

    public static boolean[] getBinary(double x) {
        return getBinary(x, DIGITS);
    }

    public static Coordinate getSplit(boolean[] bin) {
        if (bin.length % 2 != 0)
            throw new IllegalArgumentException("Must be a sequence of even length");

        Coordinate result = Coordinate.ORIGIN;
        for (int i = bin.length - 2; i >= 0; i -= 2) {
            int piece = 2 * (bin[i] ? 1 : 0) + (bin[i+1] ? 1 : 0);
            Coordinate half =result.multiply(0.5);
            switch(piece) {
                case 0:
                    result = new Coordinate(half.getY(), half.getX());
                    break;
                case 1:
                    result = new Coordinate(half.getX(), 0.5 + half.getY());
                    break;
                case 2:
                    result = new Coordinate(0.5 + half.getX(), 0.5 + half.getY());
                    break;
                case 3:
                    result = new Coordinate(1.0 - half.getY(), 0.5 - half.getX());
                    break;
                default:
                    throw new AssertionError("Internal Error.");
            }
        }
        return result;
    }

    public static Coordinate getSplit(double x) {
        if (x == 1.0)
            return new Coordinate(1.0, 0.0);
        return getSplit(getBinary(x));
    }

}
