package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.BigCoordinate;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;

import java.util.ArrayList;
import java.util.List;

public class PreciseIteratorFactoryFunctionCollection<X> implements PreciseIteratorFactoryCollection {

    private final PreciseFunctionCollection<X> collection;
    private final double bound;
    private final int maxScale;

    public PreciseIteratorFactoryFunctionCollection(PreciseFunctionCollection<X> collection, double bound, int maxScale) {
        this.collection = collection;
        this.bound = bound;
        this.maxScale = maxScale;
    }

    public List<FractalIteratorFactory<BigCoordinate>> getCollection() {
        List<ApfelFunction<X>> functions = collection.getCollection();

        List<FractalIteratorFactory<BigCoordinate>> iteratorFactories = new ArrayList<FractalIteratorFactory<BigCoordinate>>();

        for(ApfelFunction<X> function : functions)
            iteratorFactories.add(collection.getApfelIteratorFactory(function, bound, maxScale));

        return iteratorFactories;
    }

    public String getName() {
        return collection.getName();
    }

}
