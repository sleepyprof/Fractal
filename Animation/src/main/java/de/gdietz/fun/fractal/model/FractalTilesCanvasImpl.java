package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;

public class FractalTilesCanvasImpl implements FractalTilesCanvas {

    protected int tileWidth;
    protected int tileHeight;
    protected int tilesX;
    protected int tilesY;

    protected Coordinate from;
    protected Coordinate to;

    protected Coordinate paramFrom;
    protected Coordinate paramTo;

    private double ratioX;
    private double ratioY;
    private double ratioParamX;
    private double ratioParamY;


    public FractalTilesCanvasImpl(int tileWidth, int tileHeight, int tilesX, int tilesY, Coordinate from, Coordinate to, Coordinate paramFrom, Coordinate paramTo) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tilesX = tilesX;
        this.tilesY = tilesY;
        this.from = from;
        this.to = to;
        this.paramFrom = paramFrom;
        this.paramTo = paramTo;
        calculateRatios();
    }

    private void calculateRatios() {
        ratioX = (tileWidth <= 1) ? 1.0 : (to.getX() - from.getX()) / (tileWidth - 1.0);
        ratioY = (tileHeight <= 1) ? 1.0 : (to.getY() - from.getY()) / (tileHeight - 1.0);
        ratioParamX = (tilesX <= 1) ? 1.0 : (paramTo.getX() - paramFrom.getX()) / (tilesX - 1.0);
        ratioParamY = (tilesY <= 1) ? 1.0 : (paramTo.getY() - paramFrom.getY()) / (tilesY - 1.0);
    }

    public void setSize(int tileWidth, int tileHeight, int tilesX, int tilesY) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tilesX = tilesX;
        this.tilesY = tilesY;
        calculateRatios();
    }

    public void setTiles(int tilesX, int tilesY) {
        int width = tileWidth * this.tilesX;
        int height = tileHeight * this.tilesY;
        this.tilesX = Math.max(1, tilesX);
        this.tilesY = Math.max(1, tilesY);
        tileWidth = Math.max(1, width / this.tilesX);
        tileHeight = Math.max(1, height / this.tilesY);
        calculateRatios();
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getTilesX() {
        return tilesX;
    }

    public int getTilesY() {
        return tilesY;
    }

    public void setSize(int width, int height) {
        tilesX = Math.max(1, tilesX);
        tilesY = Math.max(1, tilesY);
        tileWidth = Math.max(1, width / tilesX);
        tileHeight = Math.max(1, height / tilesY);
        calculateRatios();
    }

    public int getWidth() {
        return tileWidth * tilesX;
    }

    public int getHeight() {
        return tileHeight * tilesY;
    }

    public void setCorners(Coordinate from, Coordinate to) {
        this.from = from;
        this.to = to;
        calculateRatios();
    }

    public Coordinate getCornerFrom() {
        return from;
    }

    public Coordinate getCornerTo() {
        return to;
    }

    public void setParameters(Coordinate paramFrom, Coordinate paramTo) {
        this.paramFrom = paramFrom;
        this.paramTo = paramTo;
        calculateRatios();
    }

    public Coordinate getParamFrom() {
        return paramFrom;
    }

    public Coordinate getParamTo() {
        return paramTo;
    }

    public Coordinate getCoordinate(int x, int y) {
        return new Coordinate(ratioX * x  + from.getX(), -ratioY * y + to.getY());
    }

    public int getX(Coordinate c) {
        return (ratioX == 0.0) ? 0 : (int) Math.round((c.getX() - from.getX()) / ratioX);
    }

    public int getY(Coordinate c) {
        return (ratioY == 0.0) ? 0 : (int) Math.round((c.getY() - to.getY()) / -ratioY);
    }

    public Coordinate getParameter(int tileX, int tileY) {
        return new Coordinate(ratioParamX * tileX  + paramFrom.getX(), -ratioParamY * tileY + paramTo.getY());
    }

    public void correctAspectRatio(boolean expand) {
        double deltaX = Math.abs(ratioX);
        double deltaY = Math.abs(ratioY);
        double delta = expand ? Math.max(deltaX, deltaY) : Math.min(deltaX, deltaY);
        double middleX = (to.getX() + from.getX()) / 2.0;
        double middleY = (to.getY() + from.getY()) / 2.0;
        double diffX = delta * (tileWidth - 1.0);
        double diffY = delta * (tileHeight - 1.0);
        from = new Coordinate(middleX - 0.5 * diffX, middleY - 0.5 * diffY);
        to = new Coordinate(middleX + 0.5 * diffX, middleY + 0.5 * diffY);

        double deltaParamX = Math.abs(ratioParamX);
        double deltaParamY = Math.abs(ratioParamY);
        double deltaParam = expand ? Math.max(deltaParamX, deltaParamY) : Math.min(deltaParamX, deltaParamY);
        double middleParamX = (paramTo.getX() + paramFrom.getX()) / 2.0;
        double middleParamY = (paramTo.getY() + paramFrom.getY()) / 2.0;
        double diffParamX = deltaParam * (tilesX - 1.0);
        double diffParamY = deltaParam * (tilesY - 1.0);
        paramFrom = new Coordinate(middleParamX - 0.5 * diffParamX, middleParamY - 0.5 * diffParamY);
        paramTo = new Coordinate(middleParamX + 0.5 * diffParamX, middleParamY + 0.5 * diffParamY);

        calculateRatios();
    }

}
