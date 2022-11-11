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
import de.gdietz.fun.fractal.gui.FractalAnimGUI;
import de.gdietz.fun.fractal.mandel.MandelbrotIteratorFactory;
import de.gdietz.fun.fractal.util.*;
import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FractalAnimTest extends FractalWindow {

    private final FractalAnimGUI gui;

    public FractalAnimTest(FractalAnimGUI.Config config, boolean resizeable) {
        super("Fractal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FractalIteratorFactory<Coordinate> defaultIteratorFactory = new MandelbrotIteratorFactory();
        int maxiter = 100;
        double bound = 100.0;

        int width = 501;
        int height = 501;

        int frames = 20;

        double delta = 2.0;

        Coordinate from = new Coordinate(-delta, -delta);
        Coordinate to = new Coordinate(delta, delta);

        Path<Coordinate> paramPath = new LinePath<>(Coordinate.ORIGIN, new Coordinate(1.0, 0.0));
        
        gui = new FractalAnimGUI(defaultIteratorFactory, maxiter, width, height, frames, from, to, paramPath, config, resizeable);

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

    public FractalAnimTest(FractalAnimGUI.Config config) {
        this(config, false);
    }

    public FractalAnimTest() {
        this(FractalAnimGUI.Config.MANDEL_ANIM);
    }

    public SaveableView getSaveableView() {
        return gui.getView();
    }

    public SaveableView getSaveableViewOther() {
        return gui.getViewRight();
    }

    public static void main(String[] args) {
        new FractalAnimTest();
    }

}
