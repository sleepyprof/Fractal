package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.meta.FractalPreciseMetadata;
import de.gdietz.fun.fractal.util.BigCoordinate;

public class FractalPreciseSimpleIterateModel extends FractalPreciseSimpleIterateModelAdapter<Integer> {

    public FractalPreciseSimpleIterateModel(int width, int height, BigCoordinate from, BigCoordinate to, FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter, boolean julia) {
        super(new FractalPreciseSimpleBuffer(width, height, from, to), iteratorFactory, maxiter, julia);
    }

    public FractalPreciseSimpleIterateModel(int width, int height, BigCoordinate from, BigCoordinate to, FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter) {
        this(width, height, from, to, iteratorFactory, maxiter, false);
    }

    public Integer calculate(BigCoordinate c, BigCoordinate p) {
        FractalIterator<BigCoordinate> iterator = getIteratorFactory().get(c, p);
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

    public FractalPreciseMetadata getMetadata() {
        FractalPreciseMetadata metadata = new FractalPreciseMetadata();
        metadata.setType(isJulia() ? "Julia Set" : "Mandelbrot Set");
        metadata.setIterationType(false);
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setPreciseType(getIteratorFactory());
        metadata.setMaxiter(getMaxiter());
        metadata.setSize(getWidth(), getHeight());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        return metadata;
    }

}
