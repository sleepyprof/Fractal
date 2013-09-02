package de.gdietz.fun.fractal.newton;

import de.gdietz.fun.fractal.formula.ValidityTest;

public class ClosedEyesTest<X> implements ValidityTest<X> {

    public boolean isValid(X x) {
        return true;
    }

    public boolean isSurvivor(X x) {
        return false;
    }

}
