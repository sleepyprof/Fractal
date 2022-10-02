package de.gdietz.fun.fractal.braid;

public class ConstantSelectorSequence implements SelectorSequence {

    private final int[] selectors;

    public ConstantSelectorSequence(int... selectors) {
        this.selectors = selectors;
    }

    public int get(int index) {
        return selectors[index % selectors.length];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int selector : selectors) {
            sb.append(selector);
            sb.append(" ");
        }
        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
