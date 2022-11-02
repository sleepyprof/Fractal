package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.FractalIteratorManager;
import de.gdietz.fun.fractal.formula.BoundaryTest;
import de.gdietz.fun.fractal.formula.ParamCoordMapper;
import de.gdietz.fun.fractal.formula.ValidityTest;
import de.gdietz.fun.fractal.formula.compiler.*;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorParser;
import de.gdietz.fun.fractal.formula.meta.ApfelFunctionFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelParamFunctionFactory;
import de.gdietz.fun.fractal.util.BigNormed;
import de.gdietz.fun.fractal.util.Tuple;

import java.util.List;

public class AdvancedFractalPreciseIteratorFactorySelector<T extends Tuple<T>> extends AdvancedFractalIteratorFactorySelector<T> {

    public AdvancedFractalPreciseIteratorFactorySelector(FractalIteratorManager<T> listener, boolean askMaxiter) {
        super(listener, askMaxiter);
    }

    public AdvancedFractalPreciseIteratorFactorySelector(FractalIteratorManager<T> listener) {
        this(listener, false);
    }

    protected class InteractiveFractalPreciseIteratorData<X extends BigNormed<X>> extends InteractiveFractalIteratorData<X> {

        private final int maxScale;

        public InteractiveFractalPreciseIteratorData(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, X z0, ValidityTest<X> test, int maxiter, int maxScale, String description) {
            super(parser, mapper, z0, test, maxiter, description);
            this.maxScale = maxScale;
        }

        public InteractiveFractalPreciseIteratorData(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, ValidityTest<X> test, int maxiter, int maxScale, String description) {
            super(parser, mapper, test, maxiter, description);
            this.maxScale = maxScale;
        }

        protected ApfelFunctionFactory<X> getFunctionFactory(List<CalculatorAction<X>> algorithm) {
            return new CalculatorPreciseFunctionFactory<>(algorithm, maxScale);
        }

        protected ApfelParamFunctionFactory<X> getParamFunctionFactory(List<CalculatorAction<X>> algorithm) {
            return new CalculatorPreciseFunctionFactory<>(algorithm, maxScale);
        }

    }

    public <X extends BigNormed<X>> void addCustomPrecise(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, ValidityTest<X> test, int maxiter, int maxScale, String description) {
        FractalIteratorData<T> iteratorData = new InteractiveFractalPreciseIteratorData<>(parser, mapper, test, maxiter, maxScale, description);
        addIteratorData(iteratorData);
    }
    
    public <X extends BigNormed<X>> void addCustomPrecise(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, double bound, int maxiter, int maxScale, String description) {
        addCustomPrecise(parser, mapper, new BoundaryTest<>(bound), maxiter, maxScale, description);
    }

    public <X extends BigNormed<X>> void addCustomPrecise(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, X z0, ValidityTest<X> test, int maxiter, int maxScale, String description) {
        FractalIteratorData<T> iteratorData = new InteractiveFractalPreciseIteratorData<>(parser, mapper, z0, test, maxiter, maxScale, description);
        addIteratorData(iteratorData);
    }

    public <X extends BigNormed<X>> void addCustomPrecise(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, X z0, double bound, int maxiter, int maxScale, String description) {
        addCustomPrecise(parser, mapper, z0, new BoundaryTest<>(bound), maxiter, maxScale, description);
    }

}
