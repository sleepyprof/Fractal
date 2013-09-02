package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.util.BigComplex;
import de.gdietz.fun.fractal.util.BigCoordinate;

public class BigComplexCoordMapper implements ParamCoordMapper<BigComplex, BigComplex, BigCoordinate> {

    public BigComplex get(BigCoordinate c, BigCoordinate p) {
        return new BigComplex(c);
    }

    public BigComplex getParam(BigCoordinate c, BigCoordinate p) {
        return new BigComplex(p);
    }

    public BigCoordinate coord(BigComplex c) {
        return c;
    }
    
}
