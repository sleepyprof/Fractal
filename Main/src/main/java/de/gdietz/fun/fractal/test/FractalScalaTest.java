package de.gdietz.fun.fractal.test;

import de.gdietz.fun.fractal.gui.FractalScalaGUI;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.view.SaveableView;

import javax.swing.*;

public class FractalScalaTest extends FractalWindow {

    private final FractalScalaGUI gui;

    public FractalScalaTest(boolean endless, boolean resizeable) {
		super("Fractal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int maxiter = 1000;

        int width = 501;
        int height = 501;

        double delta = 2.0;

        Coordinate from = new Coordinate(-delta, -delta);
        Coordinate to = new Coordinate(delta, delta);

        gui = new FractalScalaGUI(maxiter, width, height, from, to, endless, resizeable);

        add(gui);

		JMenuBar menu = new FractalMenuBar(this);
		setJMenuBar(menu);

		pack();
		setVisible(true);
    }

    public FractalScalaTest() {
        this(true, false);
    }

    public SaveableView getSaveableView() {
        return gui.getView();
    }


    public static void main(String[] args) {
		new FractalScalaTest();
	}

}
