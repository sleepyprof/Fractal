package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.meta.FractalVariableMetadata;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Path;

public class FractalZoomSimpleIterateModel extends FractalZoomSimpleIterateModelAdapter<Integer> {

	public FractalZoomSimpleIterateModel(int width, int height, int frames, Path<Coordinate> fromPath, Path<Coordinate> toPath, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
		super(new FractalZoomSimpleBuffer(width, height, frames, fromPath, toPath), iteratorFactory, maxiter, julia);
	}

	public FractalZoomSimpleIterateModel(int width, int height, int frames, Path<Coordinate> fromPath, Path<Coordinate> toPath, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		this(width, height, frames, fromPath, toPath, iteratorFactory, maxiter, false);
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
        metadata.setSize(getWidth(), getHeight(), getFrames());
        metadata.setCorners(getCornerFromStart(), getCornerToStart(), getCornerFromEnd(), getCornerToEnd());
        metadata.setParameter(getParameter());
        return metadata;
    }

}
