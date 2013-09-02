package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate3D;

public class Fractal3DCanvasImpl implements Fractal3DCanvas {

    protected int size;

    protected Coordinate3D from;
    protected Coordinate3D to;

    protected Coordinate3D parameter;

    private double ratioX;
    private double ratioY;
    private double ratioZ;

    public Fractal3DCanvasImpl(int size, Coordinate3D from, Coordinate3D to) {
        this.size = size;
        this.from = from;
        this.to = to;
        calculateRatios();
        parameter = Coordinate3D.ORIGIN;
    }

    private void calculateRatios() {
        ratioX = (size <= 1) ? 1.0 : (to.getX() - from.getX()) / (size - 1.0);
        ratioY = (size <= 1) ? 1.0 : (to.getY() - from.getY()) / (size - 1.0);
        ratioZ = (size <= 1) ? 1.0 : (to.getZ() - from.getZ()) / (size - 1.0);
    }

    public void setSize(int size) {
        this.size = size;
        calculateRatios();
    }

    public int getSize() {
        return size;
    }

    public void setCorners(Coordinate3D from, Coordinate3D to) {
        this.from = from;
        this.to = to;
        calculateRatios();
    }

    public Coordinate3D getCornerFrom() {
        return from;
    }

    public Coordinate3D getCornerTo() {
        return to;
    }

    public void setParameter(Coordinate3D parameter) {
        this.parameter = parameter;
    }

    public Coordinate3D getParameter() {
        return parameter;
    }

    public Coordinate3D getCoordinate(int x, int y, int z) {
        return new Coordinate3D(ratioX * x  + from.getX(), ratioY * y + from.getY(), ratioZ * z + from.getZ());
    }

    public int getX(Coordinate3D c) {
        return (ratioX == 0.0) ? 0 : (int) Math.round((c.getX() - from.getX()) / ratioX);
    }

    public int getY(Coordinate3D c) {
        return (ratioY == 0.0) ? 0 : (int) Math.round((c.getY() - from.getY()) / ratioY);
    }

    public int getZ(Coordinate3D c) {
        return (ratioZ == 0.0) ? 0 : (int) Math.round((c.getZ() - from.getZ()) / ratioZ);
    }

    public void correctAspectRatio(boolean expand) {
        double deltaX = Math.abs(ratioX);
        double deltaY = Math.abs(ratioY);
        double deltaZ = Math.abs(ratioZ);
        double delta = expand ? Math.max(Math.max(deltaX, deltaY), deltaZ) : Math.min(Math.min(deltaX, deltaY), deltaZ);
        double middleX = (to.getX() + from.getX()) / 2.0;
        double middleY = (to.getY() + from.getY()) / 2.0;
        double middleZ = (to.getY() + from.getY()) / 2.0;
        double diff = delta * (size - 1.0);
        from = new Coordinate3D(middleX - 0.5 * diff, middleY - 0.5 * diff, middleZ - 0.5 * diff);
        to = new Coordinate3D(middleX + 0.5 * diff, middleY + 0.5 * diff, middleZ + 0.5 * diff);
        calculateRatios();
    }

}
