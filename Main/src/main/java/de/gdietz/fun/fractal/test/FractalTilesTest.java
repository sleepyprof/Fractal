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
import de.gdietz.fun.fractal.gui.FractalTilesGUI;
import de.gdietz.fun.fractal.mandel.MandelbrotIteratorFactory;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class FractalTilesTest extends FractalWindow {

    private final FractalTilesGUI gui;

    public FractalTilesTest(boolean julia, boolean resizeable) {
        super("Fractal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FractalIteratorFactory<Coordinate> defaultIteratorFactory = new MandelbrotIteratorFactory();
        int maxiter = 1000;
        double bound = 100.0;

        int tileWidth = 25;
        int tileHeight = 25;

        int tilesX = 17;
        int tilesY = 17;

        double delta = 2.0;
        double deltaParam = 2.0;

        Coordinate from = new Coordinate(-delta, -delta);
        Coordinate to = new Coordinate(delta, delta);
        Coordinate paramFrom = new Coordinate(-deltaParam, -deltaParam);
        Coordinate paramTo = new Coordinate(deltaParam, deltaParam);

        gui = new FractalTilesGUI(defaultIteratorFactory, maxiter, tileWidth, tileHeight, tilesX, tilesY, from, to, paramFrom, paramTo, julia, resizeable);

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

        add(gui);

        JMenuBar menu = new FractalMenuBar(this);
        setJMenuBar(menu);

        pack();
        setVisible(true);
    }

    public FractalTilesTest(boolean julia) {
        this(julia, false);
    }

    public FractalTilesTest() {
        this(false);
    }

    public SaveableView getSaveableView() {
        return gui.getView();
    }

    
    public static void main(String[] args) {
        new FractalTilesTest();
    }

}
