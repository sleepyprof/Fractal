package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.FractalFuzzyController;
import de.gdietz.fun.fractal.fuzzy.Fuzzy;
import de.gdietz.fun.fractal.fuzzy.SplitFuzzy;
import de.gdietz.fun.fractal.model.FractalPeriodModel;

public class FractalFuzzyPeriodController extends FractalFuzzyController {

    private final FractalPeriodModel model;

    public FractalFuzzyPeriodController(FractalPeriodModel model) {
        super(model);
        this.model = model;
    }

    protected Fuzzy getFuzzy() {
        Fuzzy fuzzy = super.getFuzzy();
        if (fuzzy != null)
            return new SplitFuzzy(fuzzy, model);
        return model;
    }
    
}
