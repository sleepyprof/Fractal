package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.formula.collections.*;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorComplexParser;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorParser;
import de.gdietz.fun.fractal.gui.AdvancedFractalIteratorFactorySelector;
import de.gdietz.fun.fractal.gui.FractalGUI;
import de.gdietz.fun.fractal.util.*;
import de.gdietz.fun.fractal.mandel.MandelbrotIteratorFactory;
import de.gdietz.fun.fractal.braid.*;
import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FractalTest extends FractalWindow {

    private final FractalGUI gui;

    public FractalTest(FractalGUI.Config config, boolean endless, boolean resizeable) {
		super("Fractal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FractalIteratorFactory<Coordinate> defaultIteratorFactory = new MandelbrotIteratorFactory();
		int maxiter = 1000;
        double bound = 100.0;

        int width = 501;
        int height = 501;

        double delta = 2.0;

		Coordinate from = new Coordinate(-delta, -delta);
		Coordinate to = new Coordinate(delta, delta);

        gui = new FractalGUI(defaultIteratorFactory, maxiter, width, height, from, to, config, endless, resizeable);

		AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector = gui.getIteratorSelector();

        List<IteratorFactoryCollection> collections = new ArrayList<IteratorFactoryCollection>();
        collections.add(new IteratorFactoryFunctionCollection<Complex>(new ComplexFunctionCollection(), bound));
        collections.add(new IteratorFactoryFunctionCollection<Quaternion>(new QuaternionFunctionCollection(), bound));
        collections.add(new IteratorFactoryFunctionCollection<Vector3D>(new Vector3DFunctionCollection(), bound));
        collections.add(new RealBraidIteratorFactoryCollection());
        collections.add(new ComplexBraidIteratorFactoryCollection());

        for(IteratorFactoryCollection collection : collections) {
            String collectionName = collection.getName();
            List<FractalIteratorFactory<Coordinate>> iteratorFactories = collection.getCollection();
            for(FractalIteratorFactory<Coordinate> iteratorFactory : iteratorFactories) {
                String str = iteratorFactory.toString();
                if (!collectionName.equals("complex"))
                    str = collectionName + ": " + str;
                iteratorSelector.addIteratorFactory(iteratorFactory, maxiter, str);
            }
        }

        CalculatorParser<Complex> parser = new CalculatorComplexParser();
        ParamCoordMapper<Complex, Complex, Coordinate> mapper = new ComplexCoordMapper();
        iteratorSelector.addCustom(parser, mapper, bound, maxiter, "custom");
        iteratorSelector.addCustom(parser, mapper, Complex.ZERO, bound, maxiter, "p-custom");
        
        add(gui);

		JMenuBar menu = new FractalMenuBar(this);
		setJMenuBar(menu);

		pack();
		setVisible(true);
    }

    public FractalTest(FractalGUI.Config config) {
        this(config, true, false);
    }

    public FractalTest() {
        this(FractalGUI.Config.MANDEL);
    }

    public SaveableView getSaveableView() {
        return gui.getView();
    }

    public SaveableView getSaveableViewOther() {
        return gui.getView(false);
    }


    public static void main(String[] args) {
		new FractalTest();
	}

}
