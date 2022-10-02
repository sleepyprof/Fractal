package de.gdietz.fun.fractal.newton;

import de.gdietz.fun.fractal.formula.FractalIterator;
import de.gdietz.fun.fractal.util.Tuple;

public interface IterateInfoProvider<I, T extends Tuple<T>, R extends FractalIterator<T>> {

    public IterateInfo<I> get(R iterator);

}
