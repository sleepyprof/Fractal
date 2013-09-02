package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.meta.FractalMetadata;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalTraceModel extends FractalIterateModelAdapter<Integer> {

	private final FractalSimpleBuffer buffer;

	private FractalTraceModel(FractalSimpleBuffer buffer, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		super(buffer, iteratorFactory, maxiter);
		this.buffer = buffer;
	}

	public FractalTraceModel(int width, int height, Coordinate from, Coordinate to, FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		this(new FractalSimpleBuffer(width, height, from, to), iteratorFactory, maxiter);
	}

	private void setPixel(int x, int y) {
		if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
			buffer.increaseData(x, y);
	}

	private void setPixelBig(int x, int y) {
		for (int dx = -2; dx <= 2; dx++) {
			for (int dy = -2; dy <= 2; dy++) {
				if (Math.abs(dx) + Math.abs(dy) <= 2)
					setPixel(x + dx, y + dy);
			}
		}
	}

	protected void startCalculationSilent() {
		Coordinate c = getParameter();
		Coordinate p = Coordinate.ORIGIN;
		FractalIterator<Coordinate> iterator = getIteratorFactory().get(c, p);
		int iter = 0;
		while(iterator.isValid() && iter < getMaxiter() * 100) {
			c = iterator.getCoordinate();
			int px = getX(c);
			int py = getY(c);
			if (iter > getMaxiter() * 90)
				setPixelBig(px, py);
			else
				setPixel(px, py);
			iterator.iterate();
			iter++;
		}
	}

	protected void stopCalculationSilent() {
	}

	public boolean isCalculating() {
		return false;
	}

    public FractalMetadata getMetadata() {
        FractalMetadata metadata = new FractalMetadata();
        metadata.setType("Iteration Trace");
        metadata.setIteratorFactory(getIteratorFactory());
        metadata.setMaxiter(getMaxiter());
        metadata.setSize(getWidth(), getHeight());
        metadata.setCorners(getCornerFrom(), getCornerTo());
        metadata.setParameter(getParameter());
        return metadata;
    }

}
