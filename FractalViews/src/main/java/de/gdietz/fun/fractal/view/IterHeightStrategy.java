package de.gdietz.fun.fractal.view;

public class IterHeightStrategy implements HeightStrategy<Integer> {

    private final int maxHeight;
    private int maxiter;
    private double factor;

    public IterHeightStrategy(int maxHeight, int maxiter) {
        this.maxHeight = maxHeight;
        this.maxiter = maxiter;
        calculateFactor();
    }

    private void calculateFactor() {
        factor = maxHeight / Math.log(maxiter + 1);
    }

    public void setMaxiter(int maxiter) {
        this.maxiter = maxiter;
        calculateFactor();
    }

    public int get(Integer data) {
        if (data < 0)
            return maxHeight;

        return (int)(factor * Math.log(data + 1));
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}
