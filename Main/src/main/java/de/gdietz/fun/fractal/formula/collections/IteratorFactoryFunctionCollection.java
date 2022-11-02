package de.gdietz.fun.fractal.formula.collections;

import de.gdietz.fun.fractal.formula.ApfelFunction;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;

import java.util.ArrayList;
import java.util.List;

public class IteratorFactoryFunctionCollection<X> implements IteratorFactoryCollection {

    private final FunctionCollection<X> collection;
    private final double bound;

    public IteratorFactoryFunctionCollection(FunctionCollection<X> collection, double bound) {
        this.collection = collection;
        this.bound = bound;
    }

    public List<FractalIteratorFactory<Coordinate>> getCollection() {
        List<ApfelFunction<X>> functions = collection.getCollection();

        List<FractalIteratorFactory<Coordinate>> iteratorFactories = new ArrayList<>();

        for(ApfelFunction<X> function : functions)
            iteratorFactories.add(collection.getApfelIteratorFactory(function, bound));

        return iteratorFactories;
    }

    public List<FractalIteratorFactory<Coordinate3D>> getCollection3D() {
        List<ApfelFunction<X>> functions = collection.getCollection();

        List<FractalIteratorFactory<Coordinate3D>> iteratorFactories = new ArrayList<>();

        for(ApfelFunction<X> function : functions)
            iteratorFactories.add(collection.getApfel3DIteratorFactory(function, bound));

        return iteratorFactories;
    }

    public String getName() {
        return collection.getName();
    }

}
