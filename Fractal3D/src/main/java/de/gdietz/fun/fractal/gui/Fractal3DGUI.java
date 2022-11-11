package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.*;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.*;
import de.gdietz.fun.fractal.util.Coordinate3D;
import de.gdietz.fun.fractal.view.*;

import javax.swing.*;
import java.awt.*;

public class Fractal3DGUI extends JPanel implements FractalIteratorManager<Coordinate3D> {

	private final Fractal3DIterateController controller;

	private final Fractal3DPictureView picture;

	private final AdvancedFractalIteratorFactorySelector<Coordinate3D> iteratorSelector;

    public Fractal3DGUI(FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter, int size,
	                  Coordinate3D from, Coordinate3D to, boolean julia, boolean resizeable) {
		super(new GridBagLayout());

        Fractal3DIterateModel model = new Fractal3DSimpleIterateModel(size, from, to, iteratorFactory, maxiter, julia);

		controller = new Fractal3DSimpleController(model);

		picture = new Fractal3DPictureView(model, controller);
		JComponent controlView = new Fractal3DEditView(model, controller);
		JPanel panel = getPanel(picture, controlView);

		iteratorSelector = AdvancedFractalIteratorFactorySelector.create(Coordinate3D.class, this, true);
		iteratorSelector.addIteratorFactory(iteratorFactory, maxiter, "default");

        GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		add(panel, c);
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		add(iteratorSelector, c);

        if (resizeable) {
            Fractal3DSizeSelector sizeSelector = new Fractal3DSizeSelector(model, controller);
            add(sizeSelector, c);
        }

        controller.startCalculation();
	}

    public Fractal3DGUI(FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter, int size,
	                  Coordinate3D from, Coordinate3D to, boolean julia) {
        this(iteratorFactory, maxiter, size, from, to, julia, true);
    }

    public Fractal3DGUI(FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter, int size,
	                  Coordinate3D from, Coordinate3D to) {
        this(iteratorFactory, maxiter, size, from, to, false);
    }

	private JPanel getPanel(JComponent view, JComponent controlView) {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		panel.add(view, c);
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		panel.add(controlView, c);
		return panel;
	}

	public void setIteratorFactory(FractalIteratorFactory<Coordinate3D> iteratorFactory) {
		controller.setIteratorFactory(iteratorFactory);
	}

	public void setMaxiter(int maxiter) {
		controller.setMaxiter(maxiter);
	}

	public void setIteratorData(FractalIteratorFactory<Coordinate3D> iteratorFactory, int maxiter) {
		controller.setIteratorData(iteratorFactory, maxiter);
	}

	public AdvancedFractalIteratorFactorySelector<Coordinate3D> getIteratorSelector() {
		return iteratorSelector;
	}

	public Fractal3DPictureView getView() {
		return picture;
	}

	public Fractal3DIterateController getController() {
		return controller;
	}

}