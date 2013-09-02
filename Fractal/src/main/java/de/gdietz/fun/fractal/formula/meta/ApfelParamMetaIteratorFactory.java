package de.gdietz.fun.fractal.formula.meta;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.util.Tuple;

public class ApfelParamMetaIteratorFactory<X, T extends Tuple<T>> implements FractalIteratorFactory<T>, ValidityTestable<X> {

    private final ParamCoordMapper<X, X, T> mapper;

    private final ApfelParamFunctionFactory<X> functionFactory;

    private final X z0;

	private ValidityTest<X> test;

    public ApfelParamMetaIteratorFactory(ParamCoordMapper<X, X, T> mapper, ApfelParamFunctionFactory<X> functionFactory, X z0, ValidityTest<X> test) {
        this.mapper = mapper;
        this.functionFactory = functionFactory;
        this.z0 = z0;
        this.test = test;
    }

	private class ApfelIterator implements FractalIterator<T> {

        private final ApfelFunction<X> function;

		private X z;

		private final X c;

		public ApfelIterator(X c, X p) {
            function = functionFactory.get(p);
			this.c = c;
			this.z = z0;
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

    public ApfelParamFunctionFactory<X> getFunctionFactory() {
        return functionFactory;
    }

    public FractalIterator<T> get(T c, T p) {
		return new ApfelIterator(mapper.get(c, p), mapper.getParam(c, p));
	}

    public String toString() {
        return functionFactory.toString();
    }

}
