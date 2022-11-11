package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.*;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.*;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.view.*;

import javax.swing.*;
import java.awt.*;

public class FractalViewsGUI extends JPanel implements PaletteView, FractalIteratorManager<Coordinate> {

	private final Fractal2DIterateController controller;

    private Palette palette;

    private final IterHeightStrategy heightStrategy;

	private final FractalPictureViewBase<Coordinate> pictureViewLeft;
	private final FractalPictureViewBase<Coordinate> pictureViewRight;

	private final AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector;

    public enum ViewType {
        SURFACE
    }

    public FractalViewsGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height,
	                  Coordinate from, Coordinate to, ViewType type, boolean julia, boolean second) {
		super(new GridBagLayout());

        FractalIterateModel<Integer> model = new FractalSimpleIterateModel(width, height, from, to, iteratorFactory, maxiter, julia);

        controller = new FractalSimpleController(model);

        @SuppressWarnings("unused")
        WatchingController watcher = new WatchingController(controller);

        Fractal2DPairedController<Coordinate> pairedControllerLeft = new Fractal2DNullPairedControllerImpl(controller);
        Fractal2DPairedController<Coordinate> pairedControllerRight = new Fractal2DPairedControllerImpl(controller);

		PaletteSelector paletteSelector = new PaletteSelector(this);
		palette = paletteSelector.getPalette();

        heightStrategy = new IterHeightStrategy(100, maxiter);
        ColorStrategy<Integer> coloring = new SimpleColorStrategy(palette, false);

        FractalPicture<Coordinate> pictureLeft;
        switch(type) {
            case SURFACE:
                pictureLeft = new FractalSurfacePicture<>(model, heightStrategy, coloring);
                break;
            default:
                throw new AssertionError("Unknown FractalGUI configuration.");
        }

        pictureViewLeft = new FractalScaledPictureView<>(pictureLeft, pairedControllerLeft);

        FractalPictureViewBase<Coordinate> pictureViewSecond = null;
        if (second) {
            FractalPicture<Coordinate> pictureRight = new FractalPictureImpl<>(model, coloring);
			pictureViewSecond = new FractalScaledPictureView<>(pictureRight, pairedControllerRight);
        }
        pictureViewRight = pictureViewSecond;

        JComponent controlView = new FractalEditView(model, controller);
        JPanel panel = getPanel(pictureViewLeft, pictureViewRight, controlView);

		iteratorSelector = AdvancedFractalIteratorFactorySelector.create(Coordinate.class, this, true);
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
		add(paletteSelector, c);
		add(iteratorSelector, c);

        FractalSizeSelector sizeSelector = new FractalSizeSelector(model, controller);
        add(sizeSelector, c);

        controller.startCalculation();
	}

    public FractalViewsGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height,
                      Coordinate from, Coordinate to, ViewType type) {
        this(iteratorFactory, maxiter, width, height, from, to, type, false, false);
    }

    private JPanel getPanel(JComponent leftView, JComponent rightView, JComponent controlView) {
        JPanel view = new JPanel(new GridLayout(1, 2));
        view.add(leftView);
        view.add(rightView);
        return getPanel(view, controlView);
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

	public void setPalette(Palette palette) {
		this.palette = palette;
		pictureViewLeft.setPalette(palette);
		if (pictureViewRight != null)
			pictureViewRight.setPalette(palette);
	}

    public Palette getPalette() {
        return palette;
    }

    public void setIteratorFactory(FractalIteratorFactory<Coordinate> iteratorFactory) {
		controller.setIteratorFactory(iteratorFactory);
	}

	public void setMaxiter(int maxiter) {
		controller.setMaxiter(maxiter);
        heightStrategy.setMaxiter(maxiter);
	}

	public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		controller.setIteratorData(iteratorFactory, maxiter);
	}

	public AdvancedFractalIteratorFactorySelector<Coordinate> getIteratorSelector() {
		return iteratorSelector;
	}

	public FractalPictureViewBase<Coordinate> getView(boolean left) {
		return left ? pictureViewLeft : pictureViewRight;
	}

	public FractalPictureViewBase<Coordinate> getView() {
		return getView(true);
	}

	public Fractal2DIterateController getController() {
		return controller;
	}

}
