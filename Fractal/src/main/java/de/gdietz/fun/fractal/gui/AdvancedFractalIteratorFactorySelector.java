package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.FractalIteratorManager;
import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.formula.compiler.*;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorParser;
import de.gdietz.fun.fractal.formula.meta.ApfelFunctionFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelMetaIteratorFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelParamFunctionFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelParamMetaIteratorFactory;
import de.gdietz.fun.fractal.util.Normed;
import de.gdietz.fun.fractal.util.Tuple;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AdvancedFractalIteratorFactorySelector<T extends Tuple<T>> extends FractalIteratorFactorySelector<T> {

    public AdvancedFractalIteratorFactorySelector(FractalIteratorManager<T> listener, boolean askMaxiter) {
        super(listener, askMaxiter);
    }

    public AdvancedFractalIteratorFactorySelector(FractalIteratorManager<T> listener) {
        this(listener, false);
    }

    protected class InteractiveFractalIteratorData<X extends Normed> extends FractalIteratorData<T> {

        private final CalculatorParser<X> parser;
        private final ParamCoordMapper<X, X, T> mapper;
        private final X z0;
        private final ValidityTest<X> test;

        private String funcStr;

        public InteractiveFractalIteratorData(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, X z0, ValidityTest<X> test, int maxiter, String description) {
            super(null, maxiter, description);
            this.parser = parser;
            this.mapper = mapper;
            this.z0 = z0;
            this.test = test;
        }

        public InteractiveFractalIteratorData(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, ValidityTest<X> test, int maxiter, String description) {
            this(parser, mapper, null, test, maxiter, description);
        }

        private List<CalculatorAction<X>> getAlgorithm() {
            String funcStr = JOptionPane.showInputDialog(AdvancedFractalIteratorFactorySelector.this, "Please enter function:", this.funcStr);
            if (funcStr == null)
                funcStr = "";
            List<CalculatorAction<X>> algorithm = null;
            boolean valid;
            String error = "";
            try {
                algorithm = parser.parse(funcStr, z0 != null);
                valid = Calculator.checkValidity(algorithm);
                if (!valid)
                    error = "Operator sequence incomplete";
            } catch (CalculatorException e) {
                valid = false;
                error = e.getMessage();
                Throwable cause = e.getCause();
                if (cause != null)
                    error = error + " (" + cause.getMessage() + ")";
            }

            if (!valid) {
                JOptionPane.showMessageDialog(AdvancedFractalIteratorFactorySelector.this, "Function not valid:\n" + error, "Error", JOptionPane.ERROR_MESSAGE);
                algorithm = new ArrayList<>();
                algorithm.add((Variable<X>) (z, c, p) -> c);
            }

            this.funcStr = funcStr;

            return algorithm;
        }

        protected ApfelFunctionFactory<X> getFunctionFactory(List<CalculatorAction<X>> algorithm) {
            return new CalculatorFunctionFactory<>(algorithm);
        }

        protected ApfelParamFunctionFactory<X> getParamFunctionFactory(List<CalculatorAction<X>> algorithm) {
            return new CalculatorFunctionFactory<>(algorithm);
        }

        public FractalIteratorFactory<T> getIteratorFactory() {
            List<CalculatorAction<X>> algorithm = getAlgorithm();
            if (z0 == null)
                return new ApfelMetaIteratorFactory<>(mapper, getFunctionFactory(algorithm), test);
            return new ApfelParamMetaIteratorFactory<>(mapper, getParamFunctionFactory(algorithm), z0, test);
        }

        public String toString() {
            return funcStr == null ? super.toString() : super.toString() + ": " + funcStr;
        }

    }

    public <X extends Normed> void addCustom(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, ValidityTest<X> test, int maxiter, String description) {
        FractalIteratorData<T> iteratorData = new InteractiveFractalIteratorData<>(parser, mapper, test, maxiter, description);
        addIteratorData(iteratorData);
    }
    
    public <X extends Normed> void addCustom(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, double bound, int maxiter, String description) {
        addCustom(parser, mapper, new BoundaryTest<>(bound), maxiter, description);
    }

    public <X extends Normed> void addCustom(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, X z0, ValidityTest<X> test, int maxiter, String description) {
        FractalIteratorData<T> iteratorData = new InteractiveFractalIteratorData<>(parser, mapper, z0, test, maxiter, description);
        addIteratorData(iteratorData);
    }

    public <X extends Normed> void addCustom(CalculatorParser<X> parser, ParamCoordMapper<X, X, T> mapper, X z0, double bound, int maxiter, String description) {
        addCustom(parser, mapper, z0, new BoundaryTest<>(bound), maxiter, description);
    }

}
