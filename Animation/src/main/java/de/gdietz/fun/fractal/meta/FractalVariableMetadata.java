package de.gdietz.fun.fractal.meta;

import de.gdietz.fun.fractal.util.Coordinate;

public class FractalVariableMetadata extends FractalMetadata {

    public static final String FRACTAL_FRAMES  = "FractalAnimationFrames";
    public static final String FRACTAL_TILES_X = "FractalTilesX";
    public static final String FRACTAL_TILES_Y = "FractalTilesY";

    public static final String FRACTAL_CORNER_FROM_START_X = "FractalCornerFromStartX";
    public static final String FRACTAL_CORNER_FROM_START_Y = "FractalCornerFromStartY";
    public static final String FRACTAL_CORNER_TO_START_X   = "FractalCornerToStartX";
    public static final String FRACTAL_CORNER_TO_START_Y   = "FractalCornerToStartY";
    public static final String FRACTAL_CORNER_FROM_END_X   = "FractalCornerFromEndX";
    public static final String FRACTAL_CORNER_FROM_END_Y   = "FractalCornerFromEndY";
    public static final String FRACTAL_CORNER_TO_END_X     = "FractalCornerToEndX";
    public static final String FRACTAL_CORNER_TO_END_Y     = "FractalCornerToEndY";

    public static final String FRACTAL_PARAMETER_FROM_X = "FractalCornerFromX";
    public static final String FRACTAL_PARAMETER_FROM_Y = "FractalCornerFromY";
    public static final String FRACTAL_PARAMETER_TO_X   = "FractalCornerToX";
    public static final String FRACTAL_PARAMETER_TO_Y   = "FractalCornerToY";


    public void setSize(int width, int height, int frames) {
        setSize(width, height);
        put(FRACTAL_FRAMES, String.valueOf(frames));
    }

    public void setSize(int width, int height, int tilesX, int tilesY) {
        setSize(width, height);
        put(FRACTAL_TILES_X, String.valueOf(tilesX));
        put(FRACTAL_TILES_Y, String.valueOf(tilesY));
    }

    public void setCorners(Coordinate fromStart, Coordinate toStart, Coordinate fromEnd, Coordinate toEnd) {
        put(FRACTAL_CORNER_FROM_START_X, String.valueOf(fromStart.getX()));
        put(FRACTAL_CORNER_FROM_START_Y, String.valueOf(fromStart.getY()));
        put(FRACTAL_CORNER_TO_START_X, String.valueOf(toStart.getX()));
        put(FRACTAL_CORNER_TO_START_Y, String.valueOf(toStart.getY()));
        put(FRACTAL_CORNER_FROM_END_X, String.valueOf(fromEnd.getX()));
        put(FRACTAL_CORNER_FROM_END_Y, String.valueOf(fromEnd.getY()));
        put(FRACTAL_CORNER_TO_END_X, String.valueOf(toEnd.getX()));
        put(FRACTAL_CORNER_TO_END_Y, String.valueOf(toEnd.getY()));
    }

    public void setParameters(Coordinate paramFrom, Coordinate paramTo) {
        put(FRACTAL_PARAMETER_FROM_X, String.valueOf(paramFrom.getX()));
        put(FRACTAL_PARAMETER_FROM_Y, String.valueOf(paramFrom.getY()));
        put(FRACTAL_PARAMETER_TO_X, String.valueOf(paramTo.getX()));
        put(FRACTAL_PARAMETER_TO_Y, String.valueOf(paramTo.getY()));
    }

}
