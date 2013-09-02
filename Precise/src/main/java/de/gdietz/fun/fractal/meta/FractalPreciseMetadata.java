package de.gdietz.fun.fractal.meta;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.Scaled;
import de.gdietz.fun.fractal.formula.meta.ApfelFunctionFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelMetaIteratorFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelParamFunctionFactory;
import de.gdietz.fun.fractal.formula.meta.ApfelParamMetaIteratorFactory;
import de.gdietz.fun.fractal.util.BigCoordinate;

public class FractalPreciseMetadata extends FractalMetadata {

    public static final String FRACTAL_PRECISE_TYPE = "FractalPreciseType";

    public void setCorners(BigCoordinate from, BigCoordinate to) {
        put(FRACTAL_CORNER_FROM_X, String.valueOf(from.getX()));
        put(FRACTAL_CORNER_FROM_Y, String.valueOf(from.getY()));
        put(FRACTAL_CORNER_TO_X, String.valueOf(to.getX()));
        put(FRACTAL_CORNER_TO_Y, String.valueOf(to.getY()));
    }

    public void setParameter(BigCoordinate parameter) {
        put(FRACTAL_PARAMETER_X, String.valueOf(parameter.getX()));
        put(FRACTAL_PARAMETER_Y, String.valueOf(parameter.getY()));
    }

    public void setPreciseType(FractalIteratorFactory<?> iteratorFactory) {
        Scaled scaled = null;
        if (iteratorFactory instanceof Scaled)
            scaled = (Scaled) iteratorFactory;
        if (iteratorFactory instanceof ApfelMetaIteratorFactory) {
            ApfelMetaIteratorFactory<?, ?> metaIteratorFactory = (ApfelMetaIteratorFactory) iteratorFactory;
            ApfelFunctionFactory<?> functionFactory = metaIteratorFactory.getFunctionFactory();
            if (functionFactory instanceof Scaled)
                scaled = (Scaled) functionFactory;
        }
        if (iteratorFactory instanceof ApfelParamMetaIteratorFactory) {
            ApfelParamMetaIteratorFactory<?, ?> metaIteratorFactory = (ApfelParamMetaIteratorFactory) iteratorFactory;
            ApfelParamFunctionFactory<?> functionFactory = metaIteratorFactory.getFunctionFactory();
            if (functionFactory instanceof Scaled)
                scaled = (Scaled) functionFactory;
        }
        put(FRACTAL_PRECISE_TYPE, scaled == null ? "precise" : "precise (maxScale=" + scaled.getMaxScale() + ")");
    }

}
