package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.formula.collections.*;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorComplexParser;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorParser;
import de.gdietz.fun.fractal.formula.formulas.ApfelFunctionPow;
import de.gdietz.fun.fractal.gui.AdvancedFractalIteratorFactorySelector;
import de.gdietz.fun.fractal.gui.NewtonGUI;
import de.gdietz.fun.fractal.newton.ClosedEyesTest;
import de.gdietz.fun.fractal.newton.NewtonIteratorFactoryCollection;
import de.gdietz.fun.fractal.newton.RealNewtonIteratorFactoryCollection;
import de.gdietz.fun.fractal.util.*;
import de.gdietz.fun.fractal.braid.*;
import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class NewtonTest extends FractalWindow {

	private final NewtonGUI gui;

    public NewtonTest(boolean julia, boolean resizeable) {
		super("Fractal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FractalIteratorFactory<Coordinate> defaultIteratorFactory =
                new ApfelIteratorFactory<Complex, Coordinate>(new ComplexCoordMapper(),
                        new NewtonFunction<Complex>(new ApfelFunctionPow<Complex>(3)), new ClosedEyesTest<Complex>());
		int maxiter = 100;
        double bound = 100.0;

        int width = 501;
        int height = 501;

        double delta = 2.0;

		Coordinate from = new Coordinate(-delta, -delta);
		Coordinate to = new Coordinate(delta, delta);

        gui = new NewtonGUI(defaultIteratorFactory, maxiter, width, height, from, to, julia, resizeable);

		AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector = gui.getIteratorSelector();

        List<IteratorFactoryCollection> collections = new ArrayList<IteratorFactoryCollection>();
        collections.add(new NewtonIteratorFactoryCollection());
        collections.add(new RealNewtonIteratorFactoryCollection());
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
                if (!collectionName.equals("newton") && !collectionName.equals("complex"))
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

    public NewtonTest(boolean julia) {
        this(julia, false);
    }

    public NewtonTest() {
        this(true);
    }

    public SaveableView getSaveableView() {
        return gui.getView();
    }

    
    public static void main(String[] args) {
		new NewtonTest();
	}

}
