package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.braid.*;
import de.gdietz.fun.fractal.formula.*;
import de.gdietz.fun.fractal.formula.collections.*;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorParser;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorQuaternionParser;
import de.gdietz.fun.fractal.formula.formulas.*;
import de.gdietz.fun.fractal.gui.AdvancedFractalIteratorFactorySelector;
import de.gdietz.fun.fractal.gui.Fractal3DGUI;
import de.gdietz.fun.fractal.util.*;
import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Fractal3DTest extends FractalWindow {

    private final Fractal3DGUI gui;

    public Fractal3DTest(boolean julia) {
		super("Fractal3D");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FractalIteratorFactory<Coordinate3D> defaultIteratorFactory =
                new ApfelIteratorFactory<>(new QuaternionCoord3DMapper(), new ApfelFunctionPow<>(2), new BoundaryTest<>(2.0));
		int maxiter = 100;
        double bound = 100.0;

        int size = 101;

        double delta = 2.0;

		Coordinate3D from = new Coordinate3D(-delta, -delta, -delta);
		Coordinate3D to = new Coordinate3D(delta, delta, delta);

        gui = new Fractal3DGUI(defaultIteratorFactory, maxiter, size, from, to, julia);

		AdvancedFractalIteratorFactorySelector<Coordinate3D> iteratorSelector = gui.getIteratorSelector();

        List<IteratorFactoryCollection> collections = new ArrayList<>();
        collections.add(new IteratorFactoryFunctionCollection<>(new QuaternionFunctionCollection(), bound));
        collections.add(new IteratorFactoryFunctionCollection<>(new Vector3DFunctionCollection(), bound));
        collections.add(new IteratorFactoryFunctionCollection<>(new ComplexFunctionCollection(), bound));
        collections.add(new RealBraidIteratorFactoryCollection());
        collections.add(new ComplexBraidIteratorFactoryCollection());

        for(IteratorFactoryCollection collection : collections) {
            String collectionName = collection.getName();
            List<FractalIteratorFactory<Coordinate3D>> iteratorFactories = collection.getCollection3D();
            for(FractalIteratorFactory<Coordinate3D> iteratorFactory : iteratorFactories) {
                String str = iteratorFactory.toString();
                if (!collectionName.equals("quaternion"))
                    str = collectionName + ": " + str;
                iteratorSelector.addIteratorFactory(iteratorFactory, maxiter, str);
            }
        }

        CalculatorParser<Quaternion> parser = new CalculatorQuaternionParser();
        ParamCoordMapper<Quaternion, Quaternion, Coordinate3D> mapper = new QuaternionCoord3DMapper();
        iteratorSelector.addCustom(parser, mapper, bound, maxiter, "custom");
        iteratorSelector.addCustom(parser, mapper, Quaternion.ZERO, bound, maxiter, "p-custom");

        add(gui);

        JMenuBar menu = new FractalMenuBar(this);
        setJMenuBar(menu);

		pack();
		setVisible(true);
    }

    public Fractal3DTest() {
        this(false);
    }

    public SaveableView getSaveableView() {
        return gui.getView();
    }
    
    public static void main(String[] args) {
		new Fractal3DTest();
	}

}
