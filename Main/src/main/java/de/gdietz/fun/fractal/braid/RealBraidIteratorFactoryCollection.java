package de.gdietz.fun.fractal.braid;

import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.collections.IteratorFactoryCollection;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.util.Coordinate3D;

import java.util.ArrayList;
import java.util.List;

public class RealBraidIteratorFactoryCollection implements IteratorFactoryCollection {

    private final List<SelectorSequence> seqs;

    public RealBraidIteratorFactoryCollection() {
        seqs = new BraidSelectorCollection().getSelectorSequenceCollection();
    }

    public FractalIteratorFactory<Coordinate> getRealBraidIteratorFactory(SelectorSequence seq) {
        return new BraidIteratorFactory<>(new RealIntersecDataCoordMapper(), seq);
    }

    public FractalIteratorFactory<Coordinate3D> getRealBraid3DIteratorFactory(SelectorSequence seq) {
        return new BraidIteratorFactory<>(new RealIntersecDataCoord3DMapper(), seq);
    }

    public FractalIteratorFactory<Coordinate> getRealBraidIteratorFactory(SmartSelectorSequence<IntersecData<Double>> seq) {
        return new BraidIteratorFactory<>(new RealIntersecDataCoordMapper(), seq);
    }

    public FractalIteratorFactory<Coordinate3D> getRealBraid3DIteratorFactory(SmartSelectorSequence<IntersecData<Double>> seq) {
        return new BraidIteratorFactory<>(new RealIntersecDataCoord3DMapper(), seq);
    }

    public List<FractalIteratorFactory<Coordinate>> getCollection() {
        List<FractalIteratorFactory<Coordinate>> iteratorFactories = new ArrayList<>();

        for(SelectorSequence seq : seqs)
            iteratorFactories.add(getRealBraidIteratorFactory(seq));

        for (SmartRealIntersecDataSelectorSequence.Type type : SmartRealIntersecDataSelectorSequence.Type.values())
            iteratorFactories.add(getRealBraidIteratorFactory(new SmartRealIntersecDataSelectorSequence(type)));

        return iteratorFactories;
    }

    public List<FractalIteratorFactory<Coordinate3D>> getCollection3D() {
        List<FractalIteratorFactory<Coordinate3D>> iteratorFactories = new ArrayList<>();

        for(SelectorSequence seq : seqs)
            iteratorFactories.add(getRealBraid3DIteratorFactory(seq));

        for (SmartRealIntersecDataSelectorSequence.Type type : SmartRealIntersecDataSelectorSequence.Type.values())
            iteratorFactories.add(getRealBraid3DIteratorFactory(new SmartRealIntersecDataSelectorSequence(type)));

        return iteratorFactories;
    }

    public String getName() {
        return "braid";
    }

}
