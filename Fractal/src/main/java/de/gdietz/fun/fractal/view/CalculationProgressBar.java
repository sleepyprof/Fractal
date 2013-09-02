package de.gdietz.fun.fractal.view;

import javax.swing.*;

public class CalculationProgressBar extends JProgressBar implements CalculationListener {

    public void setCalculating(boolean calculating) {
        if (calculating) {
            setStringPainted(false);
            setIndeterminate(true);
        } else {
            setStringPainted(false);
            setValue(0);
            setIndeterminate(false);
        }
    }

    public void setProgress(int progress, int total) {
        setIndeterminate(false);
        if (getMaximum() != total) {
            setValue(0);
            setMaximum(total);
        }
        setValue(progress);
    }

}
