package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.braid.ComplexBraidIteratorFactoryCollection;
import de.gdietz.fun.fractal.braid.RealBraidIteratorFactoryCollection;
import de.gdietz.fun.fractal.formula.ComplexCoordMapper;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.formula.ParamCoordMapper;
import de.gdietz.fun.fractal.formula.collections.*;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorComplexParser;
import de.gdietz.fun.fractal.formula.compiler.parser.CalculatorParser;
import de.gdietz.fun.fractal.gui.AdvancedFractalIteratorFactorySelector;
import de.gdietz.fun.fractal.gui.FractalAnimCombinedGUI;
import de.gdietz.fun.fractal.mandel.MandelbrotIteratorFactory;
import de.gdietz.fun.fractal.util.*;
import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FractalAnimCombinedTest extends FractalWindow {

    private final FractalAnimCombinedGUI gui;

    public FractalAnimCombinedTest(boolean julia, boolean resizeable) {
        super("Fractal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FractalIteratorFactory<Coordinate> defaultIteratorFactory = new MandelbrotIteratorFactory();
        int maxiter = 100;
        double bound = 100.0;

        int width = 401;
        int height = 401;

        int frames = 33;

        double delta = 2.0;

        Coordinate from = new Coordinate(-delta, -delta);
        Coordinate to = new Coordinate(delta, delta);

        Path<Coordinate> paramPath = new CachedPath<Coordinate>(new AreaPath(new Coordinate(-1.0, -1.0), new Coordinate(1.0, 1.0)));

        gui = new FractalAnimCombinedGUI(defaultIteratorFactory, maxiter, width, height, frames, from, to, paramPath, julia, resizeable);

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

    public FractalAnimCombinedTest(boolean julia) {
        this(julia, false);
    }

    public FractalAnimCombinedTest() {
        this(false);
    }

    public SaveableView getSaveableView() {
        return gui.getView();
    }

    public SaveableView getSaveableViewOther() {
        return gui.getViewRight();
    }

    public static void main(String[] args) {
        new FractalAnimCombinedTest();
    }

}
