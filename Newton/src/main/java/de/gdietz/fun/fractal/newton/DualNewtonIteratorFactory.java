package de.gdietz.fun.fractal.newton;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.util.NormedNumber;
import de.gdietz.fun.fractal.util.Tuple;

public class DualNewtonIteratorFactory<X extends NormedNumber<X>, T extends Tuple<T>> implements FractalIteratorFactory<T> {

    private final DualCoordMapper<X, X, T> mapper;

    private final ApfelFunction<X> function;

	private final ValidityTest<X> test;

    public DualNewtonIteratorFactory(DualCoordMapper<X, X, T> mapper, ApfelFunction<X> function, ValidityTest<X> test) {
        this.mapper = mapper;
        this.function = function;
        this.test = test;
    }

	private class DualNewtonIterator implements FractalIterator<T> {

		private X z;
        private X w;

		private final X c;

		public DualNewtonIterator(X z, X w, X c) {
            this.z = z;
            this.w = w;
			this.c = c;
		}

		public boolean isValid() {
			return test.isValid(z) && test.isValid(w);
		}

		public boolean isSurvivor() {
			return test.isSurvivor(z) && test.isSurvivor(w);
		}

		public void iterate() {
            X d = w.subtract(z).multiply(0.5);
            if (d.normSqr() < 1.0E-30)
                return;
            X s = z.add(w).multiply(0.5);
            X fz = function.func(z, c);
            X fw = function.func(w, c);
            X fs = function.func(s, c);
            X derz = fs.subtract(fz).divide(d);
            X derw = fw.subtract(fs).divide(d);
            X deltaz = fz.divide(derz);
            X deltaw = fw.divide(derw);
            z = z.subtract(deltaz);
            w = w.subtract(deltaw);
        }

		public T getCoordinate() {
			return mapper.coord(z, w);
		}

	}

	public FractalIterator<T> get(T c, T p) {
		return new DualNewtonIterator(mapper.get(c, p), mapper.getSecond(c, p), mapper.getParam(c, p));
	}

    public String toString() {
        return "dualnewton(" + function + ")";
    }

}