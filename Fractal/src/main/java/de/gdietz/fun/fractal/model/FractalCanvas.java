package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.util.Coordinate;

public interface FractalCanvas extends Size2DCanvas {

	public void setCorners(Coordinate from, Coordinate to);
	public Coordinate getCornerFrom();
	public Coordinate getCornerTo();

	public void setParameter(Coordinate parameter);
	public Coordinate getParameter();

	public Coordinate getCoordinate(int x, int y);
	public int getX(Coordinate c);
	public int getY(Coordinate c);

	public void correctAspectRatio(boolean expand);

}
