package de.gdietz.fun.fractal.newton;

import de.gdietz.fun.fractal.formula.FractalIterator;

public interface IterateInfoProvider<I, R extends FractalIterator> {

    public IterateInfo<I> get(R iterator);

}
