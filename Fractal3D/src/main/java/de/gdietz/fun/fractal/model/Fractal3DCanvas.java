package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate3D;

public interface Fractal3DCanvas {

    public void setSize(int size);
    public int getSize();

    public void setCorners(Coordinate3D from, Coordinate3D to);
    public Coordinate3D getCornerFrom();
    public Coordinate3D getCornerTo();

    public void setParameter(Coordinate3D parameter);
    public Coordinate3D getParameter();

    public Coordinate3D getCoordinate(int x, int y, int z);
    public int getX(Coordinate3D c);
    public int getY(Coordinate3D c);
    public int getZ(Coordinate3D c);

    public void correctAspectRatio(boolean expand);

}
