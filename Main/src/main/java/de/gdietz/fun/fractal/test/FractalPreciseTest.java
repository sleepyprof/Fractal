package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.formula.BigComplexCoordMapper;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.ParamCoordMapper;
import de.gdietz.fun.fractal.formula.collections.BigComplexFunctionCollection;
import de.gdietz.fun.fractal.formula.collections.PreciseIteratorFactoryCollection;
import de.gdietz.fun.fractal.formula.collections.PreciseIteratorFactoryFunctionCollection;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorBigComplexParser;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorParser;
import de.gdietz.fun.fractal.gui.AdvancedFractalPreciseIteratorFactorySelector;
import de.gdietz.fun.fractal.gui.FractalPreciseGUI;
import de.gdietz.fun.fractal.mandel.MandelbrotPreciseIteratorFactory;
import de.gdietz.fun.fractal.util.*;
import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FractalPreciseTest extends FractalWindow {

    private final FractalPreciseGUI gui;

    public FractalPreciseTest(boolean julia, boolean endless, boolean resizeable) {
		super("Fractal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int maxScale = 40;

        FractalIteratorFactory<BigCoordinate> defaultIteratorFactory = new MandelbrotPreciseIteratorFactory(maxScale);
		int maxiter = 100;
        double bound = 100.0;

        int width = 401;
        int height = 401;

        BigDecimal delta = new BigDecimal(2);

		BigCoordinate from = new BigCoordinate(delta.negate(), delta.negate());
		BigCoordinate to = new BigCoordinate(delta, delta);

        gui = new FractalPreciseGUI(defaultIteratorFactory, maxiter, width, height, from, to, julia, endless, resizeable);

        AdvancedFractalPreciseIteratorFactorySelector<BigCoordinate> iteratorSelector = gui.getIteratorSelector();

        List<PreciseIteratorFactoryCollection> collections = new ArrayList<>();
        collections.add(new PreciseIteratorFactoryFunctionCollection<>(new BigComplexFunctionCollection(), bound, maxScale));

        for(PreciseIteratorFactoryCollection collection : collections) {
            String collectionName = collection.getName();
            List<FractalIteratorFactory<BigCoordinate>> iteratorFactories = collection.getCollection();
            for(FractalIteratorFactory<BigCoordinate> iteratorFactory : iteratorFactories) {
                String str = iteratorFactory.toString();
                if (!collectionName.equals("complex"))
                    str = collectionName + ": " + str;
                iteratorSelector.addIteratorFactory(iteratorFactory, maxiter, str);
            }
        }

        CalculatorParser<BigComplex> parser = new CalculatorBigComplexParser();
        ParamCoordMapper<BigComplex, BigComplex, BigCoordinate> mapper = new BigComplexCoordMapper();
        iteratorSelector.addCustomPrecise(parser, mapper, bound, maxiter, maxScale, "custom");
        iteratorSelector.addCustomPrecise(parser, mapper, BigComplex.ZERO, bound, maxiter, maxScale, "p-custom");

        add(gui);

		JMenuBar menu = new FractalMenuBar(this);
		setJMenuBar(menu);

		pack();
		setVisible(true);
    }

    public FractalPreciseTest(boolean julia) {
        this(julia, false, false);
    }

    public FractalPreciseTest() {
        this(false);
    }

    public SaveableView getSaveableView() {
        return gui.getView();
    }


    public static void main(String[] args) {
		new FractalPreciseTest();
	}

}
