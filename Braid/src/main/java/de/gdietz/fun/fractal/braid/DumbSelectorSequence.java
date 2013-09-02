package de.gdietz.fun.fractal.braid;

public class DumbSelectorSequence<X> implements SmartSelectorSequence<X> {

    private final SelectorSequence seq;

    public DumbSelectorSequence(SelectorSequence seq) {
        this.seq = seq;
    }

    public int get(int index) {
        return seq.get(index);
    }

    public int get(int index, X hint) {
        return seq.get(index);
    }

    public String toString() {
        return seq.toString();
    }

}
