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
import de.gdietz.fun.fractal.gui.FractalZoomGUI;
import de.gdietz.fun.fractal.mandel.MandelbrotIteratorFactory;
import de.gdietz.fun.fractal.util.*;
import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FractalZoomTest extends FractalWindow {

    private final FractalZoomGUI gui;

    public FractalZoomTest(boolean julia, boolean resizeable) {
        super("Fractal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FractalIteratorFactory<Coordinate> defaultIteratorFactory = new MandelbrotIteratorFactory();
        int maxiter = 250;
        double bound = 100.0;

        int width = 501;
        int height = 501;

        int frames = 20;

        Coordinate fromStart = new Coordinate(-2.0, -2.0);
        Coordinate toStart = new Coordinate(2.0, 2.0);
        Coordinate fromEnd = new Coordinate(-0.2, 1.0);
        Coordinate toEnd = new Coordinate(-0.1, 1.1);

        double alpha = 5;

        Path<Coordinate> fromPath = new LogPath<>(fromStart, fromEnd, alpha);
        Path<Coordinate> toPath = new LogPath<>(toStart, toEnd, alpha);

        gui = new FractalZoomGUI(defaultIteratorFactory, maxiter, width, height, frames, fromPath, toPath, julia, resizeable);

        AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector = gui.getIteratorSelector();

        List<IteratorFactoryCollection> collections = new ArrayList<>();
        collections.add(new IteratorFactoryFunctionCollection<>(new ComplexFunctionCollection(), bound));
        collections.add(new IteratorFactoryFunctionCollection<>(new QuaternionFunctionCollection(), bound));
        collections.add(new IteratorFactoryFunctionCollection<>(new Vector3DFunctionCollection(), bound));
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

        iteratorSelector.addScalaIfPossible(maxiter, "Scala");

        add(gui);

        JMenuBar menu = new FractalMenuBar(this);
        setJMenuBar(menu);

        pack();
        setVisible(true);
    }

    public FractalZoomTest(boolean julia) {
        this(julia, false);
    }

    public FractalZoomTest() {
        this(false);
    }

    public SaveableView getSaveableView() {
        return gui.getView();
    }

    public static void main(String[] args) {
        new FractalZoomTest();
    }

}
