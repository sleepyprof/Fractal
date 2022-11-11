package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.FractalIteratorManager;
import de.gdietz.fun.fractal.util.Tuple;

import java.io.Serializable;

public interface AdvancedFractalIteratorFactorySelectorProvider extends Serializable {

    public <T extends Tuple<T>> AdvancedFractalIteratorFactorySelector<T> createIteratorFactorySelectorProvider(Class<? extends T> clazz, FractalIteratorManager<T> listener, boolean askMaxiter);

}
