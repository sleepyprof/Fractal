package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.FractalIterator;

public class FractalSimpleIterateModel extends FractalSimpleIterateModelAdapter<Integer> {

	public FractalSimpleIterateModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
		super(new FractalSimpleBuffer(width, height, from, to), iteratorFactory, maxiter, julia);
	}

	public FractalSimpleIterateModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		this(width, height, from, to, iteratorFactory, maxiter, false);
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

    public FractalMetadata getMetadata() {
        FractalMetadata metadata = new FractalMetadata();
        metadata.setType(isJulia() ? "Julia Set" : "Mandelbrot Set");
        metadata.setIterationType(false);
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setMaxiter(getMaxiter());
        metadata.setSize(getWidth(), getHeight());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        return metadata;
    }

}
