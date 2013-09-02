package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.meta.FractalVariableMetadata;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalTilesSimpleIterateModel extends FractalTilesSimpleIterateModelAdapter<Integer> {

    public FractalTilesSimpleIterateModel(int tileWidth, int tileHeight, int tilesX, int tilesY, Coordinate from, Coordinate to, Coordinate paramFrom, Coordinate paramTo,
                                          FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
        super(new FractalTilesSimpleBuffer(tileWidth, tileHeight, tilesX, tilesY, from, to, paramFrom, paramTo), iteratorFactory, maxiter, julia);
    }

    public FractalTilesSimpleIterateModel(int tileWidth, int tileHeight, int tilesX, int tilesY, Coordinate from, Coordinate to, Coordinate paramFrom, Coordinate paramTo,
                                          FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
        this(tileWidth, tileHeight, tilesX, tilesY, from, to, paramFrom, paramTo, iteratorFactory, maxiter, false);
    }

    public Integer calculate(Coordinate c, Coordinate p) {
        FractalIterator<Coordinate> iterator = getIteratorFactory().get(c, p);
        if (iterator.isSurvivor())
            return -1;
        int iter = 0;
        while(iterator.isValid() && iter < getMaxiter()) {
            iterator.iterate();
            iter++;
        }
        if (iterator.isValid())
            return -1;
        return iter;
    }

    public FractalVariableMetadata getMetadata() {
        FractalVariableMetadata metadata = new FractalVariableMetadata();
        metadata.setType(isJulia() ? "Julia Set Animation" : "Mandelbrot Set Animation");
        metadata.setIterationType(false);
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setMaxiter(getMaxiter());
        metadata.setSize(getWidth(), getHeight(), getTilesX(), getTilesY());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameters(getParamFrom(), getParamTo());
        return metadata;
    }

}
