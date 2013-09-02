package de.gdietz.fun.fractal.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface BigNormed<X extends BigNormed<X>> extends Normed {

    public BigDecimal normSqrPrecise();

    public int scale();

    public X setScale(int newScale, RoundingMode roundingMode);
    public X setScale(int newScale);

}
