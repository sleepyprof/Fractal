package de.gdietz.fun.fractal.view;

import java.util.List;

public interface Combiner<D> {

    D combine(List<D> data);

}
