package de.gdietz.fun.fractal.controller;

import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.fuzzy.Fuzzy;
import de.gdietz.fun.fractal.model.FractalIterateModel;
import de.gdietz.fun.fractal.util.Coordinate;

public class FractalFuzzyController implements Fuzzy {

    private final FractalIterateModel<?> model;

    public FractalFuzzyController(FractalIterateModel<?> model) {
        this.model = model;
    }

    protected Fuzzy getFuzzy() {
        FractalIteratorFactory<Coordinate> iterFactory = model.getIteratorFactory();
        if (iterFactory instanceof ValidityTestable) {
            ValidityTestable<?> testable = (ValidityTestable<?>) iterFactory;
            ValidityTest<?> test = testable.getTest();
            if (test instanceof Fuzzy)
                return (Fuzzy) test;
        }
        return null;
    }

    public boolean isFuzzy() {
        return getFuzzy() != null;
    }

    public void setLambda(double lambda) {
        Fuzzy fuzzy = getFuzzy();
        if (fuzzy != null) {
            model.stopCalculation();
            fuzzy.setLambda(lambda);
            model.startCalculation();
        }
    }

    public void setEpsilon(double epsilon) {
        Fuzzy fuzzy = getFuzzy();
        if (fuzzy != null) {
            model.stopCalculation();
            fuzzy.setEpsilon(epsilon);
            model.startCalculation();
        }
    }

    public double getLambda() {
        Fuzzy fuzzy = getFuzzy();
        if (fuzzy != null)
            return fuzzy.getLambda();
        return 0.0;
    }

    public double getEpsilon() {
        Fuzzy fuzzy = getFuzzy();
        if (fuzzy != null)
            return fuzzy.getEpsilon();
        return 0.0;
    }

}
