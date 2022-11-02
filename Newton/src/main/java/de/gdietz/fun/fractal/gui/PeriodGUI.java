package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.*;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.FractalPeriodModel;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.view.*;

import javax.swing.*;
import java.awt.*;

public class PeriodGUI extends JPanel implements PaletteView, FractalIteratorManager<Coordinate> {

	private final Fractal2DIterateController controller;

	private Palette palette;

	private final FractalPictureViewBase<Coordinate> pictureView;

	private final AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector;

    public PeriodGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height,
	                  Coordinate from, Coordinate to, double epsilon, boolean julia, boolean resizeable) {
		super(new GridBagLayout());

        FractalPeriodModel model = new FractalPeriodModel(width, height, from, to, iteratorFactory, maxiter, epsilon, julia);

		controller = new FractalSimpleController(model);
        Fractal2DPairedController<Coordinate> pairedController = new Fractal2DPairedControllerImpl(controller);

        @SuppressWarnings("unused")
        WatchingController watcher = new WatchingController(controller);

		PaletteSelector paletteSelector = new PaletteSelector(this);
		palette = paletteSelector.getPalette();

        ColorStrategy<Integer> coloring = new SimpleColorStrategy(palette, false);

        FractalPicture<Coordinate> picture = new FractalPictureImpl<>(model, coloring);
        pictureView = resizeable ? new FractalScaledPictureView<>(picture, pairedController) :
                new FractalPictureView<>(picture, pairedController);

		JComponent controlView = new FractalEditView(model, controller);
		JPanel panel = getPanel(pictureView, controlView);

		iteratorSelector = new AdvancedFractalIteratorFactorySelector<>(this, true);
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

        FractalFuzzySelector fuzzyController = new FractalFuzzySelector(model, new FractalFuzzyPeriodController(model), true);
        add(fuzzyController, c);

        if (resizeable) {
            FractalSizeSelector sizeSelector = new FractalSizeSelector(model, controller);
            add(sizeSelector, c);
        }

        paletteSelector.setFactor(100.0f);
        controller.startCalculation();
	}

    public PeriodGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height,
	                  Coordinate from, Coordinate to, double epsilon, boolean julia) {
        this(iteratorFactory, maxiter, width, height, from, to, epsilon, julia, false);
    }

    public PeriodGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height,
	                  Coordinate from, Coordinate to, double epsilon) {
        this(iteratorFactory, maxiter, width, height, from, to, epsilon, false);
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
		pictureView.setPalette(palette);
	}

    public Palette getPalette() {
        return palette;
    }

    public void setIteratorFactory(FractalIteratorFactory<Coordinate> iteratorFactory) {
		controller.setIteratorFactory(iteratorFactory);
	}

	public void setMaxiter(int maxiter) {
		controller.setMaxiter(maxiter);
	}

	public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		controller.setIteratorData(iteratorFactory, maxiter);
	}

	public AdvancedFractalIteratorFactorySelector<Coordinate> getIteratorSelector() {
		return iteratorSelector;
	}

	public FractalPictureViewBase<Coordinate> getView() {
		return pictureView;
	}

	public Fractal2DIterateController getController() {
		return controller;
	}

}