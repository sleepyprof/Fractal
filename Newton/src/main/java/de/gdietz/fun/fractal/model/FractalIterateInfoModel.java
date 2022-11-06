package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.newton.IterateInfo;
import de.gdietz.fun.fractal.newton.IterateInfoProvider;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalIterateInfoModel<I> extends FractalSimpleIterateModelAdapter<IterateInfo<I>> {

    private final IterateInfoProvider<I, Coordinate, FractalIterator<Coordinate>> infoProvider;

	public FractalIterateInfoModel(IterateInfoProvider<I, Coordinate, FractalIterator<Coordinate>> infoProvider,
                                   int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, boolean julia) {
		super(new FractalInfoBuffer<>(width, height, from, to), iteratorFactory, maxiter, julia);
        this.infoProvider = infoProvider;
	}

	public FractalIterateInfoModel(IterateInfoProvider<I, Coordinate, FractalIterator<Coordinate>> infoProvider,
                                   int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		this(infoProvider, width, height, from, to, iteratorFactory, maxiter, false);
	}

	public IterateInfo<I> calculate(Coordinate c, Coordinate p) {
		FractalIterator<Coordinate> iterator = getIteratorFactory().get(c, p);

        int maxiter = getMaxiter();

        int iter = 0;
		while(iterator.isValid() && iter < maxiter) {
			iterator.iterate();
			iter++;
		}

        return infoProvider.get(iterator);
	}

    public FractalMetadata getMetadata() {
        FractalMetadata metadata = new FractalMetadata();
        metadata.setType(isJulia() ? "Colored Iteration Analysis (Julia Set mode)" : "Colored Iteration Analysis (Mandelbrot Set)");
        metadata.setIterationType(false);
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setMaxiter(getMaxiter());
        metadata.setSize(getWidth(), getHeight());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        return metadata;
    }

}