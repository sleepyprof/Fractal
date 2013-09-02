package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.*;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.util.Complex;
import de.gdietz.fun.fractal.util.Coordinate;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.view.*;
import de.gdietz.fun.fractal.model.*;

import javax.swing.*;
import java.awt.*;

public class FractalGUI extends JPanel implements PaletteView, FractalIteratorManager<Coordinate> {

	private final Fractal2DIterateController controllerLeft;
	private final Fractal2DIterateController controllerRight;

    private Palette palette;

	private final FractalPictureViewBase<Coordinate> pictureViewLeft;
	private final FractalPictureViewBase<Coordinate> pictureViewRight;

	private final AdvancedFractalIteratorFactorySelector<Coordinate> iteratorSelector;

    public enum Config {
        MANDEL, JULIA, MANDEL4D, JULIA4D, MANDEL_JULIA, MANDEL_TRACE, MANDEL_BIFURCATION
    }

    private FractalIterateModel<Integer> createDefaultModel(int width, int height, Coordinate from, Coordinate to,
                                                        FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter,
                                                        boolean julia, boolean endless) {
        return endless ? new FractalEndlessIterateModel(width, height, from, to, iteratorFactory, maxiter, julia) :
            new FractalSimpleIterateModel(width, height, from, to, iteratorFactory, maxiter, julia);
    }

    public FractalGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height,
	                  Coordinate from, Coordinate to, Coordinate from2, Coordinate to2, Config config,
                      boolean endless, boolean resizeable) {
		super(new GridBagLayout());

        FractalIterateModel<Integer> modelLeft;
        FractalIterateModel<Integer> modelRight = null;

        switch(config) {
            case MANDEL:
                modelLeft = createDefaultModel(width, height, from, to, iteratorFactory, maxiter, false, endless);
                break;
            case JULIA:
                modelLeft = createDefaultModel(width, height, from, to, iteratorFactory, maxiter, true, endless);
                break;
            case MANDEL4D:
                modelLeft = new FractalSimpleIterate4DModel(width, height, from, to, iteratorFactory, maxiter);
                break;
            case JULIA4D:
                modelLeft = new FractalSimpleIterate4DModel(width, height, from, to, iteratorFactory, maxiter, true);
                break;
            case MANDEL_JULIA:
                modelLeft = createDefaultModel(width, height, from, to, iteratorFactory, maxiter, false, endless);
                modelRight = createDefaultModel(width, height, from, to, iteratorFactory, maxiter, true, endless);
                break;
            case MANDEL_TRACE:
                modelLeft = createDefaultModel(width, height, from, to, iteratorFactory, maxiter, false, endless);
                modelRight = new FractalTraceModel(width, height, from2, to2, iteratorFactory, maxiter);
                break;
            case MANDEL_BIFURCATION:
                modelLeft = createDefaultModel(width, height, from, to, iteratorFactory, maxiter, false, endless);
                modelRight = new FractalBifurcationModel(width, height, from2, to2, iteratorFactory, maxiter);
                modelRight.setParameter(Complex.ONE);
                break;
            default:
                throw new AssertionError("Unknown FractalGUI configuration.");
        }

		controllerLeft = new FractalSimpleController(modelLeft);
        controllerRight = modelRight == null ? null : new FractalSimpleController(modelRight);

        @SuppressWarnings("unused")
        WatchingController watcherLeft = new WatchingController(controllerLeft);
        @SuppressWarnings("unused")
        WatchingController watcherRight = modelRight == null ? null : new WatchingController(controllerRight);

        Fractal2DPairedController<Coordinate> pairedControllerLeft = new Fractal2DPairedControllerImpl(controllerLeft, controllerRight);
        Fractal2DPairedController<Coordinate> pairedControllerRight = null;
        if (modelRight != null)
            pairedControllerRight = new Fractal2DPairedControllerImpl(controllerRight, controllerLeft);

		PaletteSelector paletteSelector = new PaletteSelector(this);
		palette = paletteSelector.getPalette();

        ColorStrategy<Integer> coloringLeft = new SimpleColorStrategy(palette, false);
        ColorStrategy<Integer> coloringRight = new SimpleColorStrategy(palette, config.equals(Config.MANDEL_TRACE) || config.equals(Config.MANDEL_BIFURCATION));

        FractalPicture<Coordinate> pictureLeft = new FractalPictureImpl<Integer>(modelLeft, coloringLeft);
		pictureViewLeft = resizeable ? new FractalScaledPictureView<Coordinate>(pictureLeft, pairedControllerLeft) :
                new FractalPictureView<Coordinate>(pictureLeft, pairedControllerLeft);
		JComponent controlViewLeft = new FractalEditView(modelLeft, controllerLeft);
		JPanel panelLeft = getPanel(pictureViewLeft, controlViewLeft);

        FractalPictureViewBase<Coordinate> pictureViewSecond = null;
		JPanel panelRight = null;
        if (modelRight != null) {
            FractalPicture<Coordinate> pictureRight = new FractalPictureImpl<Integer>(modelRight, coloringRight);
			pictureViewSecond =  resizeable ? new FractalScaledPictureView<Coordinate>(pictureRight, pairedControllerRight) :
                    new FractalPictureView<Coordinate>(pictureRight, pairedControllerRight);
			JComponent controlViewRight = new FractalEditView(modelRight, controllerRight);
			panelRight = getPanel(pictureViewSecond, controlViewRight);
        }
        pictureViewRight = pictureViewSecond;

		iteratorSelector = new AdvancedFractalIteratorFactorySelector<Coordinate>(this, !endless);
		iteratorSelector.addIteratorFactory(iteratorFactory, maxiter, "default");

        GridBagConstraints c = new GridBagConstraints();
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.BOTH;
		if (panelRight == null)
			c.gridwidth = GridBagConstraints.REMAINDER; 
		add(panelLeft, c);
		if (panelRight != null) {
			c.gridwidth = GridBagConstraints.REMAINDER;
			add(panelRight, c);
		}
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		add(paletteSelector, c);
		add(iteratorSelector, c);

        if (config == Config.MANDEL || config == Config.JULIA) {
            FractalFuzzySelector fuzzyController = new FractalFuzzySelector(modelLeft, new FractalFuzzyController(modelLeft), false);
            add(fuzzyController, c);
        }

        if (resizeable) {
            FractalSizeSelector sizeSelector = new FractalSizeSelector(modelLeft, controllerLeft, controllerRight);
            add(sizeSelector, c);
        }
        
        controllerLeft.startCalculation();
		if (controllerRight != null)
			controllerRight.startCalculation();
	}

	public FractalGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height,
	                  Coordinate from, Coordinate to, Config config,
                      boolean endless, boolean resizeable) {
		this(iteratorFactory, maxiter, width, height, from, to, from, to, config, endless, resizeable);
	}

    public FractalGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height,
                      Coordinate from, Coordinate to, Config config) {
        this(iteratorFactory, maxiter, width, height, from, to, config, true, false);
    }

    public FractalGUI(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter, int width, int height,
                      Coordinate from, Coordinate to) {
        this(iteratorFactory, maxiter, width, height, from, to, Config.MANDEL);
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
		controllerLeft.setIteratorFactory(iteratorFactory);
		if (controllerRight != null)
			controllerRight.setIteratorFactory(iteratorFactory);
	}

	public void setMaxiter(int maxiter) {
		controllerLeft.setMaxiter(maxiter);
		if (controllerRight != null)
			controllerRight.setMaxiter(maxiter);
	}

	public void setIteratorData(FractalIteratorFactory<Coordinate> iteratorFactory, int maxiter) {
		controllerLeft.setIteratorData(iteratorFactory, maxiter);
		if (controllerRight != null)
			controllerRight.setIteratorData(iteratorFactory, maxiter);
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

	public Fractal2DIterateController getController(boolean left) {
		return left ? controllerLeft : controllerRight;
	}

	public Fractal2DIterateController getController() {
		return getController(true);
	}

}
