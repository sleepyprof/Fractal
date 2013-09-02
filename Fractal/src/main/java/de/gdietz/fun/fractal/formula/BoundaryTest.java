package de.gdietz.fun.fractal.formula;

import de.gdietz.fun.fractal.fuzzy.Fuzzy;
import de.gdietz.fun.fractal.util.Normed;

public class BoundaryTest<X extends Normed> implements ValidityTest<X>, Fuzzy {

    private double bound;
    private double boundSqr;

    public BoundaryTest(double bound) {
        this.bound = bound;
        boundSqr = bound * bound;
    }

    public void setBound(double bound) {
        this.bound = bound;
        boundSqr = bound * bound;
    }

    public double getBound() {
        return bound;
    }

    public boolean isValid(X x) {
        return x.normSqr() <= boundSqr;
    }

    public boolean isSurvivor(X x) {
        return false;
    }

    public void setLambda(double lambda) {
        setBound(lambda);
    }

    public double getLambda() {
        return getBound();
    }

    public void setEpsilon(double epsilon) {
    }

    public double getEpsilon() {
        return 0.0;
    }
    
}
