package de.gdietz.fun.fractal.formula.meta;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.util.Tuple;

public class ApfelMetaIteratorFactory<X, T extends Tuple<T>> implements FractalIteratorFactory<T>, ValidityTestable<X> {

    private final ParamCoordMapper<X, X, T> mapper;

    private final ApfelFunctionFactory<X> functionFactory;

	private ValidityTest<X> test;

    public ApfelMetaIteratorFactory(ParamCoordMapper<X, X, T> mapper, ApfelFunctionFactory<X> functionFactory, ValidityTest<X> test) {
        this.mapper = mapper;
        this.functionFactory = functionFactory;
        this.test = test;
    }

	private class ApfelIterator implements FractalIterator<T> {

        private final ApfelFunction<X> function;

		private X z;

		private final X c;

		public ApfelIterator(X c, X p) {
            function = functionFactory.get();
			this.c = c;
			this.z = p;
		}

		public boolean isValid() {
			return test.isValid(z);
		}

		public boolean isSurvivor() {
			return test.isSurvivor(z);
		}

		public void iterate() {
			z = function.func(z, c);
		}

		public T getCoordinate() {
			return mapper.coord(z);
		}

	}

    public void setTest(ValidityTest<X> test) {
        this.test = test;
    }

    public ValidityTest<X> getTest() {
        return test;
    }

    public ApfelFunctionFactory<X> getFunctionFactory() {
        return functionFactory;
    }

    public FractalIterator<T> get(T c, T p) {
		return new ApfelIterator(mapper.get(c, p), mapper.getParam(c, p));
	}

    public String toString() {
        return functionFactory.toString();
    }

}
