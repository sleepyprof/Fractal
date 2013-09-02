package de.gdietz.fun.fractal.braid;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.collections.IteratorFactoryCollection;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;

import java.util.ArrayList;
import java.util.List;

public class ComplexBraidIteratorFactoryCollection implements IteratorFactoryCollection {

    private final List<SelectorSequence> seqs;

    public ComplexBraidIteratorFactoryCollection() {
        seqs = new BraidSelectorCollection().getSelectorSequenceCollection();
    }

    public FractalIteratorFactory<Coordinate> getComplexBraidIteratorFactory(SelectorSequence seq) {
        return new BraidIteratorFactory<Complex, Coordinate>(new ComplexIntersecDataCoordMapper(), seq);
    }

    public FractalIteratorFactory<Coordinate> getComplexBraidIteratorFactory(SmartSelectorSequence<IntersecData<Complex>> seq) {
        return new BraidIteratorFactory<Complex, Coordinate>(new ComplexIntersecDataCoordMapper(), seq);
    }

    public List<FractalIteratorFactory<Coordinate>> getCollection() {
        List<FractalIteratorFactory<Coordinate>> iteratorFactories = new ArrayList<FractalIteratorFactory<Coordinate>>();

        for(SelectorSequence seq : seqs)
            iteratorFactories.add(getComplexBraidIteratorFactory(seq));

        return iteratorFactories;
    }

    public List<FractalIteratorFactory<Coordinate3D>> getCollection3D() {
        return new ArrayList<FractalIteratorFactory<Coordinate3D>>();
    }

    public String getName() {
        return "complex braid";
    }

}
