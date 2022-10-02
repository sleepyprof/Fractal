package de.gdietz.fun.fractal.braid;

import de.gdietz.fun.fractal.formula.CoordMapper;
import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Tuple;

public class BraidIteratorFactory<X, T extends Tuple<T>> implements FractalIteratorFactory<T> {

	private final CoordMapper<IntersecData<X>, T> mapper;
    private final SmartSelectorSequence<IntersecData<X>> sigmaSelectors;
	private final double bound;

    public BraidIteratorFactory(CoordMapper<IntersecData<X>, T> mapper, SmartSelectorSequence<IntersecData<X>> sigmaSelectors, double bound) {
        this.mapper = mapper;
        this.sigmaSelectors = sigmaSelectors;
        this.bound = bound;
    }

    public BraidIteratorFactory(CoordMapper<IntersecData<X>, T> mapper, SelectorSequence sigmaSelectors, double bound) {
        this(mapper, new DumbSelectorSequence<>(sigmaSelectors), bound);
    }

    public BraidIteratorFactory(CoordMapper<IntersecData<X>, T> mapper, SmartSelectorSequence<IntersecData<X>> sigmaSelectors) {
        this(mapper, sigmaSelectors, 100.0);
    }

    public BraidIteratorFactory(CoordMapper<IntersecData<X>, T> mapper, SelectorSequence sigmaSelectors) {
        this(mapper, sigmaSelectors, 100.0);
    }

	private class BraidIterator implements FractalIterator<T> {

		private IntersecData<X> isd;
		private int iter;

		public BraidIterator(T c, T p) {
			isd = mapper.get(c, p);
			iter = 0;
		}

		public boolean isValid() {
			return isd.normSqr() <= bound * bound;
		}

		public boolean isSurvivor() {
			return isd.isDefinite();
		}

		public void iterate() {
			int sigmaSelector = sigmaSelectors.get(iter, isd);
			iter++;
			isd = isd.sigma(sigmaSelector);
		}

		public T getCoordinate() {
			return mapper.coord(isd);
		}

	}

	public FractalIterator<T> get(T c, T p) {
		return new BraidIterator(c, p);
	}

    public String toString() {
        return sigmaSelectors.toString();
    }

}
