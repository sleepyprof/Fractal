package de.gdietz.fun.fractal.gui;

import de.gdietz.fun.fractal.controller.*;
import de.gdietz.fun.fractal.formula.FractalIteratorFactory;
import de.gdietz.fun.fractal.model.FractalPreciseEndlessIterateModel;
import de.gdietz.fun.fractal.model.FractalPreciseIterateModel;
import de.gdietz.fun.fractal.model.FractalPreciseSimpleIterateModel;
import de.gdietz.fun.fractal.palette.Palette;
import de.gdietz.fun.fractal.util.BigCoordinate;
import de.gdietz.fun.fractal.view.*;

import javax.swing.*;
import java.awt.*;

public class FractalPreciseGUI extends JPanel implements PaletteView, FractalIteratorManager<BigCoordinate> {

    private final FractalPrecise2DIterateController controller;

    private Palette palette;

    private final FractalPictureViewBase<BigCoordinate> pictureView;

    private final AdvancedFractalPreciseIteratorFactorySelector<BigCoordinate> iteratorSelector;

    public FractalPreciseGUI(FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter, int width, int height,
                             BigCoordinate from, BigCoordinate to, boolean julia, boolean endless, boolean resizeable) {
        super(new GridBagLayout());

        FractalPreciseIterateModel<Integer> model = endless ?
                new FractalPreciseEndlessIterateModel(width, height, from, to, iteratorFactory, maxiter, julia) :
                new FractalPreciseSimpleIterateModel(width, height, from, to, iteratorFactory, maxiter, julia);

        controller = new FractalPreciseSimpleController(model);
        Fractal2DPairedController<BigCoordinate> pairedController = new FractalPrecise2DPairedControllerImpl(controller);

        @SuppressWarnings("unused")
        WatchingController watcher = new WatchingController(controller);

        PaletteSelector paletteSelector = new PaletteSelector(this);
        palette = paletteSelector.getPalette();

        ColorStrategy<Integer> coloring = new SimpleColorStrategy(palette, false);

        FractalPicture<BigCoordinate> picture = new FractalPrecisePictureImpl<Integer>(model, coloring);
        pictureView = resizeable ? new FractalScaledPictureView<BigCoordinate>(picture, pairedController) :
                new FractalPictureView<BigCoordinate>(picture, pairedController);

        JComponent controlView = new FractalPreciseEditView(model, controller);
        JPanel panel = getPanel(pictureView, controlView);

        iteratorSelector = new AdvancedFractalPreciseIteratorFactorySelector<BigCoordinate>(this, !endless);
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

        if (resizeable) {
            FractalSizeSelector sizeSelector = new FractalSizeSelector(model, controller);
            add(sizeSelector, c);
        }

        controller.startCalculation();
    }

    public FractalPreciseGUI(FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter, int width, int height,
                             BigCoordinate from, BigCoordinate to, boolean julia) {
        this(iteratorFactory, maxiter, width, height, from, to, julia, false, false);
    }
    
    public FractalPreciseGUI(FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter, int width, int height,
	                  BigCoordinate from, BigCoordinate to) {
        this(iteratorFactory, maxiter, width, height, from, to, true);
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

    public void setIteratorFactory(FractalIteratorFactory<BigCoordinate> iteratorFactory) {
		controller.setIteratorFactory(iteratorFactory);
	}

	public void setMaxiter(int maxiter) {
		controller.setMaxiter(maxiter);
	}

	public void setIteratorData(FractalIteratorFactory<BigCoordinate> iteratorFactory, int maxiter) {
		controller.setIteratorData(iteratorFactory, maxiter);
	}

	public AdvancedFractalPreciseIteratorFactorySelector<BigCoordinate> getIteratorSelector() {
		return iteratorSelector;
	}

	public FractalPictureViewBase<BigCoordinate> getView() {
		return pictureView;
	}

	public FractalPrecise2DIterateController getController() {
		return controller;
	}

}
