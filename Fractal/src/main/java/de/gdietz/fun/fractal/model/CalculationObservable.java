package de.gdietz.fun.fractal.model;

import de.gdietz.fun.fractal.view.CalculationListener;

import java.util.Observable;
import java.util.Vector;

public class CalculationObservable extends Observable implements CalculationListener {

    private Vector<CalculationListener> listeners;

    public CalculationObservable() {
    	listeners = new Vector<>();
    }

    public synchronized void addCalculationListener(CalculationListener listener) {
        if (!listeners.contains(listener))
            listeners.addElement(listener);

    }

    public synchronized void deleteCalculationListener(CalculationListener listener) {
        listeners.removeElement(listener);
    }

    public void setCalculating(boolean calculating) {
        CalculationListener[] listenersLocal;
    	synchronized (this) {
            listenersLocal = listeners.toArray(new CalculationListener[listeners.size()]);
        }
        for (CalculationListener listener : listenersLocal)
            listener.setCalculating(calculating);
    }

    public void setProgress(int progress, int total) {
        CalculationListener[] listenersLocal;
    	synchronized (this) {
            listenersLocal = listeners.toArray(new CalculationListener[listeners.size()]);
        }
        for (CalculationListener listener : listenersLocal)
            listener.setProgress(progress, total);
    }

    public synchronized void deleteListeners() {
    	listeners.removeAllElements();
    }

    public synchronized int countListeners() {
    	return listeners.size();
    }

}
