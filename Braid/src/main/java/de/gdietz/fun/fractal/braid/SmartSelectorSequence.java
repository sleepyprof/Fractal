package de.gdietz.fun.fractal.braid;

public interface SmartSelectorSequence<X> {

    public int get(int index, X hint);

}
