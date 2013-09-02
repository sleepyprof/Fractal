package de.gdietz.fun.fractal.util;

public class EllipsePath implements Path<Coordinate> {

    private Coordinate from;
    private Coordinate to;

    private Coordinate middle;
    private Coordinate diff;

    public EllipsePath(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
        middle = from.add(to).multiply(0.5);
        diff = to.subtract(middle);
    }

    public void setLimits(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
        middle = from.add(to).multiply(0.5);
        diff = to.subtract(middle);
    }

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }

    public Coordinate get(double t) {
        return middle.add(new Coordinate(diff.getX() * Math.cos(Math.PI * t), diff.getY() * Math.sin(Math.PI * t)));
    }

}
